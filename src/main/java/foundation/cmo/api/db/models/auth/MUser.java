package foundation.cmo.api.db.models.auth;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import io.leangen.graphql.annotations.GraphQLIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

@Data
@Entity(name = "Usuario")
public class MUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "nm_usuario")
	@GraphQLQuery(name = "nm_usuario")
	private String username;
	
	@Column(name = "ds_senha")
	@GraphQLIgnore
	private String password;
	
	@Transient
	@GraphQLIgnore
	private Date dateExp;

}
