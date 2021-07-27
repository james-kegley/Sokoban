package com.example.sokoban.model;

import com.example.sokoban.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Integer levelCount;
    public List<Level> allMyLevels = new ArrayList<Level>();

    public void addLevel(String levelName, int height, int width, String levelString) {
        Level level = new Level(levelName, height, width, levelString);
        this.allMyLevels.add(level);
    }

    public String toString() {
        if (allMyLevels.size() != 0) {
            Level currentLevel = allMyLevels.get(MainActivity.selectedLevel);
            return currentLevel.toString();
        }
        else {
            return "no levels";
        }
    }

    public int getLevelCount() {
        levelCount = allMyLevels.size();
        return levelCount;
    }

    public void move(Direction direction) {
        Level currentLevel = allMyLevels.get(MainActivity.selectedLevel);
        currentLevel.moveCount++;
        for (int y = 0; y < currentLevel.allPlaceables.length; y++) {
            for (int x = 0; x < currentLevel.allPlaceables[y].length; x++) {
                Enterable levelEnterable;
                Empty levelEmpty = new Empty(y, x);
                Target levelTarget = new Target(y, x);
                Worker levelWorker = new Worker(y, x);
                Crate levelCrate = new Crate(y,x);

                // Checks what the current enterable at position y/x is. So we know what if the worker is standing on an empty space or a target - the only two possibilities.
                if (currentLevel.allPlaceables[y] [x] instanceof Target) {
                    levelEnterable = levelTarget;
                }
                else {
                    levelEnterable = levelEmpty;
                }

                //
                // The comments for one case can be applied to all cases //
                //
                switch (direction) {
                    case LEFT:
                        // Get the position of the worker and check they are not up against a wall
                        if ((currentLevel.allPlaceables[y] [x] instanceof Worker || currentLevel.allPlaceables[y][x].toString().equals("W")) && !(currentLevel.allPlaceables[y] [x-1] instanceof Wall)) {
                            // Replace the worker with what was underneath it
                            currentLevel.allPlaceables[y][x] = levelEnterable;
                            // If you're moving the worker onto a target,
                            if ((currentLevel.allPlaceables[y] [x-1] instanceof Target)) {
                                // If you're moving the worker onto a target and that target already has a crate on it,
                                if (currentLevel.allPlaceables[y] [x-1].toString().equals("X")) {
                                    // Check there is not a wall and not a crate next to that crate in the direction you want to move it
                                    if (!(currentLevel.allPlaceables[y] [x-2] instanceof Wall) && (!(currentLevel.allPlaceables[y] [x-2] instanceof Crate))) {
                                        // Lowers the completed count as you're moving a crate off a target
                                        currentLevel.completedCount--;

                                        // Sets the new crate and target in the appropriate position
                                        currentLevel.allPlaceables[y][x - 2] = levelCrate;
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y][x -= 1] = levelTarget;
                                    }
                                    // If there is a crate or wall blocking you from moving the crate, keep the worker in the same place it currently is
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                                // If you're moving the worker onto an unoccupied target,
                                else {
                                    levelTarget.addWorker(levelWorker);
                                    currentLevel.allPlaceables[y][x -= 1] = levelTarget;
                                }
                            }
                            // Check if you're pushing a crate,
                            else if ((currentLevel.allPlaceables[y] [x-1] instanceof Crate) || (currentLevel.allPlaceables[y][x-1].toString().equals("X"))) {
                                // Check that crate is not up against a wall
                                if (!(currentLevel.allPlaceables[y] [x-2] instanceof Wall)) {
                                    // Check the crate is also not against another crate
                                    if (!(currentLevel.allPlaceables[y][x-2] instanceof Crate)) {
                                        if (!(currentLevel.allPlaceables[y][x-2].toString().equals("X"))) {
                                            // If you're moving the crate onto a target,
                                            if (currentLevel.allPlaceables[y][x-2] instanceof Target) {
                                                currentLevel.completedCount++;
                                                levelTarget.addCrate(levelCrate);
                                                currentLevel.allPlaceables[y][x-2] = levelTarget;
                                            }
                                            // Otherwise just move the crate normally
                                            else {
                                                currentLevel.allPlaceables[y][x-2] = levelCrate;
                                            }
                                            // In both cases, move the worker
                                            currentLevel.allPlaceables[y][x-=1] = levelWorker;
                                        }
                                        // If there is a crate blocking another crate from moving, keep the worker where they are
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                    // If the crate is up against another crate,
                                    else {
                                        // Keep the worker on the current target or empty space they are already on
                                        if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                            levelTarget.addWorker(levelWorker);
                                            currentLevel.allPlaceables[y][x] = levelTarget;
                                        }
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                } // If the crate is up against a wall,
                                else {
                                    // Keep the worker on the current target or empty space they are already on
                                    if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y][x] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                            }
                            // Normal functionality is just move the worker
                            else {
                                currentLevel.allPlaceables[y][x-=1] = levelWorker;
                            }
                        }
                        break;
                    case DOWN:
                        if ((currentLevel.allPlaceables[y] [x] instanceof Worker || currentLevel.allPlaceables[y][x].toString().equals("W")) &&
                                !(currentLevel.allPlaceables[y+1] [x] instanceof Wall)) {
                            currentLevel.allPlaceables[y][x] = levelEnterable;
                            if ((currentLevel.allPlaceables[y+1] [x] instanceof Target)) {
                                if (currentLevel.allPlaceables[y+1] [x].toString().equals("X")) {
                                    if (!(currentLevel.allPlaceables[y+2] [x] instanceof Wall) && (!(currentLevel.allPlaceables[y+2] [x] instanceof Crate))) {
                                        currentLevel.completedCount--;
                                        currentLevel.allPlaceables[y + 2][x] = levelCrate;
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y += 1][x] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                                else {
                                    levelTarget.addWorker(levelWorker);
                                    currentLevel.allPlaceables[y += 1][x] = levelTarget;
                                }
                            }
                            else if ((currentLevel.allPlaceables[y+1] [x] instanceof Crate) || (currentLevel.allPlaceables[y+1][x].toString().equals("X"))) {
                                if (!(currentLevel.allPlaceables[y+2] [x] instanceof Wall)) {
                                    if (!(currentLevel.allPlaceables[y+2][x] instanceof Crate)) {
                                        if (!(currentLevel.allPlaceables[y+2][x].toString().equals("X"))) {
                                            if (currentLevel.allPlaceables[y+2][x] instanceof Target) {
                                                currentLevel.completedCount++;
                                                levelTarget.addCrate(levelCrate);
                                                currentLevel.allPlaceables[y+2][x] = levelTarget;
                                            }
                                            else {
                                                currentLevel.allPlaceables[y+2][x] = levelCrate;
                                            }
                                            currentLevel.allPlaceables[y+=1][x] = levelWorker;
                                        }
                                    }
                                    else {
                                        if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                            levelTarget.addWorker(levelWorker);
                                            currentLevel.allPlaceables[y][x] = levelTarget;
                                        }
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                }
                                else {
                                    if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y][x] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                            }
                            else {
                                currentLevel.allPlaceables[y+=1][x] = levelWorker;
                            }
                        }
                        break;
                    case RIGHT:
                        if ((currentLevel.allPlaceables[y] [x] instanceof Worker || currentLevel.allPlaceables[y][x].toString().equals("W")) && !(currentLevel.allPlaceables[y] [x+1] instanceof Wall)) {
                            currentLevel.allPlaceables[y][x] = levelEnterable;
                            if ((currentLevel.allPlaceables[y] [x+1] instanceof Target)) {
                                if (currentLevel.allPlaceables[y] [x+1].toString().equals("X")) {
                                    if (!(currentLevel.allPlaceables[y] [x+2] instanceof Wall) && (!(currentLevel.allPlaceables[y] [x+2] instanceof Crate))) {
                                        currentLevel.completedCount--;
                                        currentLevel.allPlaceables[y][x + 2] = levelCrate;
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y][x += 1] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                                else {
                                    levelTarget.addWorker(levelWorker);
                                    currentLevel.allPlaceables[y][x += 1] = levelTarget;
                                }
                            }
                            else if ((currentLevel.allPlaceables[y] [x+1] instanceof Crate) || (currentLevel.allPlaceables[y][x+1].toString().equals("X"))) {
                                if (!(currentLevel.allPlaceables[y] [x+2] instanceof Wall)) {
                                    if (!(currentLevel.allPlaceables[y][x+2] instanceof Crate)) {
                                        if (!(currentLevel.allPlaceables[y][x+2].toString().equals("X"))) {
                                            if (currentLevel.allPlaceables[y][x + 2] instanceof Target) {
                                                currentLevel.completedCount++;
                                                levelTarget.addCrate(levelCrate);
                                                currentLevel.allPlaceables[y][x + 2] = levelTarget;
                                            }
                                            else {
                                                currentLevel.allPlaceables[y][x + 2] = levelCrate;
                                            }
                                            currentLevel.allPlaceables[y][x += 1] = levelWorker;
                                        }
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                    else {
                                        if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                            levelTarget.addWorker(levelWorker);
                                            currentLevel.allPlaceables[y][x] = levelTarget;
                                        }
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                }
                                else {
                                    if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y][x] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                            }
                            else {
                                currentLevel.allPlaceables[y][x+=1] = levelWorker;
                            }
                        }
                        break;
                    case UP:
                        if ((currentLevel.allPlaceables[y] [x] instanceof Worker || currentLevel.allPlaceables[y][x].toString().equals("W")) && !(currentLevel.allPlaceables[y-1] [x] instanceof Wall)) {
                            currentLevel.allPlaceables[y][x] = levelEnterable;
                            if ((currentLevel.allPlaceables[y-1] [x] instanceof Target)) {
                                if (currentLevel.allPlaceables[y-1] [x].toString().equals("X")) {
                                    if (!(currentLevel.allPlaceables[y-2] [x] instanceof Wall) && (!(currentLevel.allPlaceables[y-2] [x] instanceof Crate))) {
                                        currentLevel.completedCount--;
                                        currentLevel.allPlaceables[y - 2][x] = levelCrate;
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y -= 1][x] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                                else {
                                    levelTarget.addWorker(levelWorker);
                                    currentLevel.allPlaceables[y -= 1][x] = levelTarget;
                                }
                            }
                            else if ((currentLevel.allPlaceables[y-1] [x] instanceof Crate) || (currentLevel.allPlaceables[y-1][x].toString().equals("X"))) {
                                if (!(currentLevel.allPlaceables[y-2] [x] instanceof Wall)) {
                                    if (!(currentLevel.allPlaceables[y-2][x] instanceof Crate)) {
                                        if (!(currentLevel.allPlaceables[y-2][x].toString().equals("X"))) {
                                            if (currentLevel.allPlaceables[y - 2][x] instanceof Target) {
                                                currentLevel.completedCount++;
                                                levelTarget.addCrate(levelCrate);
                                                currentLevel.allPlaceables[y - 2][x] = levelTarget;
                                            }
                                            else {
                                                currentLevel.allPlaceables[y - 2][x] = levelCrate;
                                            }
                                            currentLevel.allPlaceables[y -= 1][x] = levelWorker;
                                        }
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                    else {
                                        if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                            levelTarget.addWorker(levelWorker);
                                            currentLevel.allPlaceables[y][x] = levelTarget;
                                        }
                                        else {
                                            currentLevel.allPlaceables[y][x] = levelWorker;
                                        }
                                    }
                                }
                                else {
                                    if (currentLevel.allPlaceables[y][x] instanceof Target) {
                                        levelTarget.addWorker(levelWorker);
                                        currentLevel.allPlaceables[y][x] = levelTarget;
                                    }
                                    else {
                                        currentLevel.allPlaceables[y][x] = levelWorker;
                                    }
                                }
                            }
                            else {
                                currentLevel.allPlaceables[y-=1][x] = levelWorker;
                            }
                        }
                        break;
                }
            }
        }
    }
}

