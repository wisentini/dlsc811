package br.dev.wisentini.dlsc811.atividade.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConversorUtil {

    public static byte[] converterListaDeBytesParaArrayDeBytes(List<Byte> listaBytes) {
        if (Objects.isNull(listaBytes)) {
            return new byte[0];
        }

        byte[] arrayBytes = new byte[listaBytes.size()];

        for (int i = 0; i < listaBytes.size(); i++) {
            arrayBytes[i] = listaBytes.get(i);
        }

        return arrayBytes;
    }

    public static List<Byte> converterArrayDeBytesParaListaDeBytes(byte[] arrayBytes) {
        List<Byte> lista = new ArrayList<>();

        for (byte b : arrayBytes) {
            lista.add(b);
        }

        return lista;
    }
}
