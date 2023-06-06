package foundation.cmo.api.db.models.location;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "Municipio")
public class MCity extends MLocation{
	@ManyToOne(optional = false)
	@JoinColumn(name = "cd_uf", columnDefinition = "id")
	private MState state;
}
