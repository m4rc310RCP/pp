package foundation.cmo.api.services.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MState;
import foundation.cmo.api.db.repositories.MCityRepository;
import foundation.cmo.api.db.repositories.MDistrictRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class MServiceLocation {

	@Autowired
	private MDistrictRepository districtRepository;
	@Autowired
	private MCityRepository cityRepository;
	
	
	@GraphQLQuery(name = "listar_municipios_estado")
	private List<MCity> getState(@GraphQLArgument(name = "uf") MState state) {
		return cityRepository.findAllByMState(state);
	}
}
