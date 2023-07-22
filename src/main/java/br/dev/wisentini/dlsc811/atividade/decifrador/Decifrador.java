package br.dev.wisentini.dlsc811.atividade.decifrador;

import br.dev.wisentini.dlsc811.atividade.util.CriptografiaUtil;
import br.dev.wisentini.dlsc811.atividade.quebrador.Quebrador;

import java.util.Objects;

public class Decifrador {

    public static ResultadoDecifrador decifrarCiphertextBase16PorForcaBrutaLinguaPortuguesaASCII(String ciphertext, String chave) {
        if (Objects.isNull(chave)) {
            return new ResultadoDecifrador();
        }

        String plaintext = CriptografiaUtil.decifrar(ciphertext, chave);

        return new ResultadoDecifrador(ciphertext, plaintext, chave);
    }

    public static ResultadoDecifrador decifrarCiphertextBase16PorForcaBrutaLinguaPortuguesaASCII(String ciphertext) {
        String chave = Quebrador.quebrarCiphertextBase16PorForcaBrutaLinguaPortuguesaASCII(ciphertext);

        return decifrarCiphertextBase16PorForcaBrutaLinguaPortuguesaASCII(ciphertext, chave);
    }

    public static ResultadoDecifrador decifrarCiphertextBase16PorAnaliseFrequenciaLinguaPortuguesaASCII(String ciphertext, String chave) {
        String plaintext = CriptografiaUtil.decifrar(ciphertext, chave);

        return new ResultadoDecifrador(ciphertext, plaintext, chave);
    }

    public static ResultadoDecifrador decifrarCiphertextBase16PorAnaliseFrequenciaLinguaPortuguesaASCII(String ciphertext) {
        String chave = Quebrador.quebrarCiphertextBase16PorAnaliseFrequenciaLinguaPortuguesaASCII(ciphertext);

        return decifrarCiphertextBase16PorAnaliseFrequenciaLinguaPortuguesaASCII(ciphertext, chave);
    }

    public static ResultadoDecifrador decifrarCiphertextBase64LinguaPortuguesaASCII(String ciphertext) {
        String ciphertextBase16 = CriptografiaUtil.converterDeBase64ParaBase16(ciphertext);
        String chaveASCII = Quebrador.quebrarCiphertextBase64LinguaPortuguesaASCII(ciphertext);
        String chaveBase16 = CriptografiaUtil.codificarParaBase16(chaveASCII);
        String plaintext = CriptografiaUtil.decifrar(ciphertextBase16, chaveBase16);

        return new ResultadoDecifrador(ciphertext, plaintext, chaveASCII);
    }
}
