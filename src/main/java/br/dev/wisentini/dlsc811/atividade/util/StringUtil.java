package br.dev.wisentini.dlsc811.atividade.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringUtil {

    public static List<String> dividirStringACadaNCaracteres(String string, int n) {
        if (string == null) {
            return new ArrayList<>();
        }

        if (n < 0 || n > string.length()) {
            return new ArrayList<>(){{
                add(string);
            }};
        }

        List<String> resultado = new ArrayList<>();

        int tamanho = string.length();

        for (int i = 0; i < tamanho; i += n) {
            if (i + n < tamanho) {
                resultado.add(string.substring(i, i + n));
            } else {
                resultado.add(string.substring(i));
            }
        }

        return resultado;
    }

    public static String removerCaracteresDeControle(String string) {
        if (Objects.isNull(string)) {
            return null;
        }

        return string.replaceAll("\\p{Cntrl}", "");
    }
}
