package com.example.sokoban.model;

public class Empty extends Enterable {

    public Empty(int y, int  x) {
        super(y, x);
    }

    @Override
    public String toString() {
        if (worker != null) {
            return "w";
        }
        else if (crate != null) {
            return "x";
        }
        else {
            return ".";
        }
    }

}

