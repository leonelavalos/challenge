package ar.com.mercadolibre.mutantes.util;

public class StringUtil {

    public static char[][] convertToCharArray(String[] arrayString) {
        char[][] matrix = new char[arrayString.length][arrayString.length];

        for (int i = 0; i < arrayString.length; i++) {
            matrix[i] = arrayString[i].toCharArray();
        }

        return matrix;
    }
}
