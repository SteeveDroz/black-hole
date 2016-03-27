package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class BlackHoleBox extends Pane {
    private List<BlackHoleBox> neighbors;
    private BlackHoleNumber number;
    private BlackHole parent;

    public BlackHoleBox(BlackHole parent) {
	Ellipse ellipse = new Ellipse(20, 20, 20, 20);
	ellipse.setFill(null);
	ellipse.setStroke(Color.BLACK);
	ellipse.setStrokeWidth(2);
	getChildren().add(ellipse);

	this.neighbors = new ArrayList<BlackHoleBox>();
	this.number = null;
	this.parent = parent;
    }

    public void addNeighbor(BlackHoleBox neighbor) {
	addSingleWayNeighbor(neighbor);
	neighbor.addSingleWayNeighbor(this);
    }

    private void addSingleWayNeighbor(BlackHoleBox neighbor) {
	neighbors.add(neighbor);
    }

    public List<BlackHoleBox> getNeighbors() {
	return neighbors;
    }

    public BlackHoleNumber getNumber() {
	return number;
    }

    public void setNumber(BlackHoleNumber number) {
	if (this.number != null) {
	    throw new IllegalMoveException("This box is already taken.");
	}
	this.number = number;
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		getChildren().add(number);
	    }
	});
	parent.fillBox();
    }

    public int getNeighborsPoints(BlackHolePlayer player) {
	int points = 0;
	for (BlackHoleBox neighbor : getNeighbors()) {
	    if (neighbor.getNumber().getPlayer().equals(player)) {
		points += neighbor.getNumber().getValue();
	    }
	}
	return points;
    }

    public BlackHoleBox cloneOf() {
	BlackHoleBox box = new BlackHoleBox(new BlackHole());
	if (number != null) {
	    box.number = number.cloneOf();
	}
	return box;
    }

    @Override
    public String toString() {
	if (getNumber() == null) {
	    return "";
	}
	return getNumber().getPlayer().getName() + " (" + getNumber().getValue() + ")";
    }
}
