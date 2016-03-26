package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class RandomPlayer extends BlackHolePlayer {
    Random random;

    public RandomPlayer(String name, Color color) {
	super(name, color);
	random = new Random();
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	List<Integer> positions = new ArrayList<Integer>();
	for (int i = 0; i < boxes.size(); i++) {
	    BlackHoleBox box = boxes.get(i);
	    if (box.getNumber() == null) {
		positions.add(i);
	    }
	}
	return positions.get(random.nextInt(positions.size()));
    }
}
