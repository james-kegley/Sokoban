package com.example.sokoban.model;

public class Enterable extends Placeable {

    protected Worker worker;
    protected Crate crate;

    public Enterable(int y, int x) {
        super(y, x);
    }

    public void addWorker(Worker worker) {
        this.worker = worker;
    }

    public void addCrate(Crate crate) {
        this.crate = crate;
    }

}
