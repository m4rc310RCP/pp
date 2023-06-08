package foundation.cmo.api.db.models.location;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class MLocation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "cd_ibge")
	private Long id;
}
