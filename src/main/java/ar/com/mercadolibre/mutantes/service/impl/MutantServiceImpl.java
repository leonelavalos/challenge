package ar.com.mercadolibre.mutantes.service.impl;

import ar.com.mercadolibre.mutantes.exception.DnaException;
import ar.com.mercadolibre.mutantes.service.MutantService;
import ar.com.mercadolibre.mutantes.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class MutantServiceImpl implements MutantService {

    @Autowired
    private StringUtil stringUtil;

    private static final String ALLOWED_NITRO_BASES = "[ATCG]+";

    private static final int MINIMUM_MUTANT_SEQUENCE = 4;

    @Override
    public boolean isMutant(String[] dna) {

        char[][] matrix = stringUtil.convertToCharArray(dna);
        int countMatched = 0;

        validateDna(dna);

        countMatched = searchHorizontally(matrix);

        if (countMatched > 1) return true;

        countMatched += searchVertically(matrix);

        if (countMatched > 1) return true;

        countMatched += searchDiagonally(matrix);

        if (countMatched > 1) return true;

        countMatched += searchAntiDiagonally(matrix);

        return countMatched > 1;
    }

    @Override
    public void validateDna(String[] dna) {
        if(Objects.isNull(dna) || dna.length == 0)
            throw new DnaException("ADN no puede ser nulo");

        if(dna.length < MINIMUM_MUTANT_SEQUENCE)
            throw new DnaException("Secuencias de ADN pequeña, minimo debe ser una matrix 4x4");

        if(Stream.of(dna).anyMatch(String::isEmpty))
            throw new DnaException("ADN no puede tener secuencias vacias");

        if(Stream.of(dna).anyMatch(s -> s.length() != dna.length))
            throw new DnaException("Las secuencias de ADN no son una matrix NxN");

        if(!Stream.of(dna).allMatch(s -> s.matches(ALLOWED_NITRO_BASES)))
            throw new DnaException("ADN no puede tener un caracter distinto a [A, T, C, G]");
    }

    private int searchAntiDiagonally(char[][] matrix) {
        int length = matrix.length;
        int countMatched = 0;

        for (int k = 0; k <= 2 * (length - 1); ++k) {
            int charRepetition = 1;
            int jMin = Math.max(0, k - matrix.length + 1);
            int jMax = Math.min(matrix.length - 1, k);
            for (int j = jMin; j < jMax; ++j) {
                int i = k - j;

                if (matrix[j][i] == matrix[j + 1][i - 1]) {
                    charRepetition++;
                } else {
                    charRepetition = 1;
                }

                if(charRepetition > 3) {
                    countMatched++;
                    charRepetition = 1;
                }
            }
        }

        return countMatched;
    }

    private int searchDiagonally(char[][] matrix) {
        int length = matrix.length;
        int countMatched = 0;

        for (int j = length-1; j >= 0; j--) {
            int charRepetition = 1;
            for (int k=0; k < length; k++) {
                if ((j + k + 1) < length) {
                    if (matrix[k][j + k] == matrix[k + 1 ][j + k + 1]) {
                        charRepetition++;
                    } else {
                        charRepetition = 1;
                    }

                    if(charRepetition > 3) {
                        countMatched++;
                        charRepetition = 1;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < length; i++) {
            int charRepetition = 1;
            for(int j=i, k=0; j<matrix.length-1 && k<matrix.length; j++, k++){
                if(matrix[j][k] == matrix[j + 1][k + 1]){
                    charRepetition++;
                } else {
                    charRepetition = 1;
                }

                if(charRepetition > 3) {
                    countMatched++;
                    charRepetition = 1;
                }
            }
        }

        return countMatched;
    }

    private int searchHorizontally(char[][] matrix) {
        int length = matrix.length;
        int countMatched = 0;

        for (int i = 0; i < length; i++) {
            int charRepetition = 1;
            for (int j = 0; j < length - 1; j++) {
                if (matrix[i][j] == matrix[i][j + 1]) {
                    charRepetition++;
                } else {
                    charRepetition = 1;
                }

                if (charRepetition > 3) {
                    countMatched++;
                    charRepetition = 1;
                }
            }
        }

        return countMatched;
    }

    private int searchVertically(char[][] matrix) {
        int length = matrix.length;
        int countMatched = 0;

        for (int j = 0; j < length; j++) {
            int charRepetition = 1;
            for (int i = 0; i < length - 1; i++) {
                if (matrix[i][j] == matrix[i+1][j]) {
                    charRepetition++;
                } else {
                    charRepetition = 1;
                }

                if (charRepetition > 3) {
                    countMatched++;
                    charRepetition = 1;
                }
            }
        }

        return countMatched;
    }
}
