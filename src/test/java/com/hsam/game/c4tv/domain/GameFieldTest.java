package com.hsam.game.c4tv.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameFieldTest {

    @Test
    public void thatFieldCanBeCreated() {
        GameField gameField = new GameField(10, 10, 10);

        assertThat(gameField.getField().length, is(10));
        assertThat(gameField.getField()[0].length, is(10));
        assertThat(gameField.getField()[0][0].length, is(10));
    }

    @Test
    public void thatTopViewWorks() {
        GameField gameField = new GameField(3, 1, 1);
        gameField.addBlock(new GameBlock("#111111", "1", 0, 0, 0));
        gameField.addBlock(new GameBlock("#111111", "1", 1, 0, 0));
        gameField.addBlock(new GameBlock("#111111", "2", 1, 0, 0));

        GameBlock[][] topView = gameField.topView();

        assertThat(topView.length, is(3));
        assertThat(topView[0][0].getPlayer(), is("1"));
        assertThat(topView[1][0].getPlayer(), is("2"));
        assertThat(topView[2][0].getPlayer(), is(""));
    }

    @Test
    public void thatFrontViewWorks() {
        GameField gameField = new GameField(3, 3, 1);
        gameField.addBlock(new GameBlock("#111111", "1", 0, 0, 0));
        gameField.addBlock(new GameBlock("#111111", "3", 1, 0, 0));
        gameField.addBlock(new GameBlock("#111111", "1", 0, 2, 0));
        gameField.addBlock(new GameBlock("#111111", "2", 0, 2, 0));

        GameBlock[][] frontView = gameField.frontView();

        assertThat(frontView.length, is(2));
        assertThat(frontView[0].length, is(3));
        assertThat(frontView[0][0].getPlayer(), is("1"));
        assertThat(frontView[0][1].getPlayer(), is(""));
        assertThat(frontView[0][2].getPlayer(), is("1"));
        assertThat(frontView[1][0].getPlayer(), is(""));
        assertThat(frontView[1][1].getPlayer(), is(""));
        assertThat(frontView[1][2].getPlayer(), is("2"));
    }

    @Test
    public void thatBlockCanBeAdded() {
        GameField field = new GameField(10, 10, 1);

        field.addBlock(new GameBlock("someColor", "somePlayer", 5, 7, -1));

        assertThat(field.getField()[5][7][0].getColor(), is("someColor"));
        assertThat(field.getField()[5][7][0].getPlayer(), is("somePlayer"));
        assertThat(field.getField()[5][7][0].getX(), is(5));
        assertThat(field.getField()[5][7][0].getY(), is(7));
        assertThat(field.getField()[5][7][0].getZ(), is(0));
    }

    @Test
    public void thatVerticalResizeOnBlockAddingWorks() {
        GameField field = new GameField(10, 10, 1);

        field.addBlock(new GameBlock("someColor", "somePlayer", 5, 5, -1));
        assertThat(field.getField()[0][0].length, is(1));

        field.addBlock(new GameBlock("someOtherColor", "someOtherPlayer", 5, 5, -1));
        assertThat(field.getField()[0][0].length, is(2));

        assertThat(field.getField()[5][5][0].getColor(), is("someColor"));
        assertThat(field.getField()[5][5][0].getPlayer(), is("somePlayer"));
        assertThat(field.getField()[5][5][0].getX(), is(5));
        assertThat(field.getField()[5][5][0].getY(), is(5));
        assertThat(field.getField()[5][5][0].getZ(), is(0));

        assertThat(field.getField()[5][5][1].getColor(), is("someOtherColor"));
        assertThat(field.getField()[5][5][1].getPlayer(), is("someOtherPlayer"));
        assertThat(field.getField()[5][5][1].getX(), is(5));
        assertThat(field.getField()[5][5][1].getY(), is(5));
        assertThat(field.getField()[5][5][1].getZ(), is(1));
    }

    @Test
    public void thatBlockCanBeDeleted() {
        GameField field = new GameField(10, 10, 1);

        field.addBlock(new GameBlock("someColor", "somePlayer", 5, 5, -1));
        assertThat(field.getField()[0][0].length, is(1));

        field.deleteBlock(new GameBlock("someColor", "somePlayer", 5, 5, 0));
        assertThat(field.getField()[0][0].length, is(1));

        assertThat(field.getField()[5][5][0].getColor(), is("#000000"));
        assertThat(field.getField()[5][5][0].getPlayer(), is(""));
        assertThat(field.getField()[5][5][0].getX(), is(5));
        assertThat(field.getField()[5][5][0].getY(), is(5));
        assertThat(field.getField()[5][5][0].getZ(), is(0));
    }

    @Test
    public void thatVerticalResizeOnDeletionWorks() {
        GameField field = new GameField(10, 10, 1);

        field.addBlock(new GameBlock("someColor", "somePlayer", 5, 5, -1));
        assertThat(field.getField()[0][0].length, is(1));

        field.addBlock(new GameBlock("someOtherColor", "someOtherPlayer", 5, 5, -1));
        assertThat(field.getField()[0][0].length, is(2));

        field.deleteBlock(new GameBlock("someColor", "somePlayer", 5, 5, 0));
        assertThat(field.getField()[0][0].length, is(1));

        // topblock remains
        assertThat(field.getField()[5][5][0].getColor(), is("someOtherColor"));
        assertThat(field.getField()[5][5][0].getPlayer(), is("someOtherPlayer"));
        assertThat(field.getField()[5][5][0].getX(), is(5));
        assertThat(field.getField()[5][5][0].getY(), is(5));
        assertThat(field.getField()[5][5][0].getZ(), is(0));
    }

    @Test
    public void thatLineIsFoundIfPresent_XY() {
        GameField field = new GameField(7, 7, 1);

        field.addBlock(new GameBlock("#000000", "1", 2, 2, 0));
        field.addBlock(new GameBlock("#000000", "1", 3, 3, 0));
        field.addBlock(new GameBlock("#000000", "1", 4, 4, 0));
        field.addBlock(new GameBlock("#000000", "1", 5, 5, 0));

        List<GameBlock> four = field.getFour();

        assertThat(four.size(), is(4));
        assertThat(four.get(0).getX(), is(2));
        assertThat(four.get(0).getY(), is(2));
        assertThat(four.get(0).getZ(), is(0));

        assertThat(four.get(1).getX(), is(3));
        assertThat(four.get(1).getY(), is(3));
        assertThat(four.get(1).getZ(), is(0));

        assertThat(four.get(2).getX(), is(4));
        assertThat(four.get(2).getY(), is(4));
        assertThat(four.get(2).getZ(), is(0));

        assertThat(four.get(3).getX(), is(5));
        assertThat(four.get(3).getY(), is(5));
        assertThat(four.get(3).getZ(), is(0));
    }

    @Test
    public void thatLineIsFound() {
        for (int dirX = -1 ; dirX <= 1 ; dirX++) {
            for (int dirY = -1 ; dirY <= 1 ; dirY++) {
                for (int dirZ = -1 ; dirZ <= 1 ; dirZ++) {
                    if (dirX == 0 && dirY == 0 && dirZ == 0) {
                        continue;
                    }
                    System.out.println(String.format("Testcase : %s, %s, %s", dirX, dirY, dirZ));

                    GameField field = new GameField(7, 7, 7);


                    int x = 3;
                    int y = 3;
                    int z = 3;
                    List<GameBlock> addedBlocks = new LinkedList<>();
                    for (int i = 0 ; i < 4 ; i++) {
                        addedBlocks.add(new GameBlock("#000000", "1", x, y, z));
                        field.getField()[x][y][z] = addedBlocks.get(i);

                        x += dirX;
                        y += dirY;
                        z += dirZ;
                    }

                    List<GameBlock> four = field.getFour();

                    assertThat(four.size(), is(4));
                    assertThat(four, hasItems(addedBlocks.get(0), addedBlocks.get(1), addedBlocks.get(2), addedBlocks.get(3)));
                }
            }
        }
    }

    @Test
    public void thatInterruptedLineIsNotFound() {
        GameField field = new GameField(4, 4, 4);
        field.addBlock(new GameBlock("#000000", "1", 0, 0, 0));
        field.addBlock(new GameBlock("#000000", "1", 1, 0, 0));
        field.addBlock(new GameBlock("#000000", "2", 2, 0, 0)); // <- the break
        field.addBlock(new GameBlock("#000000", "1", 3, 0, 0));

        assertThat(field.getFour().size(), is(0));
    }

    @Test
    public void thatBrokenLineIsNotFound() {
        GameField field = new GameField(4, 4, 4);
        field.addBlock(new GameBlock("#000000", "1", 0, 0, 0));
        field.addBlock(new GameBlock("#000000", "1", 1, 0, 0));
        field.addBlock(new GameBlock("#000000",  "", 2, 0, 0)); // <- missing
        field.addBlock(new GameBlock("#000000", "1", 3, 0, 0));

        assertThat(field.getFour().size(), is(0));
    }

    @Test
    public void thatLineCanBeDeleted() {
        GameField field = new GameField(1, 4, 5);
        field.addBlock(new GameBlock("#000000", "1", 0, 0, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 1, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 2, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 3, 0));

        field.addBlock(new GameBlock("#111111", "2", 0, 0, 0));
        field.addBlock(new GameBlock("#000000", "1", 0, 1, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 2, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 3, 0));

        field.addBlock(new GameBlock("#111111", "2", 0, 0, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 1, 0));
        field.addBlock(new GameBlock("#000000", "1", 0, 2, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 3, 0));

        field.addBlock(new GameBlock("#111111", "2", 0, 0, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 1, 0));
        field.addBlock(new GameBlock("#111111", "2", 0, 2, 0));
        field.addBlock(new GameBlock("#000000", "1", 0, 3, 0));

        // this now looks like this:
        // 2221
        // 2212
        // 2122
        // 1222

        List<GameBlock> four = field.getFour();

        assertThat(four.size(), is(4));
        assertThat(four.get(0).getX(), is(0));
        assertThat(four.get(0).getY(), is(0));
        assertThat(four.get(0).getZ(), is(0));

        assertThat(four.get(1).getX(), is(0));
        assertThat(four.get(1).getY(), is(1));
        assertThat(four.get(1).getZ(), is(1));

        assertThat(four.get(2).getX(), is(0));
        assertThat(four.get(2).getY(), is(2));
        assertThat(four.get(2).getZ(), is(2));

        assertThat(four.get(3).getX(), is(0));
        assertThat(four.get(3).getY(), is(3));
        assertThat(four.get(3).getZ(), is(3));

        four.forEach(field::deleteBlock);

        assertThat(field.getField()[0][0].length, is(3));

        for (int y = 0 ; y < 4 ; y++) {
            for (int z = 0 ; z < 3 ; z++) {
                assertThat(field.getField()[0][y][z].getPlayer(), is("2"));
                assertThat(field.getField()[0][y][z].getColor(), is("#111111"));
            }
        }
    }

    @Test
    public void thatTowerSinksOneDown() {
        GameField field = new GameField(1, 1, 5);
        field.addBlock(new GameBlock("#111111", "1", 0, 0, 0));
        field.addBlock(new GameBlock("#222222", "2", 0, 0, 0));
        field.addBlock(new GameBlock("#333333", "3", 0, 0, 0));
        field.addBlock(new GameBlock("#444444", "4", 0, 0, 0));

        assertThat(field.getField()[0][0][0].getPlayer(), is("1"));
        assertThat(field.getField()[0][0][1].getPlayer(), is("2"));
        assertThat(field.getField()[0][0][2].getPlayer(), is("3"));
        assertThat(field.getField()[0][0][3].getPlayer(), is("4"));

        field.deleteBlock(new GameBlock("#111111", "1", 0, 0, 0));

        assertThat(field.getField()[0][0].length, is(3));
        assertThat(field.getField()[0][0][0].getPlayer(), is("2"));
        assertThat(field.getField()[0][0][1].getPlayer(), is("3"));
        assertThat(field.getField()[0][0][2].getPlayer(), is("4"));

    }
}
