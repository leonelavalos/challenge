package ar.com.mercadolibre.mutantes.service.impl;

import ar.com.mercadolibre.mutantes.domain.Human;
import ar.com.mercadolibre.mutantes.dto.StatsDTO;
import ar.com.mercadolibre.mutantes.exception.DnaException;
import ar.com.mercadolibre.mutantes.repository.HumanRepository;
import ar.com.mercadolibre.mutantes.service.MessageService;
import ar.com.mercadolibre.mutantes.service.MutantService;
import ar.com.mercadolibre.mutantes.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MutantServiceImpl implements MutantService {

    private static final String ALLOWED_NITROGEN_BASES = "[ATCG]+";

    private static final int MINIMUM_GEN_SEQUENCE = 4;

    private static final int VALID_SEQUENCE_COUNT = 2;

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public boolean isMutant(String[] dna) {

        Optional<Human> human = humanRepository.findByDna(dna);
        if(human.isPresent())
           return human.get().isMutant();

        char[][] matrix = StringUtil.convertToCharArray(dna);
        boolean isMutant;

        isMutant = analyzeDNA(matrix);
        save(dna, isMutant);

        return isMutant;
    }

    @Override
    public boolean isValid(String[] dna) {
        if(Objects.isNull(dna) || dna.length == 0)
            throw new DnaException(messageService.getMessage("dna.empty"));

        if(Stream.of(dna).anyMatch(String::isEmpty))
            throw new DnaException(messageService.getMessage("dna.sequence.empty"));

        if(dna.length < MINIMUM_GEN_SEQUENCE)
            throw new DnaException(messageService.getMessage("dna.sequence.minimum.mutant"));

        if(Stream.of(dna).anyMatch(s -> s.length() != dna.length))
            throw new DnaException(messageService.getMessage("dna.invalid.format.table"));

        if(!Stream.of(dna).allMatch(s -> s.matches(ALLOWED_NITROGEN_BASES)))
            throw new DnaException(messageService.getMessage("dna.invalid.nitrogen.bases"));

        return true;
    }

    @Override
    public void save(String[] dna, boolean isMutant) {
        Human newHuman = new Human(dna, isMutant);
        humanRepository.save(newHuman);
    }

    @Override
    public StatsDTO getStats() {
        Long countHuman = humanRepository.countByMutant(false);
        Long countMutant = humanRepository.countByMutant(true);

        return new StatsDTO(countHuman, countMutant);
    }

    public boolean analyzeDNA(char[][] matrix) {
        int findedDna = 0;

        findedDna = searchHorizontally(matrix, findedDna);
        if(findedDna >= VALID_SEQUENCE_COUNT)
            return true;

        findedDna = searchVertically(matrix, findedDna);
        if(findedDna >= VALID_SEQUENCE_COUNT)
            return true;

        findedDna = searchAntiDiagonally(matrix, findedDna);
        if(findedDna >= VALID_SEQUENCE_COUNT)
            return true;

        findedDna = searchDiagonally(matrix, findedDna);
        if(findedDna >= VALID_SEQUENCE_COUNT)
            return true;

        return false;
    }
    
    private int searchHorizontally(char[][] matrix, int findedDna) {
        int length = matrix.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= length - MINIMUM_GEN_SEQUENCE; j++) {
                if (StringUtil.areEqual(matrix[i][j], matrix[i][j + 1], matrix[i][j + 2], matrix[i][j + 3])) {
                    findedDna++;
                    j += MINIMUM_GEN_SEQUENCE;
                }
                if (findedDna >= VALID_SEQUENCE_COUNT) {
                    return VALID_SEQUENCE_COUNT;
                }
            }
        }

        return findedDna;
    }

    private int searchVertically(char[][] matrix, int findedDna) {
        int length = matrix.length;

        for (int j = 0; j < length; j++) {
            for (int i = 0; i <= length - MINIMUM_GEN_SEQUENCE; i++) {
                if (StringUtil.areEqual(matrix[i][j], matrix[i + 1][j], matrix[i + 2][j], matrix[i + 3][j])) {
                    findedDna++;
                    i += MINIMUM_GEN_SEQUENCE;
                }
                if (findedDna >= VALID_SEQUENCE_COUNT) {
                    return VALID_SEQUENCE_COUNT;
                }
            }
        }

        return findedDna;
    }

    private int searchDiagonally(char[][] matrix, int findedDna) {
        int length = matrix.length;

        for (int j = length-1; j >= 0; j--) {
            int consecutiveGen = 1;
            for (int k=0; k < length; k++) {
                if ((j + k + 1) < length) {

                    if (matrix[k][j + k] == matrix[k + 1 ][j + k + 1]) {
                        consecutiveGen++;
                    } else {
                        consecutiveGen = 1;
                    }

                    if (consecutiveGen >= MINIMUM_GEN_SEQUENCE) {
                        findedDna++;
                        consecutiveGen = 1;
                    }

                    if (findedDna >= VALID_SEQUENCE_COUNT) {
                        return VALID_SEQUENCE_COUNT;
                    }

                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < length; i++) {
            int consecutiveGen = 1;
            for (int j = i, k = 0; j < matrix.length - 1 && k < matrix.length; j++, k++){
                if (matrix[j][k] == matrix[j + 1][k + 1]){
                    consecutiveGen++;
                } else {
                    consecutiveGen = 1;
                }

                if (consecutiveGen >= MINIMUM_GEN_SEQUENCE) {
                    findedDna++;
                    consecutiveGen = 1;
                }

                if (findedDna >= VALID_SEQUENCE_COUNT) {
                    return VALID_SEQUENCE_COUNT;
                }
            }
        }

        return findedDna;
    }

    private int searchAntiDiagonally(char[][] matrix, int findedDna) {
        int length = matrix.length;

        for (int k = 0; k <= 2 * (length - 1); ++k) {
            int consecutiveGen = 1;
            int jMin = Math.max(0, k - matrix.length + 1);
            int jMax = Math.min(matrix.length - 1, k);
            for (int j = jMin; j < jMax; ++j) {
                int i = k - j;

                if (matrix[j][i] == matrix[j + 1][i - 1]) {
                    consecutiveGen++;
                } else {
                    consecutiveGen = 1;
                }

                if (consecutiveGen >= MINIMUM_GEN_SEQUENCE) {
                    findedDna++;
                    consecutiveGen = 1;
                }

                if (findedDna >= VALID_SEQUENCE_COUNT) {
                    return VALID_SEQUENCE_COUNT;
                }
            }
        }

        return findedDna;
    }
}
