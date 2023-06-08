package foundation.cmo.api.db.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MState;

public interface MCityRepository extends JpaRepository<MCity, Long> {
	List<MCity> findAllByState(MState state);

	List<MCity> findAllByStateAcronym(String acronym);
	List<MCity> findAllByStateAcronym(String acronym, Pageable pageable);

	List<MCity> findAllByNameContainingIgnoreCase(String name);
}
