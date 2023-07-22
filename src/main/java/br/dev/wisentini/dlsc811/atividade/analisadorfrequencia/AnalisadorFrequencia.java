package br.dev.wisentini.dlsc811.atividade.analisadorfrequencia;

import br.dev.wisentini.dlsc811.atividade.util.StringUtil;

import java.util.*;

public class AnalisadorFrequencia {

    // Fonte: https://www.gta.ufrj.br/grad/06_2/alexandre/criptoanalise.html
    public static final Map<Character, Double> FREQUENCIA_LETRAS_LINGUA_PORTUGUESA = new LinkedHashMap<>() {{
        put('a', 0.1463); put('e', 0.1257); put('o', 0.1073); put('s', 0.0781); put('r', 0.0653);
        put('i', 0.0618); put('n', 0.0505); put('d', 0.0499); put('m', 0.0474); put('u', 0.0463);
        put('t', 0.0434); put('c', 0.0388); put('l', 0.0278); put('p', 0.0252); put('v', 0.0167);
        put('g', 0.0130); put('h', 0.0128); put('q', 0.0120); put('b', 0.0104); put('f', 0.0102);
        put('z', 0.0047); put('j', 0.0040); put('x', 0.0021); put('k', 0.0002); put('w', 0.0001);
        put('y', 0.0001);
    }};

    private static final double DIFERENCA_MAXIMA_ACEITAVEL_FREQUENCIA = 0.015d;

    public static ResultadoAnalisadorFrequencia analisar(String string, int n) {
        if (Objects.isNull(string) || n < 0 || n > string.length()) {
            return new ResultadoAnalisadorFrequencia();
        }

        String stringSemCaracteresDeControle = StringUtil.removerCaracteresDeControle(string);

        List<String> substrings = StringUtil.dividirStringACadaNCaracteres(stringSemCaracteresDeControle, n);

        Map<String, Integer> resultado = new HashMap<>();

        for (String substring : substrings) {
            resultado.merge(substring.toLowerCase(), 1, Integer::sum);
        }

        return new ResultadoAnalisadorFrequencia(resultado);
    }

    public static Character encontrarLetraCorrespondenteLinguaPortuguesaPorFrequencia(double frequencia) {
        for (Map.Entry<Character, Double> frequenciaLetraLinguaPortuguesa : FREQUENCIA_LETRAS_LINGUA_PORTUGUESA.entrySet()) {
            if (Math.abs(frequencia - frequenciaLetraLinguaPortuguesa.getValue()) <= DIFERENCA_MAXIMA_ACEITAVEL_FREQUENCIA) {
                return frequenciaLetraLinguaPortuguesa.getKey();
            }
        }

        return null;
    }
}
