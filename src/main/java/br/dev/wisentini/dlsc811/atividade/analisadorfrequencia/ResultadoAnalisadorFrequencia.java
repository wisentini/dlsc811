package br.dev.wisentini.dlsc811.atividade.analisadorfrequencia;

import java.util.*;
import java.util.stream.Collectors;

public class ResultadoAnalisadorFrequencia {

    private final Map<String, Integer> resultado;

    public ResultadoAnalisadorFrequencia() {
        this.resultado = new HashMap<>();
    }

    public ResultadoAnalisadorFrequencia(Map<String, Integer> resultado) {
        this.resultado = this.ordenarResultadoPelaFrequenciaEmOrdemDecrescente(resultado);
    }

    private Map<String, Integer> ordenarResultadoPelaFrequenciaEmOrdemDecrescente(Map<String, Integer> resultado) {
        return resultado
            .entrySet()
            .stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new
            ));
    }

    public Map<String, Integer> obterTabelaFrequencia() {
        return this.resultado;
    }

    public ResultadoAnalisadorFrequencia obterOsNTokensMaisFrequentes(int n) {
        Map<String, Integer> resultado = new LinkedHashMap<>();

        Iterator<Map.Entry<String, Integer>> iterator = this.resultado.entrySet().iterator();

        int i = 0;

        while (iterator.hasNext() && i++ < n) {
            Map.Entry<String, Integer> entrada = iterator.next();

            resultado.put(entrada.getKey(), entrada.getValue());
        }

        return new ResultadoAnalisadorFrequencia(resultado);
    }

    private int obterTamanhoMaiorToken() {
        String maiorToken = "";

        for (String token : this.resultado.keySet()) {
            if (token.compareTo(maiorToken) > 0) {
                maiorToken = token;
            }
        }

        return maiorToken.length();
    }

    private void imprimirLinhaCabecalho(int larguraColuna) {
        System.out.printf("| %1$-" + larguraColuna + "s | %2$" + larguraColuna + "s |\n", "Token", "Frequência");
    }

    private void imprimirLinhaDivisora(int larguraColuna) {
        for (int i = 0; i < 2; i++) {
            System.out.print("| ");

            for (int j = 0; j < larguraColuna; j++) {
                System.out.print("-");
            }

            System.out.print(" ");
        }

        System.out.println("|");
    }

    private void imprimirLinhaConteudo(Map.Entry<String, Integer> conteudo, int larguraColuna) {
        System.out.printf("| %1$-" + larguraColuna + "s | %2$" + larguraColuna + "s |\n", conteudo.getKey(), conteudo.getValue());
    }

    public void imprimir() {
        System.out.println();

        int larguraColuna = Math.max(this.obterTamanhoMaiorToken(), 10);

        this.imprimirLinhaCabecalho(larguraColuna);
        this.imprimirLinhaDivisora(larguraColuna);

        int i = 0;

        for (Map.Entry<String, Integer> conteudo : this.resultado.entrySet()) {
            this.imprimirLinhaConteudo(conteudo, larguraColuna);

            if (i < this.resultado.size() - 1) {
                this.imprimirLinhaDivisora(larguraColuna);
            }

            i++;
        }

        System.out.println();
    }

    public double calcularSemelhancaFrequenciaLetrasLinguaPortuguesa() {
        List<String> letrasResultado = new ArrayList<>(this.resultado.keySet());
        List<String> letrasLinguaPortuguesa = AnalisadorFrequencia.FREQUENCIA_LETRAS_LINGUA_PORTUGUESA
            .keySet()
            .stream()
            .map(String::valueOf)
            .collect(Collectors.toList());

        int n = 0;

        for (int i = 0; i < letrasResultado.size(); i++) {
            n += Math.abs(letrasLinguaPortuguesa.indexOf(letrasResultado.get(i)) - i);
        }

       return 1 - n / (letrasLinguaPortuguesa.size() * (double) (letrasLinguaPortuguesa.size() + 1) / 2);
    }
}
