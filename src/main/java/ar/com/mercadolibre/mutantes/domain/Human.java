package ar.com.mercadolibre.mutantes.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "human")
public class Human {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    private String[] dna;

    private boolean mutant;
}
