package com.example.sokoban.model;

public class Level {
    private String levelName;
    private int height;
    private int width;
    int moveCount;
    int completedCount;
    private int targetCount;
    public Placeable[] [] allPlaceables;

    public Level(String newLevelName, int newHeight, int newWidth, String newLevelString) {
        this.levelName = newLevelName;
        this.levelName = this.levelName.replaceAll("Test","Level ");
        this.height = newHeight;
        this.width = newWidth;
        for (int i = 0; i < newLevelString.length(); i++) {
            if(newLevelString.charAt(i) == '+') {
                this.targetCount ++;
            }
        }
        this.allPlaceables = new Placeable[height] [width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0 || y == height-1) {
                    Immoveable newImmoveable = new Wall(y, x);
                    this.allPlaceables[y] [x] = newImmoveable;
                }
                else if (x == 0 || x == width-1) {
                    Immoveable newImmoveable = new Wall(y, x);
                    this.allPlaceables[y] [x] = newImmoveable;
                }
                else {
                    Enterable newEnterable = new Enterable(y, x);
                    if (newLevelString.charAt((y*width)+x) == '+') {
                        newEnterable = new Target(y, x);
                    }
                    else {
                        newEnterable = new Empty(y, x);
                    }
                    this.allPlaceables[y] [x] = newEnterable;
                }
            }
        }

        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                if (newLevelString.charAt((y*width)+x) == 'x') {
                    this.allPlaceables[y] [x] = new Crate(y, x);
                }
                else if (newLevelString.charAt((y*width)+x) == 'w') {
                    this.allPlaceables[y] [x] = new Worker(y, x);
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public String getLevelName() {
        return levelName;
    }

    public String toString() {
        String levelStr = "";
        for (Placeable[] row : this.allPlaceables) {
            for (Placeable cell : row) {
                levelStr += cell.toString();
            }
            levelStr += "\n";
        }
        return String.format("%s\n%smove %d \ncompleted %d of %d \n", levelName, levelStr, moveCount, completedCount, targetCount);
    }
}

