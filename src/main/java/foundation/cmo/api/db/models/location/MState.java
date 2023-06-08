package foundation.cmo.api.db.models.location;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "Estado")
public class MState extends MLocation{
	private static final long serialVersionUID = 1L;
	@Column(name = "nm_estado")
	@GraphQLQuery(name = "nm_estado")
	private String name;
	@Column(name = "ds_sigla")
	@GraphQLQuery(name = "ds_sigla")
	private String acronym;
}
