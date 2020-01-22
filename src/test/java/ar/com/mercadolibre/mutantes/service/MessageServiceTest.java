package ar.com.mercadolibre.mutantes.service;

import ar.com.mercadolibre.mutantes.exception.DnaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageServiceTest {

    private final String[] DNA_NULL = { };
    private final String[] DNA_SMALL = { "ABC", "DEF", "GHI" };
    private final String[] DNA_SEQUENCE_EMPTY = { "", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
    private final String[] DNA_NxM = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA" };
    private final String[] DNA_INVALID_NITROGEN_BASE = { "ATGCGA", "CAGTGC", "TTAXGT", "AGAAGG", "CCCCTA", "TCACTG" };

    @Autowired
    private MutantService mutantService;

    @Test
    public void testInvalidDna1() {
        Exception exception = assertThrows(DnaException.class, () -> mutantService.isMutant(DNA_NULL));

        String expectedMessage = "El ADN no puede ser nulo";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testInvalidDna2() {
        Exception exception = assertThrows(DnaException.class, () -> mutantService.isMutant(DNA_SMALL));

        String expectedMessage = "Las secuencias de ADN son pequeñas, minimo debe ser una matrix 4x4";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testInvalidDna3() {
        Exception exception = assertThrows(DnaException.class, () -> mutantService.isMutant(DNA_SEQUENCE_EMPTY));

        String expectedMessage = "El ADN no puede tener secuencias vacias";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testInvalidDna4() {
        Exception exception = assertThrows(DnaException.class, () -> mutantService.isMutant(DNA_NxM));

        String expectedMessage = "Las secuencias de ADN no son una matrix NxN";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testInvalidDna5() {
        Exception exception = assertThrows(DnaException.class, () -> mutantService.isMutant(DNA_INVALID_NITROGEN_BASE));

        String expectedMessage = "El ADN no puede tener bases nitrogenadas distintas a [A, T, C, G]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
