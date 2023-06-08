package foundation.cmo.api.services.graphql;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MDistrict;
import foundation.cmo.api.db.models.location.MState;
import foundation.cmo.api.db.repositories.MDistrictRepository;
import io.leangen.graphql.annotations.GraphQLQuery;

//@Service
//@GraphQLApi
public class MServiceInit {

	@Autowired
	private MDistrictRepository districtRepository;
	private long i;
	
	@GraphQLQuery(name = "test_aplication")
	public String test() {
		return "OK";
	}

//	@GraphQLQuery(name = "test_aplication")
	public String testApplication() {

		String surl = "https://servicodados.ibge.gov.br/api/v1/localidades/distritos";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(surl, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			// A requisição foi bem-sucedida
			String responseBody = response.getBody();

			JSONArray array = new JSONArray(responseBody);
			array.forEach(obj -> {
				JSONObject dist = (JSONObject) obj;
//            	System.out.println(dist.get("nome"));
				MDistrict dis = new MDistrict();
				dis.setId(dist.getLong("id"));
				dis.setName(dist.getString("nome"));

				// --------------------------------------------//
				dist = dist.getJSONObject("municipio");
				MCity city = new MCity();
				city.setId(dist.getLong("id"));
				city.setName(dist.getString("nome"));

				dis.setCity(city);
				// --------------------------------------------//
				dist = dist.getJSONObject("microrregiao")
						   .getJSONObject("mesorregiao")
						   .getJSONObject("UF");

				MState state = new MState();
				state.setId(dist.getLong("id"));
				state.setName(dist.getString("nome"));
				state.setAcronym(dist.getString("sigla"));
				
				city.setState(state);
				// --------------------------------------------//

				districtRepository.save(dis);

				if ((i++) % 200 == 0) {
					districtRepository.flush();
				}

//				dis.setCity(new MCity());
//				dis.getCity().setState(new MState());
			});

		} else {
			// A requisição falhou
			System.out.println("Falha na requisição: " + response.getStatusCode());
		}

//		
//		dis.setId(123L);
//		dis.setName("Test District");
//		
//		dis.getCity().setId(12L);
//		dis.getCity().setName("Test City");
//		
//		dis.getCity().getState().setId(1L);
//		dis.getCity().getState().setName("Test State");;
//		
//		districtRepository.save(dis);

		return "OK";
	}
}
