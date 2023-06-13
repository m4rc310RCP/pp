package foundation.cmo.api.services.graphql.openweather;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import foundation.cmo.api.services.MConsts;
import foundation.cmo.api.services.MService;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@GraphQLApi
public class MServiceOpenWeatherV1 extends MService implements MConsts {
	
	private final String API_KEY = "f78e35a5edf2478e0569d6abe8a7dcdb";

	@GraphQLQuery(name = QUERY_TEST_API)
	public String test() throws Exception {
		String city = UriUtils.encode("Campo Mourão", StandardCharsets.UTF_8.toString());

		String uri = String.format("http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=%s", API_KEY);

		URL url = new URL(uri);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		
		InputStream responseStream = connection.getInputStream();
//		ObjectMapper mapper = new ObjectMapper();
//		List<String> list = mapper.readValue(responseStream, new TypeReference<List<String>>(){});
//		
//		list.forEach(inf -> {
//			log.info(inf);
//		});
		
		return "OK";
	}

}
