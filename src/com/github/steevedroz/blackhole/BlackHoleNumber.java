package com.github.steevedroz.blackhole;

import javafx.scene.shape.Ellipse;

public class BlackHoleNumber extends Ellipse {
    private BlackHolePlayer player;
    private int value;

    public BlackHoleNumber(BlackHolePlayer player, int value) {
	this.player = player;
	this.value = value;
    }

    public BlackHolePlayer getPlayer() {
	return player;
    }

    public int getValue() {
	return value;
    }
}
