package foundation.cmo.api.db.models.location;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

@Data
@MappedSuperclass
public class MLocation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cd_ibge")
	@GraphQLQuery(name = "cd_ibge")
	private Long id;
}
