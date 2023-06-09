package foundation.cmo.api.services.graphql;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class MServiceEan {

	//http://brasilapi.simplescontrole.com.br/mercadoria/consulta/?format=json&ean=7894900700046&access-token=ojE6dFmiy9RJw6h5sIENbbthFm-Dbg-l
	
	
	private final String API_TOKEN    = "ojE6dFmiy9RJw6h5sIENbbthFm-Dbg-l";
	private final String API_ENDPOINT = "http://brasilapi.simplescontrole.com.br/mercadoria/consulta/?format=json&ean=%d&access-token=%s";
	
	@GraphQLQuery(name = "obter_ean")
	public String getEan(@GraphQLArgument(name = "cd_ean") Long ean) {
		return getEanJson(ean);
	}
	
	private String getEanJson(Long ean) {
		String apiEndPoint = String.format(API_ENDPOINT, ean, API_TOKEN);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(apiEndPoint, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			
			return responseBody;
		}
		return "";
	}
	

}
