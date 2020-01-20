package ar.com.mercadolibre.mutantes.dto;

import lombok.Data;

@Data
public class StatsDTO {

    private Long countMutantDna;

    private Long countHumanDna;

    private Double ratio;

    public StatsDTO(Long countHuman, Long countMutant) {
        this.countHumanDna = countHuman;
        this.countMutantDna = countMutant;
        this.ratio = countHuman == 0 ? 1d : (double)countMutant/(double)countHuman;
    }
}
