package com.github.steevedroz.blackhole;

import java.util.List;

import javafx.scene.paint.Color;

public abstract class BlackHolePlayer {
    private String name;
    private int playerId;
    private Color color;
    protected int size;
    private int points;

    public BlackHolePlayer(String name, Color color, int playerId) {
	this.name = name;
	this.color = color;
	this.playerId = playerId;
	this.points = 0;
    }

    public String getName() {
	return name + " " + playerId;
    }

    public Color getColor() {
	return color;
    }

    public final void askForPoints(BlackHole game) {
	if (game != null) {
	    points = game.getBlackHole().getNeighborsPoints(this);
	}
    }

    public final int getPoints() {
	return points;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public abstract int play(List<BlackHoleBox> boxes);
}
