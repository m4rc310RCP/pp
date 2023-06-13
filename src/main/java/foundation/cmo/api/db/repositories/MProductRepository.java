package foundation.cmo.api.db.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import foundation.cmo.api.db.models.sales.MProduct;

public interface MProductRepository extends JpaRepository<MProduct, Long> {

	Optional<MProduct> findByIdOrEan(Long id, Long ean);

	Optional<MProduct> findByEan(Long ean);
}
