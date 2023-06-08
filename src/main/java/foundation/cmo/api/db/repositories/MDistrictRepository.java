package foundation.cmo.api.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.db.models.location.MCity;
import foundation.cmo.api.db.models.location.MDistrict;

public interface MDistrictRepository extends JpaRepository<MDistrict, Long> {
	List<MDistrict> findAllByCity(MCity city);
}
