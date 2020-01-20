package ar.com.mercadolibre.mutantes.dto;

import lombok.Data;

@Data
public class StatsDTO {

    private Long countMutantDna;

    private Long countHumanDna;

    private Double ratio;
}
