package br.dev.wisentini.dlsc811.atividade;

import br.dev.wisentini.dlsc811.atividade.decifrador.Decifrador;
import br.dev.wisentini.dlsc811.atividade.decifrador.ResultadoDecifrador;
import br.dev.wisentini.dlsc811.atividade.util.ArquivoUtil;

import java.io.IOException;

public class Main {

    private final static String CAMINHO_ARQUIVO_CIPHERTEXT = "cifra.base64";

    public static void main(String[] args) throws IOException {
        String ciphertextBase64 = ArquivoUtil.lerArquivoDoDiretorioResources(CAMINHO_ARQUIVO_CIPHERTEXT);

        ResultadoDecifrador resultadoDecifrador = Decifrador.decifrarCiphertextBase64LinguaPortuguesaASCII(ciphertextBase64);

        System.out.println(resultadoDecifrador);
    }
}
