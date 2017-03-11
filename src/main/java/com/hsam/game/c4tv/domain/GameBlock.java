package com.hsam.game.c4tv.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

import java.util.Objects;

public class GameBlock {

    private String color;

    @NotNull
    private String player;

    @NotNull
    private Integer x;

    @NotNull
    private Integer y;

    private Integer z;

    public GameBlock(@JsonProperty("color") String color,
                     @JsonProperty("player") String player,
                     @JsonProperty("x") Integer x,
                     @JsonProperty("y") Integer y,
                     @JsonProperty("z") Integer z) {
        this.color = color != null ? color : "";
        this.player = player != null ? player : "";
        this.x = x != null ? x : 0;
        this.y = y != null ? y : 0;
        this.z = z != null ? z : 0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBlock gameBlock = (GameBlock) o;
        return Objects.equals(color, gameBlock.color) &&
                Objects.equals(player, gameBlock.player) &&
                Objects.equals(x, gameBlock.x) &&
                Objects.equals(y, gameBlock.y) &&
                Objects.equals(z, gameBlock.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, player, x, y, z);
    }
}
