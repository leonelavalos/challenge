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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {

    @InjectMocks
    private MutantServiceImpl mutantService;

    @Mock
    private HumanRepository humanRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testExampleMeLi() {
        String[] exampleMeLi = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertTrue(mutantService.isMutant(exampleMeLi));
    }

    @Test
    public void testHorizontalDNA() {
        String[] horizontalDNA = {
                "CCCCC",
                "ATCGT",
                "ATCGT",
                "CTTTT",
                "CAGAC"
        };
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertTrue(mutantService.isMutant(horizontalDNA));
    }

    @Test
    public void testVerticualDNA() {
        String[] verticalDNA = {
                "CGCGC",
                "ATCGT",
                "ATCGT",
                "CTCTT",
                "CAGAT"
        };
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertTrue(mutantService.isMutant(verticalDNA));
    }

    @Test
    public void testDiagonalDNA() {
        String[] diagonalDNA = {
                "CCACC",
                "ACCGT",
                "AACGT",
                "CTACA",
                "CAGAT"
        };
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertTrue(mutantService.isMutant(diagonalDNA));
    }

    @Test
    public void testAntiDiagonalDNA() {
        String[] antiDiagonalDNA = {
                "CGCCC",
                "ATCGT",
                "ACGGT",
                "CGCTT",
                "GAGAG"
        };
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertTrue(mutantService.isMutant(antiDiagonalDNA));
    }


    @Test
    public void testIsHuman() {
        String[] humanDNA = {
                "ATGCCA",
                "CTGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        verify(humanRepository, never()).save(Mockito.any(Human.class));
        assertFalse(mutantService.isMutant(humanDNA));
    }

    @Test
    public void testGetStats() {

        Long countHuman = 100L;
        Long countMutant = 40L;
        StatsDTO dto = new StatsDTO(countHuman, countMutant);

        when(humanRepository.countByMutant(false)).thenReturn(countHuman);
        when(humanRepository.countByMutant(true)).thenReturn(countMutant);

        assertEquals(mutantService.getStats(), dto);
    }

}
