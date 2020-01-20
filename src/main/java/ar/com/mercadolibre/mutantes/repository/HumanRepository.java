package ar.com.mercadolibre.mutantes.repository;

import ar.com.mercadolibre.mutantes.domain.Human;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumanRepository extends JpaRepository<Human, Long> {

    Long countByMutant(boolean isMutant);
}
