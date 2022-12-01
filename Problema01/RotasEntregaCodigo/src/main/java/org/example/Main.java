package org.example;

import org.example.entities.Rota;
import org.example.entities.Caminhao;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Rota> rotas = new LinkedList<>();
        Random r = new Random(2);
        for (int i = 0; i < 100; i++) {
            rotas.add(new Rota(i, r.nextInt(30) + 1));
        }
        rotas.sort(Comparator.comparingInt(Rota::getQuilometros).reversed());

//        imprimir(rotas.stream().map(Rota::getQuilometros).toArray(Integer[]::new));

        imprimir(algoritmoGulosoRotasCaminhoes(2, rotas));;
        imprimir(algoritmoGulosoRotasCaminhoes(3, rotas));;
        imprimir(algoritmoGulosoRotasCaminhoes(4, rotas));;
        imprimir(algoritmoGulosoRotasCaminhoes(10, rotas));;
    }

    public static void imprimir(List<Caminhao> caminhoes) {
        int totalPercorrido = caminhoes.stream().mapToInt(Caminhao::getQuilometros).sum();
        System.out.println("\nTotal percorrido: " + totalPercorrido + "km");
        String saida = "";
        for (int i = 0; i < caminhoes.size(); i++) {
            saida += caminhoes.get(i) + " ";
        }
        System.out.println(saida);
    }

    public static int algoritmoGuloso(int valorTotal, List<Rota> rotas) {
        int contadorRotas = 0;
        int valorAcumulado = 0;
        int pos = rotas.size() - 1;
        while(valorAcumulado < valorTotal) {
            int quant = (valorTotal - valorAcumulado) / rotas.get(pos).getQuilometros();
            contadorRotas += quant;
            valorAcumulado += rotas.get(pos).getQuilometros() * quant;
            pos--;
        }
        return contadorRotas;
    }

    public static int media(List<Caminhao> valores) {
        return valores.stream().mapToInt(Caminhao::getQuilometros).sum() / valores.size();
    }

    public static int posicaoRotaMaisProxima(List<Rota> rotas, int valor) {
        // pegar a rota que mais se aproxima do valor médio
        int posRota = 0;
        int diferenca = Integer.MAX_VALUE;
        for(int j = 0; j < rotas.size(); j++) {
            int dif = Math.abs(rotas.get(j).getQuilometros() - valor);
            if (dif < diferenca) {
                diferenca = dif;
                posRota = j;
            }
        }

        return posRota;
    }

    public static List<Caminhao> algoritmoGulosoRotasCaminhoes(int qtdCaminhoes, List<Rota> rotas) {
//        int[] caminhoes = new int[qtdCaminhoes];
        List<Caminhao> caminhoes = new ArrayList<>(qtdCaminhoes);
        for (int i = 0; i < qtdCaminhoes; i++) {
            caminhoes.add(new Caminhao(i + 1));
        }
        List<Rota> rotasClone = new LinkedList<>(rotas);
        for(int i = 0; i < rotas.size(); i++) {
            int pos = i % qtdCaminhoes;
            if (!caminhoes.get(pos).getRotas().isEmpty()) {
                int valorMedio = media(caminhoes);
                int posRota = posicaoRotaMaisProxima(rotasClone, valorMedio);
                caminhoes.get(pos).getRotas().add(rotasClone.remove(posRota));
            } else
                caminhoes.get(pos).getRotas().add(rotasClone.remove(0));

        }

        return caminhoes;
    }
}