package foundation.cmo.api.db.models.location;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class MLocation {
	@Id
	@Column(name = "cd_ibge")
	private Long id;
}
