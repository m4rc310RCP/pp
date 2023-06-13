package foundation.cmo.api.services.graphql;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import foundation.cmo.api.db.models.sales.MProduct;
import foundation.cmo.api.db.repositories.MProductRepository;
import foundation.cmo.api.services.MService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

//@Service
//@GraphQLApi
public class MServiceEan extends MService {

	// http://brasilapi.simplescontrole.com.br/mercadoria/consulta/?format=json&ean=7894900700046&access-token=ojE6dFmiy9RJw6h5sIENbbthFm-Dbg-l

	private final String API_TOKEN = "ojE6dFmiy9RJw6h5sIENbbthFm-Dbg-l";
//	private final String API_ENDPOINT = "http://brasilapi.simplescontrole.com.br/mercadoria/consulta/?format=json&ean=%d&access-token=%s";
//	private final String API_ENDPOINT = "https://ean-db.com/api/v1/product/%s";
//	private final String API_ENDPOINT = "https://world.openfoodfacts.org/api/v0/product/%s.json";
//	private final String API_ENDPOINT = "https://world.openfoodfacts.org/api/v2/search?code=%s&fields=code,product_name,brands,image_url";
	private final String API_ENDPOINT = "https://world.openfoodfacts.org/api/v2/product/%d?fields=product_name,code,countries,grade,brands";

	@Autowired
	private MProductRepository productRepository;

//	@Cacheable("getEan")
	@GraphQLQuery(name = "obter_ean")
	public MProduct getEan(@GraphQLArgument(name = "cd_ean") Long ean) {
		return getEanJson(ean);
	}

	private MProduct getEanJson(Long ean) {
		String apiEndPoint = String.format(API_ENDPOINT, ean, API_TOKEN);

//		HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_JSON);
//	    headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwOWFmZTg3My01ZDI3LTQ5MmMtYjNlNy0yNzU5MjJhNWEzYTIiLCJpc3MiOiJjb20uZWFuLWRiIiwiaWF0IjoxNjg2MzU4ODM1LCJleHAiOjE3MTc4OTQ4MzV9.1RNTyJIHUQF8voU4yeRl-iOdQ3O0wyZcqmo55ERQMFJljtnOsMgd2DEG1wybYnwTwXLFqxF6bptXJpfQc0KSKg");
//	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity(apiEndPoint, String.class);
//		ResponseEntity<String> response = restTemplate.exchange(apiEndPoint, HttpMethod.GET, entity, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {

			System.out.println(response.getBody());

			JSONObject obj = new JSONObject(response.getBody());

			if (obj.getInt("status") == 0) {
				return null;
			}

			obj = obj.getJSONObject("product");

//			if (obj.has("message")) {
//				throw new UnsupportedOperationException(obj.getString("message"));
//			}
//			obj = obj.getJSONObject("return");
//			
			MProduct product = new MProduct();
			product.setName(get(obj, "product_name", String.class));
			product.setEan(ean);
			product.setCategories(get(obj, "categories", String.class));
			product.setBrands(get(obj, "brands", String.class));

//			cloneObject("ean", "findByEan", null, null);

////			product.setMinPrice(new BigDecimal(obj.getString("preco_minimo")));
////			product.setMaxPrice(new BigDecimal(obj.getString("preco_maximo")));
////			product.setAvgPrice(new BigDecimal(obj.getString("preco_medio")));
//			product.setPackageType(obj.getString("tipo_embalagem"));

			return storeProduct(product);
		}
		return null;
	}

	@GraphQLMutation(name = "save_product")
	public MProduct storeProduct(MProduct product) {
		Long ean = product.getEan();
		if (ean != null) {
			cloneObject("ean", "findByEan", productRepository, product);
		}
		return productRepository.save(product);
	}

	@GraphQLQuery(name = "lista_produtos")
	public List<MProduct> listAllProducts() {
		return productRepository.findAll();
	}

	private <T> T get(JSONObject obj, String key, Class<T> type) {
		try {
			return type.cast(obj.get(key));
		} catch (Exception e) {
			return null;
		}
	}

}
