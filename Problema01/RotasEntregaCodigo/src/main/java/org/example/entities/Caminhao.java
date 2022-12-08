package org.example.entities;

import java.util.LinkedList;
import java.util.List;

public class Caminhao implements Cloneable, Comparable<Caminhao> {
    private int id;
    private List<Rota> rotas;

    public Caminhao(Object o) {
        this.rotas = new LinkedList<>();

    }

    public Caminhao() {
        this.rotas = new LinkedList<>();
    }

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

    public void addRota(Rota rota) {
        this.rotas.add(rota);
    }



    @Override
    public Caminhao clone() {
        try {
            Caminhao c = (Caminhao) super.clone();
            c.rotas = new LinkedList<>(this.rotas);
            c.id = this.id;
            return c;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(Caminhao o) {
        return this.getQuilometros() - o.getQuilometros();
    }
}
