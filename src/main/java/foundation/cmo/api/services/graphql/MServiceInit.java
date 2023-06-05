package foundation.cmo.api.services.graphql;

import org.springframework.stereotype.Service;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class MServiceInit {

	@GraphQLQuery(name = "test_aplication")
	public String testApplication() {
		return "OK";
	}
}
