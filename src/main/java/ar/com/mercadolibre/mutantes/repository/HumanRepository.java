package ar.com.mercadolibre.mutantes.repository;

import ar.com.mercadolibre.mutantes.domain.Human;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HumanRepository extends MongoRepository<Human, String>{

    Long countByMutant(boolean isMutant);
}
