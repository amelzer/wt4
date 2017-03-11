package com.hsam.game.c4tv.domain;

import com.hsam.game.c4tv.exception.GameBlockOutOfBoundsException;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameField {

    private GameBlock[][][] field;

    private Map<String, Integer> scores;

    private int sizeX;
    private int sizeY;
    private int sizeZ;

    public GameField(int sizeX, int sizeY, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.field = new GameBlock[sizeX][sizeY][sizeZ];
        this.scores = new HashMap<>();
        resetField(this.field, sizeX, sizeY, sizeZ);
    }

    private void resetField(GameBlock[][][] field, int sizeX, int sizeY, int sizeZ) {
        for (int x = 0 ; x < sizeX ; x++) {
            for (int y = 0 ; y < sizeY ; y++) {
                for (int z = 0 ; z < sizeZ ; z++) {
                    field[x][y][z] = new GameBlock("#000000", "", x, y, z);
                }
            }
        }
    }

    public GameBlock[][][] getField() {
        return field;
    }

    public GameBlock[][] topView() {
        GameBlock[][] topView = new GameBlock[sizeX][sizeY];

        LOOP_X:
        for (int i = 0 ; i < sizeX ; i++) {
            LOOP_Y:
            for (int j = 0 ; j < sizeY ; j++) {
                LOOP_Z:
                for (int k = sizeZ -1 ; k >= 0 ; k--) {
                    if (!field[i][j][k].getPlayer().isEmpty()) {
                        topView[i][j] = field[i][j][k];
                        continue LOOP_Y;
                    }
                }
                topView[i][j] = new GameBlock("#000000", "", i, j, 0);
            }
        }

        return topView;
    }

    public GameBlock[][] frontView() {
        GameBlock[][] frontView = new GameBlock[sizeZ][sizeY];

        for (int y = 0 ; y < sizeY ; y++) {
            for (int z = 0 ; z < sizeZ ; z++) {
                frontView[z][y] = field[0][y][z];
            }
        }

        return frontView;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void addBlock(GameBlock block) {
        int x = block.getX();
        int y = block.getY();
        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY) {
            throw new GameBlockOutOfBoundsException();
        }
        // check for z resize
        if (!field[x][y][sizeZ - 1].getPlayer().isEmpty()) {
            resize(sizeX, sizeY, sizeZ + 1, 0, 0, 0);
        }

        for (int z = 0 ; z < sizeZ ; z++) {
            if (field[x][y][z].getPlayer().isEmpty()) {
                block.setZ(z);
                field[x][y][z] = block;
                return;
            }
        }
    }

    public void deleteBlock(GameBlock deletedBlock) {
        int x = deletedBlock.getX();
        int y = deletedBlock.getY();
        int z = deletedBlock.getZ();
        // boundary check
        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY ||
            z < 0 || z >= sizeZ) {
            return;
        }

        field[x][y][z] = new GameBlock("#000000","", x, y, z);
        for (int currZ = z; currZ <= sizeZ - 2 ; currZ++) {
            GameBlock movingBlock = field[x][y][currZ + 1];
            movingBlock.setZ(currZ);
            field[x][y][currZ] = movingBlock;
        }
        // delete top block
        field[x][y][sizeZ - 1] = new GameBlock("#000000", "", x, y, sizeZ - 1);

        boolean resizable;
        do {
            resizable = sizeZ > 1;
            // check for z resize
            LOOP:
            for (int currX = 0; currX < sizeX; currX++) {
                for (int currY = 0; currY < sizeY; currY++) {
                    if (!field[currX][currY][sizeZ - 1].getPlayer().isEmpty()) {
                        // top level block is filled - no resize
                        resizable = false;
                        break LOOP;
                    }
                }
            }

            if (resizable) {
                resize(sizeX, sizeY, sizeZ - 1, 0, 0, 0);
            }
        } while (resizable);
    }

    public List<GameBlock> getFour() {
        for (int x = 0 ; x < sizeX ; x++) {
            for (int y = 0 ; y < sizeY ; y++) {
                for (int z = 0 ; z < sizeZ ; z++) {
                    List<GameBlock> lineWithStartPoint = getLineWithStartPoint(x, y, z);
                    if (!lineWithStartPoint.isEmpty()) {
                        return lineWithStartPoint;
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<GameBlock> getLineWithStartPoint(int x, int y, int z) {
        for (int dirX = -1 ; dirX <= 1 ; dirX++) {
            for (int dirY = -1 ; dirY <= 1 ; dirY++) {
                for (int dirZ = -1 ; dirZ <= 1 ; dirZ++) {
                    List<GameBlock> line = getLineFromStartWithDirection(x, y, z, dirX, dirY, dirZ);
                    if (!line.isEmpty()) {
                        return line;
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<GameBlock> getLineFromStartWithDirection(int x, int y, int z, int dirX, int dirY, int dirZ) {
        // avoid boundaries
        if (x < 0 || (dirX > 0 && (x + 3) > sizeX - 1) || (dirX < 0 && (x - 3) < 0) ||
            y < 0 || (dirY > 0 && (y + 3) > sizeY - 1) || (dirY < 0 && (y - 3) < 0) ||
            z < 0 || (dirZ > 0 && (z + 3) > sizeZ - 1) || (dirZ < 0 && (z - 3) < 0) ) {
            return Collections.emptyList();
        }

        // avoid standstill
        if (dirX == 0 && dirY == 0 && dirZ == 0) {
            return Collections.emptyList();
        }

        // don't check for empty start blocks
        if (field[x][y][z].getPlayer().isEmpty()) {
            return Collections.emptyList();
        }

        String startPlayer = field[x][y][z].getPlayer();
        int currX = x;
        int currY = y;
        int currZ = z;

        List<GameBlock> result = new LinkedList<>();
        result.add(field[x][y][z]);
        for (int i = 0 ; i < 3 ; i++) {
            currX += dirX;
            currY += dirY;
            currZ += dirZ;
            if (!field[currX][currY][currZ].getPlayer().equals(startPlayer)) {
                return Collections.emptyList();
            }
            result.add(field[currX][currY][currZ]);
        }

        return result;
    }

    private void resize(int newSizeX, int newSizeY, int newSizeZ, int offsetX, int offsetY, int offsetZ) {
        GameBlock[][][] newField = new GameBlock[newSizeX][newSizeY][newSizeZ];
        resetField(newField, newSizeX, newSizeY, newSizeZ);

        copy(this.field, newField, offsetX, offsetY, offsetZ);

        this.field = newField;
        this.sizeX = newSizeX;
        this.sizeY = newSizeY;
        this.sizeZ = newSizeZ;
    }

    private void copy(GameBlock[][][] from, GameBlock[][][] to, int offsetX, int offsetY, int offsetZ) {
        for (int x = 0 ; x < to.length ; x++) {
            for (int y = 0 ; y < to[0].length ; y++) {
                for (int z = 0 ; z < to[0][0].length ; z++) {
                    if (withinField(from, x+offsetX, y+offsetY, z+offsetZ)) {
                        to[x][y][z] = from[x + offsetX][y + offsetY][z+offsetZ];
                    } else {
                        to[x][y][z] = new GameBlock("#000000", "", x, y, z);
                    }
                }
            }
        }
    }

    private boolean withinField(GameBlock[][][] field, int x, int y, int z) {
        return x >= 0 && x < field.length &&
               y >= 0 && y < field[0].length &&
               z >= 0 && z < field[0][0].length;
    }

    public void increaseScore(String id) {
        scores.computeIfAbsent(id, i -> 0);
        scores.compute(id, (i, score) -> score + 1);
    }
}
