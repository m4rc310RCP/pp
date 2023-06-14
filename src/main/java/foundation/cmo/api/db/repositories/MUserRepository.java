package foundation.cmo.api.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.db.models.auth.MUser;

public interface MUserRepository extends JpaRepository<MUser, String> {
}
