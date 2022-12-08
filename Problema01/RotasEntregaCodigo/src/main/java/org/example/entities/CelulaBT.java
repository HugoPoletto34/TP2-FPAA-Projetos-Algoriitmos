package org.example.entities;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CelulaBT {
    public List<Caminhao> array;
    public double indiceBalanceamento;
    public CelulaBT[] filhos;

//    public CelulaBT(int valor, int qtdFilhos) {
//        this.array = new int[qtdFilhos];
//        this.array[0] += valor;
//        this.filhos = gerarFilhos(valor, qtdFilhos);
//    }

    public void gerarFilhos(Rota valor, int qtdFilhos) {
        CelulaBT[] filhos = new CelulaBT[array.size()];
        for (int i = 0; i < qtdFilhos; i++) {
            List<Caminhao> arrayClone = array.stream().map(Caminhao::clone).collect(Collectors.toList());
            arrayClone.get(i).addRota(valor);
            filhos[i] = new CelulaBT(qtdFilhos);
            filhos[i].array = arrayClone;
            filhos[i].indiceBalanceamento = indiceDeBalanceamento(filhos[i].array);
        }

        this.filhos = filhos;
        this.podar();

    }

    public CelulaBT(int qtdFilhos) {
        this.array = new LinkedList<>();
        for (int i = 0; i < qtdFilhos; i++) {
            this.array.add(new Caminhao(i));
        }
        this.indiceBalanceamento = Integer.MAX_VALUE;
        this.filhos = new CelulaBT[qtdFilhos];
    }

    @Override
    public String toString() {
        return "Celula [array=" + array.toString() + ", filhos=" + Arrays.toString(filhos) + "]";
    }

    public List<Caminhao> getArray() {
        return array;
    }

    public CelulaBT[] getFilhos() {
        return filhos;
    }

    public double getIndiceBalanceamento() {
        return indiceBalanceamento;
    }

//    public double indiceDeBalanceamento() {
//        int soma = 0;
//        for (int i = 0; i < array.length; i++) {
//            soma += array[i];
//        }
//        double media = soma / array.length;
//        double somaQuadrados = 0;
//        for (int i = 0; i < array.length; i++) {
//            somaQuadrados += Math.pow(array[i] - media, 2);
//        }
//        double variancia = somaQuadrados / array.length;
//        double desvioPadrao = Math.sqrt(variancia);
//        return desvioPadrao / media;
//    }

    private double indiceDeBalanceamento(List<Caminhao> array) {
        double maior = array.stream().max(Caminhao::compareTo).get().getQuilometros();
        double menor = array.stream().min(Caminhao::compareTo).get().getQuilometros();

        return maior - menor;
    }
    public void podar() {
        double[] indicesFilhos = Arrays.stream(this.filhos).mapToDouble(CelulaBT::getIndiceBalanceamento).toArray();
        double maiorIndice = Arrays.stream(indicesFilhos).max().getAsDouble();
        if (!Arrays.stream(indicesFilhos).allMatch(i -> i == maiorIndice)) {
            this.filhos = Arrays.stream(this.filhos).filter(e -> e.indiceBalanceamento != maiorIndice).toArray(CelulaBT[]::new);
            Arrays.stream(this.filhos).sorted(Comparator.comparing(CelulaBT::getIndiceBalanceamento)).collect(Collectors.toList());
        }
    }
}