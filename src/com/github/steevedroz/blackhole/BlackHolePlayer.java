package com.github.steevedroz.blackhole;

import javafx.scene.paint.Color;

public class BlackHolePlayer {
    private String name;
    private Color color;

    public BlackHolePlayer(String name, Color color) {
	this.name = name;
	this.color = color;
    }

    public String getName() {
	return name;
    }

    public Color getColor() {
	return color;
    }
}
