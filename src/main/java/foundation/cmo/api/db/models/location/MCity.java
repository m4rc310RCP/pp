package foundation.cmo.api.db.models.location;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "Municipio")
public class MCity extends MLocation{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "nm_municipio")
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cd_uf", referencedColumnName = "ds_sigla")
	private MState state;
}
