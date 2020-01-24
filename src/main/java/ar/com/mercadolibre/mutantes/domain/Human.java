package ar.com.mercadolibre.mutantes.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Human {

    private String[] dna;

    private boolean mutant;

    public Human(String[] dna, boolean isMutant) {
        this.dna = dna;
        this.mutant = isMutant;
    }
}
