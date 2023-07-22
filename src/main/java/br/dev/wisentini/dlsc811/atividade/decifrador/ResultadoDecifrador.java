package br.dev.wisentini.dlsc811.atividade.decifrador;

public class ResultadoDecifrador {

    private final String ciphertext;

    private final String plaintext;

    private final String chave;

    public ResultadoDecifrador() {
        this.ciphertext = null;
        this.plaintext = null;
        this.chave = null;
    }

    public ResultadoDecifrador(String ciphertext, String plaintext, String chave) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.chave = chave;
    }

    public String getCiphertext() {
        return this.ciphertext;
    }

    public String getPlaintext() {
        return this.plaintext;
    }

    public String getChave() {
        return this.chave;
    }

    @Override
    public String toString() {
        return String.format(
            "\nCIPHERTEXT: %s\n\nPLAINTEXT: %s\n\nCHAVE: %s",
            this.ciphertext,
            this.plaintext,
            this.chave
        );
    }
}
