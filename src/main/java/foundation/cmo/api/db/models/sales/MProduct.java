package foundation.cmo.api.db.models.sales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.Data;

@Data
@Entity(name = "Produto")
@GraphQLType(name = "Produto")
public class MProduct implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_produto")
	@GraphQLQuery(name = "cd_produto")
	private Long id;
	
	@Column(name = "cd_ean")
	@TableGenerator(name = "nr_seq", table = "Sequence")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "nr_seq")
	@GraphQLQuery(name = "cd_ean")
	private Long ean;
	
	@Column(name = "nm_produto")
	@GraphQLQuery(name = "nm_produto")
	private String name;
	
	@Column(name = "ds_categories")
	@GraphQLQuery(name = "ds_categories")
	private String categories;
	
	@Column(name = "ds_marcas")
	@GraphQLQuery(name = "ds_marcas")
	private String brands;
	
	@Column(name = "cd_ncm")
	@GraphQLQuery(name = "cd_ncm")
	private Long ncm;

}
