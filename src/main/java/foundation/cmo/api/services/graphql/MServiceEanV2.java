package foundation.cmo.api.services.graphql;

import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import foundation.cmo.api.db.models.sales.MProduct;
import foundation.cmo.api.db.repositories.MProductRepository;
import foundation.cmo.api.security.JwtUtils;
import foundation.cmo.api.services.MConsts;
import foundation.cmo.api.services.MService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@GraphQLApi
public class MServiceEanV2 extends MService implements MConsts {

	@Autowired
	private MProductRepository productRepository;
	
	@Autowired
	private JwtUtils jwtUtils;

//	@Cacheable(GET_PRODUCT)
	@GraphQLQuery(name = GET_PRODUCT)
	public MProduct getProduct(@GraphQLArgument(name = FIELD$product_ean) Long ean) {
		log.info("Finding for product...");

		MProduct product = productRepository.findByEan(ean).orElse(null);
		if (product != null) {
			return product;
		}

		RestTemplate restTemplate = new RestTemplate();

		String uri = "http://www.eanpictures.com.br:9000/api/desc/%d";
		uri = String.format(uri, ean);

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		JSONObject obj = new JSONObject(response.getBody());

		if (obj.getInt("Status") != 200) {
//			throw getRuntimeExceptionNotFound(get(obj, "Status_Desc", String.class));
			return null;
		}

		product = new MProduct();
		product.setEan(ean);
		product.setName(get(obj, "Nome", String.class));
		product.setBrands(get(obj, "Marca", String.class));
		product.setNcm(get(obj, "Ncm", Long.class));

		return productRepository.save(product);
	}

	@GraphQLQuery(name = FIELD$product_img)
	public String getImage(@GraphQLContext MProduct product) {
		try {
			String uri = String.format("http://www.eanpictures.com.br:9000/api/gtin/%d", product.getEan());
			
			log.info(uri);
			
			java.net.URL url = new java.net.URL(uri);
			InputStream is = url.openStream();
			byte[] bytes = IOUtils.toByteArray(is);
			return Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			
			return null;
		}
	}

	@GraphQLMutation(name = STORE_PRODUCT)
	public MProduct storeProduct(MProduct product) {
		cloneObject("ean", "findByEan", productRepository, product);
		return productRepository.save(product);
	}

	@GraphQLQuery(name = QUERY_TEST, description = "${text.message}")
	public String testApi() {
//		log.info(jwtUtils.generateToken());
		return jwtUtils.generateToken(null);
	}

//	private RuntimeException getRuntimeExceptionNotFound() {
//		return getRuntimeExceptionNotFound(getString(MESSAGE$exception_notfound));
//	}
	
//	private RuntimeException getRuntimeExceptionNotFound(String message) {
//		return new UnsupportedOperationException(message);
//	}

	private <T> T get(JSONObject obj, String key, Class<T> type) {
		try {
			return type.cast(obj.get(key));
		} catch (Exception e) {
			return null;
		}
	}

}
