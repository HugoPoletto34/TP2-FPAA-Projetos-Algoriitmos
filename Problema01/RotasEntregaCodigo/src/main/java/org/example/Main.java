package org.example;

import org.example.entities.CelulaBT;
import org.example.entities.CelulaPD;
import org.example.entities.Rota;
import org.example.entities.Caminhao;
import org.example.utilLib.ArquivoTextoLeitura;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Rota> rotas = new LinkedList<>();
//        Random r = new Random(2);
//        for (int i = 0; i < 100; i++) {
//            rotas.add(new Rota(i, r.nextInt(30) + 1));
//        }


        ArquivoTextoLeitura arquivo = new ArquivoTextoLeitura("./src/main/resources/caminhoes_compacto.txt");
        int qtdCaminhoes = Integer.parseInt(arquivo.lerBuffer());
        String linha = arquivo.lerBuffer();
        while (linha != null) {
            String[] dados = linha.split(";");
            rotas.add(new Rota(Integer.parseInt(dados[0]), Integer.parseInt(dados[1])));
            linha = arquivo.lerBuffer();
        }
        rotas.sort(Comparator.comparingInt(Rota::getQuilometros).reversed());

//        imprimir(rotas.stream().map(Rota::getQuilometros).toArray(Integer[]::new));

        System.out.println("\nTeste de algoritmos com caminhões compactos: ");
        System.out.println("\nAlgoritmo Guloso: ");
        long tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoGulosoRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");

        System.out.println("\nProgramação Dinâmica: ");
        tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoPogramacaoDinamicaRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");




        rotas = new LinkedList<>();
        arquivo = new ArquivoTextoLeitura("./src/main/resources/caminhoes_disperso.txt");
        qtdCaminhoes = Integer.parseInt(arquivo.lerBuffer());
        linha = arquivo.lerBuffer();
        while (linha != null) {
            String[] dados = linha.split(";");
            rotas.add(new Rota(Integer.parseInt(dados[0]), Integer.parseInt(dados[1])));
            linha = arquivo.lerBuffer();
        }
        rotas.sort(Comparator.comparingInt(Rota::getQuilometros).reversed());

        System.out.println("\nTeste de algoritmos com caminhões diversos: ");
        System.out.println("\nAlgoritmo Guloso: ");
        tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoGulosoRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");

        System.out.println("\nProgramação Dinâmica: ");
        tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoPogramacaoDinamicaRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");


        rotas = new LinkedList<>();
        arquivo = new ArquivoTextoLeitura("./src/main/resources/caminhoes_longo.txt");
        qtdCaminhoes = Integer.parseInt(arquivo.lerBuffer());
        linha = arquivo.lerBuffer();
        while (linha != null) {
            String[] dados = linha.split(";");
            rotas.add(new Rota(Integer.parseInt(dados[0]), Integer.parseInt(dados[1])));
            linha = arquivo.lerBuffer();
        }
        rotas.sort(Comparator.comparingInt(Rota::getQuilometros).reversed());

        System.out.println("\nTeste de algoritmos com caminhões longos: ");
        System.out.println("\nAlgoritmo Guloso: ");
        tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoGulosoRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");

        System.out.println("\nProgramação Dinâmica: ");
        tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoPogramacaoDinamicaRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");

        rotas = new LinkedList<>();
        rotas.add(new Rota(4, 12));
        rotas.add(new Rota(2, 23));
        rotas.add(new Rota(1, 48));
        rotas.add(new Rota(6, 5));
        rotas.add(new Rota(5, 9));
        rotas.add(new Rota(3, 20));
        qtdCaminhoes = 3;
        rotas.sort(Comparator.comparingInt(Rota::getQuilometros).reversed());

        System.out.println("\nBacktracking com exemplo (rotas e caminhões longos demoram a executar): ");
        tempoInicial = System.currentTimeMillis();
        imprimir(algoritmoBacktrackingRotasCaminhoes(qtdCaminhoes, rotas));
        System.out.println("Tempo de execução: " + (System.currentTimeMillis() - tempoInicial) + "ms");

    }

    public static void imprimir(List<Caminhao> caminhoes) {
        int totalPercorrido = caminhoes.stream().mapToInt(Caminhao::getQuilometros).sum();
        System.out.println("\nTotal percorrido: " + totalPercorrido + "km");
        System.out.println("Total de caminhões: " + caminhoes.size());
        System.out.println("Indice de Balanceamento: " + indiceDeBalanceamento(caminhoes.stream().mapToInt(Caminhao::getQuilometros).toArray()));
        String saida = "";
        for (int i = 0; i < caminhoes.size(); i++) {
            saida += caminhoes.get(i) + " ";
        }
        System.out.println(saida);
    }

    public static int media(List<Caminhao> valores) {
        return valores.stream().mapToInt(Caminhao::getQuilometros).sum() / valores.size();
    }

    public static List<Caminhao> algoritmoGulosoRotasCaminhoes(int qtdCaminhoes, List<Rota> rotas) {
        List<Caminhao> caminhoes = new ArrayList<>(qtdCaminhoes);
        for (int i = 0; i < qtdCaminhoes; i++) {
            caminhoes.add(new Caminhao(i + 1));
        }
        List<Rota> rotasClone = new LinkedList<>(rotas);
        int indice;
        for(int i = 0; i < rotas.size(); i++) {
            int pos = i % qtdCaminhoes;
            Caminhao caminhaoAtual = caminhoes.get(pos);
            if (!caminhaoAtual.getRotas().isEmpty()) {
                double[] distanciasMaxima = new double[rotasClone.size()];
                List<Caminhao> caminhoesClone = caminhoes.stream().map(Caminhao::clone).collect(Collectors.toList());
                int iArrayDistanciaMaxima = 0;
                for (Rota rota : rotasClone){
                    caminhoesClone.get(pos).addRota(rota);
                    double media = media(caminhoesClone);

                    double[] distanciaMaximaCandidatas = new double[qtdCaminhoes];
                    for (int j = 0; j < qtdCaminhoes; j++) {
                        distanciaMaximaCandidatas[j] = Math.abs(caminhoesClone.get(j).getQuilometros() - media);
                    }

                    distanciasMaxima[iArrayDistanciaMaxima++] = Arrays.stream(distanciaMaximaCandidatas).max().getAsDouble();
                    caminhoesClone.get(pos).getRotas().remove(rota);
                }
                double valor = caminhaoAtual.getQuilometros() > media(caminhoes)
                        ? Arrays.stream(distanciasMaxima).min().getAsDouble()
                        : Arrays.stream(distanciasMaxima).max().getAsDouble();

                indice = Arrays.stream(distanciasMaxima).boxed().collect(Collectors.toList()).indexOf(valor);

            } else {
                Rota maiorRota = rotasClone.stream().max(Comparator.comparingInt(Rota::getQuilometros)).get();
                indice = rotasClone.indexOf(maiorRota);
            }
            caminhaoAtual.addRota(rotasClone.remove(indice));

        }

        return caminhoes;
    }

    private static double indiceDeBalanceamento(int[] vetor) {

        double maior = Arrays.stream(vetor).max().getAsInt();
        double menor = Arrays.stream(vetor).min().getAsInt();

        return maior - menor;
    }

    public static List<Caminhao> algoritmoPogramacaoDinamicaRotasCaminhoes(int qtdCaminhoes, List<Rota> rotas) {
        CelulaPD[][] matriz = new CelulaPD[rotas.size() + 1][qtdCaminhoes];
        List<Rota> rotasClone = new LinkedList<>(rotas);

        for (int i = 0; i < rotasClone.size() + 1; i++) {
            for (int j = 0; j < qtdCaminhoes; j++) {
                matriz[i][j] = new CelulaPD(new int[qtdCaminhoes], 0);
            }
        }
        for (int i = 1; i < rotasClone.size() + 1; i++) {
            for (int j = 0; j < qtdCaminhoes; j++) {

                double[] valores = new double[qtdCaminhoes];
                int[][] vetores = new int[qtdCaminhoes][qtdCaminhoes];
                for (int k = 0; k < qtdCaminhoes; k++) {
                    int[] vetor = new int[qtdCaminhoes];
                    System.arraycopy(matriz[i - 1][k].array, 0, vetor, 0, qtdCaminhoes);
                    vetor[j] += rotasClone.get(i - 1).getQuilometros();
                    valores[k] = indiceDeBalanceamento(vetor);
                    vetores[k] = vetor;
                }

                double valor = Arrays.stream(valores).min().getAsDouble();
                int indice = Arrays.stream(valores).boxed().collect(Collectors.toList()).indexOf(valor);

                if (j == 0) {
                    matriz[i][j].array = vetores[indice];
                    matriz[i][j].indiceBalanceamento = valor;
                } else {
                    if (valor < matriz[i][j - 1].indiceBalanceamento) {
                        matriz[i][j].array = vetores[indice];
                        matriz[i][j].indiceBalanceamento = valor;
                    } else {
                        matriz[i][j].array = matriz[i][j - 1].array;
                        matriz[i][j].indiceBalanceamento = matriz[i][j - 1].indiceBalanceamento;
                    }
                }
            }
        }

        List<Caminhao> caminhoes = new ArrayList<>(qtdCaminhoes);
        for (int i = 0; i < qtdCaminhoes; i++) {
            caminhoes.add(new Caminhao(i + 1));
        }

        int coluna = qtdCaminhoes - 1;
        int ultimaColuna = qtdCaminhoes - 1;
        for (Caminhao caminhao : caminhoes) {
            int indice = 0;
            // transformar as linhas em colunas da matriz
            for (int i = 0; i <  rotasClone.size(); i++) {

                if (matriz[i][ultimaColuna].array[coluna] < matriz[i + 1][ultimaColuna].array[coluna]) {
                    caminhao.addRota(rotasClone.get(indice));
                }
                indice++;

            }
            coluna--;
        }

        return caminhoes;
    }


    public static List<Caminhao> algoritmoBacktrackingRotasCaminhoes(int qtdCaminhoes, List<Rota> rotas) {
        CelulaBT celulaBT = new CelulaBT(qtdCaminhoes);
        CelulaBT melhorIndice = new CelulaBT(qtdCaminhoes);
        int i = 0;

        CelulaBT celula = backtracking(celulaBT, melhorIndice, i, qtdCaminhoes, rotas);
        return celula.array.stream().map(Caminhao::clone).collect(Collectors.toList());
    }

    private static CelulaBT backtracking(CelulaBT celulaBT, CelulaBT melhorIndice, int i, int qtdCaminhoes, List<Rota> rotas) {
        List<Rota> rotasClone = new LinkedList<>(rotas);
        if (i == rotasClone.size()) {
            return celulaBT;
        } else {
            Rota novoValor = rotasClone.get(i);
            celulaBT.gerarFilhos(novoValor, qtdCaminhoes);
            for (int j = 0; j < celulaBT.filhos.length; j++) {
                CelulaBT celulaAnterior = backtracking(celulaBT.getFilhos()[j], melhorIndice, i + 1, qtdCaminhoes, rotasClone);
                if (celulaAnterior.indiceBalanceamento < melhorIndice.indiceBalanceamento) {
                    melhorIndice = celulaAnterior;
                }
            }
        }

        return melhorIndice;
    }

}