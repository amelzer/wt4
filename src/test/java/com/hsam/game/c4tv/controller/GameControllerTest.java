package com.hsam.game.c4tv.controller;

import com.hsam.game.c4tv.domain.GameBlock;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class GameControllerTest {

    private GameController sut;

    @Before
    public void setUp() throws Exception {
        sut = new GameController();

    }

    @Test
    public void foursAreReturned() throws Exception {
        sut.putBlock(new GameBlock("#000000", "1", 0, 0, 0));
        sut.putBlock(new GameBlock("#000000", "1", 0, 0, 0));
        sut.putBlock(new GameBlock("#000000", "1", 0, 0, 0));
        GameController.Response response = sut.putBlock(new GameBlock("#000000", "1", 0, 0, 0));

        assertThat(response.getFours(), notNullValue());
        assertThat(response.getFrontView(), notNullValue());
        assertThat(response.getFullView(), notNullValue());
    }
}
