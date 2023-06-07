package foundation.cmo.api.db.models.location;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(name = "Estado")
public class MState extends MLocation{
	@Column(name = "nm_estado")
	private String name;
}
