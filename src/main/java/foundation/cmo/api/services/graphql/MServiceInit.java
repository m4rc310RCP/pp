package foundation.cmo.api.services.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MDistrict;
import foundation.cmo.api.db.models.location.MState;
import foundation.cmo.api.db.repositories.MDistrictRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class MServiceInit {
	
	@Autowired private MDistrictRepository districtRepository;

	@GraphQLQuery(name = "test_aplication")
	public String testApplication() {
		
		MDistrict dis = new MDistrict();
		dis.setCity(new MCity());
		dis.getCity().setState(new MState());
		
		dis.setId(123L);
		dis.setName("Test District");
		
		dis.getCity().setId(12L);
		dis.getCity().setName("Test City");
		
		dis.getCity().getState().setId(1L);
		dis.getCity().getState().setName("Test State");;
		
		districtRepository.save(dis);
		
		return "OK";
	}
}
