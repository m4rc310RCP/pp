package foundation.cmo.api.db.models.location;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "Distrito")
public class MDistrict extends MLocation{
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "cd_cidade", columnDefinition = "id")
	private MCity city;
}
