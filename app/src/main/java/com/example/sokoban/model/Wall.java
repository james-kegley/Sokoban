package com.example.sokoban.model;

public class Wall extends Immoveable {

    public Wall(int y, int  x) {
        super(y, x);
    }


    @Override
    public String toString() {
        return "#";
    }

}

