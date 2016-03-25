package com.github.steevedroz.blackhole;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;

public class BlackHoleNumber extends Pane {
    private BlackHolePlayer player;
    private int value;

    public BlackHoleNumber(BlackHolePlayer player, int value) {
	this.player = player;
	this.value = value;

	Ellipse ellipse = new Ellipse(20, 20, 16, 16);
	ellipse.setFill(player.getColor());
	getChildren().add(ellipse);

	Label label = new Label("" + value);
	label.setFont(Font.font(20));
	FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
	label.setPrefWidth(fontLoader.computeStringWidth(label.getText(), label.getFont()));
	label.setPrefHeight(25);
	label.setLayoutX((40 - label.getPrefWidth()) / 2);
	label.setLayoutY((40 - label.getPrefHeight()) / 2);
	getChildren().add(label);
    }

    public BlackHolePlayer getPlayer() {
	return player;
    }

    public int getValue() {
	return value;
    }
}
