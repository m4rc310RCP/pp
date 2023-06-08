package foundation.cmo.api.db.models.location;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "Distrito")
public class MDistrict extends MLocation{
	
	private static final long serialVersionUID = 1L;

	@GraphQLQuery(name = "nm_distrito")
	@Column(name = "nm_distrito")
	private String name;
	
	@GraphQLIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cd_municipio", referencedColumnName = "cd_ibge")
	private MCity city;
}
