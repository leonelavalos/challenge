package ar.com.mercadolibre.mutantes.repository;

import ar.com.mercadolibre.mutantes.domain.Human;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HumanRepository extends MongoRepository<Human, String>{

    Long countByMutant(boolean isMutant);
    Optional<Human> findByDna(String[] dna);
}
