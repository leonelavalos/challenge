package ar.com.mercadolibre.mutantes.service;

import ar.com.mercadolibre.mutantes.dto.StatsDTO;

public interface MutantService {

    boolean isMutant(String[] dna);
    boolean isValid(String[] dna);
    void save(String[] dna, boolean isMutant);
    StatsDTO getStats();

}

