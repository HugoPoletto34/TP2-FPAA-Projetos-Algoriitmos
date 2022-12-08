package org.example.entities;

import java.util.Arrays;

public class CelulaPD {
    public int[] array;
    public double indiceBalanceamento;

    public CelulaPD(int[] array, double indiceBalanceamento) {
        this.array = array;
        this.indiceBalanceamento = indiceBalanceamento;
    }

    public CelulaPD() {
    }

    @Override
    public String toString() {
        return "Celula [array=" + Arrays.toString(array) + ", indiceBalanceamento=" + indiceBalanceamento + "]";
    }

    public int[] getArray() {
        return array;
    }

    public double getIndiceBalanceamento() {
        return indiceBalanceamento;
    }
}