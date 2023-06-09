package foundation.cmo.api.services.graphql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MDistrict;
import foundation.cmo.api.db.repositories.MCityRepository;
import foundation.cmo.api.db.repositories.MDistrictRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
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
	public List<MCity> getState(@GraphQLArgument(name = "cd_uf") String acronym) {
		return cityRepository.findAllByStateAcronym(acronym);
	}

	@GraphQLQuery(name = "listar_municipios_estado")
	public List<MCity> getState(@GraphQLArgument(name = "cd_uf") String acronym, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return cityRepository.findAllByStateAcronym(acronym, pageable);
	}
	
	
	@GraphQLQuery(name = "cd_uf")
	public String getStateAcronym(@GraphQLContext MCity city) {
		try {
			return city.getState().getAcronym();
		} catch (Exception e) {
			return "";
		}
	}

	@GraphQLQuery(name = "obter_municipio")
	public MCity getCity(@GraphQLArgument(name = "cd_ibge") Long id) {
		return cityRepository.findById(id).orElse(null);
	}
	
	@GraphQLQuery(name = "pesquisa_municipios")
	public List<MCity> getCities(@GraphQLArgument(name = "nm_municipio") String name){
		return cityRepository.findAllByNameContainingIgnoreCase(name);
	}
	
	@GraphQLQuery(name = "ls_distritos")
	public List<MDistrict> getDistrictsFromCity(@GraphQLContext MCity city) {
		List<MDistrict> list = new ArrayList<>();
		districtRepository.findAllByCity(city).forEach(district -> {
			String sid = String.format("%d", district.getId());
			if (!sid.endsWith("05")) {
				list.add(district);
			}
		});
		return list;
	}
	
	

}
