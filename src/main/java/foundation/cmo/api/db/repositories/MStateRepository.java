package foundation.cmo.api.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.db.models.location.MState;

public interface MStateRepository extends JpaRepository<MState, Long> {
}
