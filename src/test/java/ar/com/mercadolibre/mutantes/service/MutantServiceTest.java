package ar.com.mercadolibre.mutantes.service;


import ar.com.mercadolibre.mutantes.domain.Human;
import ar.com.mercadolibre.mutantes.dto.StatsDTO;
import ar.com.mercadolibre.mutantes.repository.HumanRepository;
import ar.com.mercadolibre.mutantes.service.impl.MutantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {

    private final String[] DNA_MUTANT_DIAG = { "ACGGAG", "CAGGCA", "TCACAT", "TACACT", "ACGGGC", "GAGGCG" };
    private final String[] DNA_MUTANT_H_V = { "AAAACT", "CTCTGC", "GAGAGT", "TCAGGT", "CACGGC", "ATTGCT" };
    private final String[] DNA_NOT_MUTANT = { "ATGCCA", "CTGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

    @InjectMocks
    private MutantServiceImpl mutantService;

    @Mock
    private HumanRepository humanRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHumanIsMutant() {
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertTrue(mutantService.isMutant(DNA_MUTANT_DIAG));
        assertTrue(mutantService.isMutant(DNA_MUTANT_H_V));
    }

    @Test
    public void testHumanIsNotMutant() {
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertFalse(mutantService.isMutant(DNA_NOT_MUTANT));
    }

    @Test
    public void testGetStats() {
        StatsDTO stats = mutantService.getStats();
        assertNotNull(stats);
    }

}
