package ar.com.mercadolibre.mutantes.util;

public class StringUtil {

    public static char[][] convertToCharArray(String[] arrayString) {
        char[][] matrix = new char[arrayString.length][arrayString.length];

        for (int i = 0; i < arrayString.length; i++) {
            matrix[i] = arrayString[i].toCharArray();
        }

        return matrix;
    }

    public static boolean areEqual(char a, char b, char c, char d) {
        return a == b && b == c && c == d;
    }
}
