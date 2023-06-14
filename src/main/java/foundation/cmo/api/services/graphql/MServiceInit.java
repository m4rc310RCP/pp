package foundation.cmo.api.services.graphql;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import foundation.cmo.api.db.models.auth.MUser;
import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MDistrict;
import foundation.cmo.api.db.models.location.MState;
import foundation.cmo.api.db.repositories.MDistrictRepository;
import foundation.cmo.api.db.repositories.MUserRepository;
import foundation.cmo.api.security.JwtUtils;
import foundation.cmo.api.security.MUserDetails;
import foundation.cmo.api.services.MConsts;
import foundation.cmo.api.services.MService;
import foundation.cmo.api.services.MServiceCache;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@GraphQLApi
public class MServiceInit extends MService implements MConsts {

	@Autowired
	private MDistrictRepository districtRepository;
	private long i;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private MUserRepository userRepository;
	
	@Autowired
	private MServiceCache serviceCache;
	
	//@GraphQLQuery(name = "test_aplication")
	public String test() {
		return "OK";
	}

	@GraphQLMutation(name = MUTATION$user)
	public MUser storeUser(@GraphQLArgument(name = FIELD$user) MUser user) {
		cloneObject("username", "findById", userRepository, user);
		return userRepository.save(user);
	}
	
	@GraphQLQuery(name = FIELD$user_token)
	public String getTokenFrom(@GraphQLContext MUser user) {
		return jwtUtils.generateToken(new MUserDetails(user));
	}
	
	
	@GraphQLQuery(name = "obter_usuario")
	public MUser getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication  authentication = context.getAuthentication();
//			return userRepository.findById(authentication.getName()).orElse(null);
			return serviceCache.getUser(authentication.getName());
		}
		return null;
	}
	
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
