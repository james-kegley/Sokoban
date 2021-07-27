package com.example.sokoban.model;

public class Target extends Enterable {

    public Target(int y, int  x) {
        super(y, x);
    }

    @Override
    public String toString() {
        if (worker != null) {
            return "W";
        }
        else if (crate != null) {
            return "X";
        }
        else {
            return "+";
        }
    }

}

