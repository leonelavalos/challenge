package ar.com.mercadolibre.mutantes.service;

public interface MutantService {

    boolean isMutant(String[] dna);
    void validateDna(String[] dna);

}

