package org.example.entities;

public class Rota implements Cloneable {
    private int id;
    private int quilometros;

    public Rota(int quilometros) {
    }

    public Rota(Rota rota) {
        this.id = rota.id;
        this.quilometros = rota.quilometros;
    }

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
    public Rota clone() {
        try {
            Rota r = (Rota) super.clone();
            r.id = this.id;
            r.quilometros = this.quilometros;
            return r;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Rota " + id + " (" + quilometros + " km)";
    }
}
