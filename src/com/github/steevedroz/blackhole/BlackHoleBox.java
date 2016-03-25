package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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

	addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
	    @Override
	    public void handle(Event event) {
		if (number == null && BlackHole.isPlayable()) {
		    setNumber(BlackHole.nextNumber());
		}
	    }
	});
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
	this.number = number;
	getChildren().add(number);
	parent.fillBox();
    }

    public void blackHole() {
	for (BlackHoleBox neighbor : getNeighbors()) {
	    neighbor.getNumber().getPlayer().addPoints(neighbor.getNumber().getValue());
	}
    }
}
