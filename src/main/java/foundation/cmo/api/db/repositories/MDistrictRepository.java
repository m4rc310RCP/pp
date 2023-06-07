package foundation.cmo.api.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.db.models.location.MDistrict;

public interface MDistrictRepository extends JpaRepository<MDistrict, Long> {
}
