package br.dev.wisentini.dlsc811.atividade.util;

import com.google.common.io.BaseEncoding;

import java.nio.charset.StandardCharsets;

import java.util.Objects;

public class CriptografiaUtil {

    public static String codificarParaBase64(byte[] bytes) {
        return BaseEncoding.base64().encode(bytes);
    }

    public static String codificarParaBase64(String string) {
        return codificarParaBase64(string.getBytes());
    }

    public static byte[] decodificarDeBase64(String string) {
        return BaseEncoding.base64().decode(string);
    }

    public static String codificarParaBase16(byte[] bytes) {
        return BaseEncoding.base16().encode(bytes).toUpperCase();
    }

    public static String codificarParaBase16(String string) {
        return BaseEncoding.base16().encode(string.getBytes()).toUpperCase();
    }

    public static byte[] decodificarDeBase16(String string) {
        return BaseEncoding.base16().decode(string.toUpperCase());
    }

    public static String codificarParaASCII(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String converterDeBase64ParaBase16(String string) {
        return codificarParaBase16(decodificarDeBase64(string));
    }

    public static String converterDeBase16ParaBase64(String string) {
        return codificarParaBase64(decodificarDeBase16(string));
    }

    public static byte[] cifrarComOperacaoXOR(byte[] buffer1, byte[] buffer2) {
        if (Objects.isNull(buffer1) || Objects.isNull(buffer2)) return null;

        byte[] maiorBuffer;
        byte[] menorBuffer;

        if (buffer1.length >= buffer2.length) {
            maiorBuffer = buffer1;
            menorBuffer = buffer2;
        } else {
            maiorBuffer = buffer2;
            menorBuffer = buffer1;
        }

        byte[] resultado = new byte[maiorBuffer.length];

        for (int i = 0; i < maiorBuffer.length; i++) {
            resultado[i] = (byte) (maiorBuffer[i] ^ menorBuffer[i % menorBuffer.length]);
        }

        return resultado;
    }

    public static int calcularDistanciaDeHamming(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            throw new IllegalArgumentException("Para o cálculo da distância de Hamming, os arrays de bytes fornecidos devem ser do mesmo tamanho");
        }

        int distancia = 0;

        for (int i = 0; i < bytes1.length; i++) {
            byte xor = (byte) (bytes1[i] ^ bytes2[i]);

            while (xor != 0) {
                if ((xor & 1) == 1) {
                    distancia++;
                }

                xor >>= 1;
            }
        }

        return distancia;
    }

    /**
     *
     * @param ciphertextBase16 o ciphertext codificado em Base16
     * @param chaveBase16      a chave codificada em Base16
     * @return                 o plaintext codificado em ASCII
     */
    public static String decifrar(String ciphertextBase16, String chaveBase16) {
        return CriptografiaUtil.codificarParaASCII(
            CriptografiaUtil.cifrarComOperacaoXOR(
                CriptografiaUtil.decodificarDeBase16(ciphertextBase16),
                CriptografiaUtil.decodificarDeBase16(chaveBase16)
            )
        );
    }
}
