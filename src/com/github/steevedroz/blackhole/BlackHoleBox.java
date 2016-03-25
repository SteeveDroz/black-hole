package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.shape.Ellipse;

public class BlackHoleBox extends Ellipse {
    private List<BlackHoleBox> neighbors;
    private BlackHoleNumber number;

    private static Random random = new Random();

    public BlackHoleBox() {
	super(random.nextInt(360) + 20, random.nextInt(360) + 20, 20, 20);

	this.neighbors = new ArrayList<BlackHoleBox>();
	this.number = null;
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
    }
}
