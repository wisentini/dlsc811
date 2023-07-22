package br.dev.wisentini.dlsc811.atividade.quebrador;

import br.dev.wisentini.dlsc811.atividade.analisadorfrequencia.AnalisadorFrequencia;
import br.dev.wisentini.dlsc811.atividade.analisadorfrequencia.ResultadoAnalisadorFrequencia;
import br.dev.wisentini.dlsc811.atividade.util.ConversorUtil;
import br.dev.wisentini.dlsc811.atividade.util.CriptografiaUtil;

import java.util.*;

public class Quebrador {

    private static final double MELHOR_SEMELHANCA_ENCONTRADA_INICIAL = -Double.MAX_VALUE;

    public static String quebrarCiphertextBase16PorForcaBrutaLinguaPortuguesaASCII(String ciphertext) {
        int tamanhoCiphertextASCII = ciphertext.length() / 2;
        double melhorSemelhancaEncontrada = MELHOR_SEMELHANCA_ENCONTRADA_INICIAL;
        String chaveProvavel = null;

        for (int i = 0; i <= 32; i += 32) {
            for (int j = 65 + i; j <= 90 + i; j++) {
                String chaveBase16 = String.valueOf(String.format("%X", j)).repeat(tamanhoCiphertextASCII);

                String plaintext = CriptografiaUtil.decifrar(ciphertext, chaveBase16);

                ResultadoAnalisadorFrequencia resultadoAnaliseFrequenciaPlaintext = AnalisadorFrequencia.analisar(plaintext, 1);

                double semelhancaFrequenciaLetrasLinguasPortuguesa = resultadoAnaliseFrequenciaPlaintext.calcularSemelhancaFrequenciaLetrasLinguaPortuguesa();

                if (semelhancaFrequenciaLetrasLinguasPortuguesa > melhorSemelhancaEncontrada) {
                    melhorSemelhancaEncontrada = semelhancaFrequenciaLetrasLinguasPortuguesa;
                    chaveProvavel = Character.toString((char) j);
                };
            }
        }

        return chaveProvavel;
    }

    public static String quebrarCiphertextBase16PorAnaliseFrequenciaLinguaPortuguesaASCII(String ciphertext) {
        ResultadoAnalisadorFrequencia resultadoAnaliseFrequenciaCiphertext = AnalisadorFrequencia.analisar(ciphertext, 2);

        int tamanhoCiphertextASCII = ciphertext.length() / 2;
        double melhorSemelhancaEncontrada = MELHOR_SEMELHANCA_ENCONTRADA_INICIAL;
        String chaveProvavel = null;

        for (Map.Entry<String, Integer> entrada : resultadoAnaliseFrequenciaCiphertext.obterTabelaFrequencia().entrySet()) {
            double frequenciaRelativa = (double) entrada.getValue() / tamanhoCiphertextASCII;

            Character possivelChave = AnalisadorFrequencia.encontrarLetraCorrespondenteLinguaPortuguesaPorFrequencia(frequenciaRelativa);

            if (Objects.isNull(possivelChave)) continue;

            String chave = CriptografiaUtil.decifrar(entrada.getKey(), Integer.toHexString(possivelChave));

            String plaintext = CriptografiaUtil.decifrar(ciphertext, CriptografiaUtil.codificarParaBase16(chave));

            ResultadoAnalisadorFrequencia resultadoAnaliseFrequenciaPlaintext = AnalisadorFrequencia.analisar(plaintext, 1);

            double semelhancaFrequenciaLetrasLinguasPortuguesa = resultadoAnaliseFrequenciaPlaintext.calcularSemelhancaFrequenciaLetrasLinguaPortuguesa();

            if (semelhancaFrequenciaLetrasLinguasPortuguesa > melhorSemelhancaEncontrada) {
                melhorSemelhancaEncontrada = semelhancaFrequenciaLetrasLinguasPortuguesa;
                chaveProvavel = chave;
            }
        }

        return chaveProvavel;
    }

    private static int encontrarTamanhoProvavelChave(byte[] ciphertext) {
        Map<Integer, Integer> distanciasHammingNormalizadasKeySize = new HashMap<>();

        for (int keySize = 2; keySize <= 40; keySize++) {
            int numeroBlocos = ciphertext.length / keySize;

            byte[][] blocos = new byte[numeroBlocos][];

            int from = 0;
            int to = keySize;

            for (int i = 0; i < numeroBlocos; i++) {
                if (from >= ciphertext.length) break;

                blocos[i] = Arrays.copyOfRange(ciphertext, from, Math.min(to, ciphertext.length));
                from = to;
                to += keySize;
            }

            int somaDistanciaHammingBlocosNormalizada = 0;

            for (int i = 0; i < blocos.length - 1; i++) {
                byte[] primeiroBloco = blocos[i];
                byte[] segundoBloco = blocos[i + 1];

                if (primeiroBloco.length != segundoBloco.length) break;

                int distanciaHammingBlocos = CriptografiaUtil.calcularDistanciaDeHamming(primeiroBloco, segundoBloco);

                somaDistanciaHammingBlocosNormalizada += distanciaHammingBlocos / keySize;
            }

            distanciasHammingNormalizadasKeySize.put(keySize, somaDistanciaHammingBlocosNormalizada / blocos.length);
        }

        return Collections.min(distanciasHammingNormalizadasKeySize.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static List<List<Byte>> criarBlocos(byte[] ciphertext, int tamanhoProvavelChave) {
        List<List<Byte>> blocos = new ArrayList<>();

        for (int i = 0; i < ciphertext.length; i += tamanhoProvavelChave) {
            byte[] bytes = Arrays.copyOfRange(ciphertext, i, Math.min(i + tamanhoProvavelChave, ciphertext.length));
            blocos.add(ConversorUtil.converterArrayDeBytesParaListaDeBytes(bytes));
        }

        return blocos;
    }

    private static List<List<Byte>> transporBlocos(List<List<Byte>> blocos, int tamanhoProvavelChave) {
        List<List<Byte>> blocosTranspostos = new ArrayList<>();

        for (int i = 0; i < tamanhoProvavelChave; i++) {
            List<Byte> blocoTransposto = new ArrayList<>();

            for (List<Byte> bloco : blocos) {
                if (i < bloco.size()) {
                    blocoTransposto.add(bloco.get(i));
                }
            }

            blocosTranspostos.add(blocoTransposto);
        }

        return blocosTranspostos;
    }

    public static String quebrarCiphertextBase64LinguaPortuguesaASCII(String ciphertextBase64) {
        byte[] ciphertext = CriptografiaUtil.decodificarDeBase64(ciphertextBase64);

        int tamanhoProvavelChave = encontrarTamanhoProvavelChave(ciphertext);

        List<List<Byte>> blocos = criarBlocos(ciphertext, tamanhoProvavelChave);

        List<List<Byte>> blocosTranspostos = transporBlocos(blocos, tamanhoProvavelChave);

        StringBuilder chave = new StringBuilder();

        for (List<Byte> blocoTransposto : blocosTranspostos) {
            String chaveProvavel = quebrarCiphertextBase16PorAnaliseFrequenciaLinguaPortuguesaASCII(
                CriptografiaUtil.codificarParaBase16(
                    ConversorUtil.converterListaDeBytesParaArrayDeBytes(blocoTransposto)
                )
            );

            chave.append(chaveProvavel);
        }

        return chave.toString();
    }
}
