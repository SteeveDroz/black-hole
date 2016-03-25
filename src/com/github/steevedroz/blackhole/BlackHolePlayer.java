package com.github.steevedroz.blackhole;

import javafx.scene.paint.Color;

public class BlackHolePlayer {
    private String name;
    private Color color;
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

    public void addPoints(int amount) {
	points += amount;
    }

    public int getPoints() {
	return points;
    }
}
