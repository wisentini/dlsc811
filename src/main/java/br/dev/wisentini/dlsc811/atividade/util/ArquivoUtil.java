package br.dev.wisentini.dlsc811.atividade.util;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

public class ArquivoUtil {

    public static String lerArquivoDoDiretorioResources(String caminhoArquivo) throws IOException {
        ClassLoader classLoader = ArquivoUtil.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(caminhoArquivo)) {
            if (inputStream == null) {
                throw new IllegalArgumentException(
                    String.format("O arquivo \"%s\" não foi encontrado no diretório \"resources\".", caminhoArquivo)
                );
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
