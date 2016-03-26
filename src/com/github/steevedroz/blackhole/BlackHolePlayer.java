package com.github.steevedroz.blackhole;

import java.util.List;

import javafx.scene.paint.Color;

public abstract class BlackHolePlayer {
    protected String name;
    protected Color color;
    private int points;

    public BlackHolePlayer(String name, Color color) {
	this.name = name;
	this.color = color;
	this.points = 0;
    }

    public String getName() {
	return name;
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

    public abstract int play(List<BlackHoleBox> boxes);
}
