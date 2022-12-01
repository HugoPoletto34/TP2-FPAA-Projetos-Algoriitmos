package org.example.entities;

public class Rota {
    private int id;
    private int quilometros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuilometros() {
        return quilometros;
    }

    public void setQuilometros(int quilometros) {
        this.quilometros = quilometros;
    }

    public Rota(int id, int quilometros) {
        this.id = id;
        this.quilometros = quilometros;
    }

    @Override
    public String toString() {
        return "Rota " + id + " (" + quilometros + " km)";
    }
}
