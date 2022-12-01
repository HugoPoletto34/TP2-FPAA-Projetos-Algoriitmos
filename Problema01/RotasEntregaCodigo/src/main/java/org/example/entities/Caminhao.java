package org.example.entities;

import java.util.LinkedList;
import java.util.List;

public class Caminhao {
    private int id;
    private List<Rota> rotas;

    public List<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(List<Rota> rotas) {
        this.rotas = rotas;
    }

    public int getQuilometros() {
        return rotas.stream().mapToInt(Rota::getQuilometros).sum();
    }

    public Caminhao(List<Rota> rotas) {
        this.rotas = rotas;
    }

    public Caminhao(int id) {
        this.id = id;
        this.rotas = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Caminhão " + this.id + " Total: " + this.getQuilometros() + "km - ";
    }
}
