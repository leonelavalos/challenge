package ar.com.mercadolibre.mutantes.service;

import ar.com.mercadolibre.mutantes.dto.StatsDTO;

public interface MutantService {

    boolean isMutant(String[] dna);
    void validateDna(String[] dna);
    void save(String[] dna, boolean isMutant);
    StatsDTO getStats();
}

