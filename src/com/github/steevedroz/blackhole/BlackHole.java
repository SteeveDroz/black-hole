package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class BlackHole extends AnchorPane {
    private int size;

    private List<BlackHoleBox> boxes;
    private static List<BlackHolePlayer> players;
    private static int move;

    public BlackHole(int size) throws BadSizeException {
	if (!BlackHole.checkSize(size)) {
	    throw new BadSizeException(
		    "A triangle with " + size + " spaces on the side contains an even number of spaces ("
			    + BlackHole.triangularValue(size) + ")");
	}
	this.size = size;
	generateBoxes();

    }

    public static BlackHoleNumber nextNumber() {
	if (players == null) {
	    players = new ArrayList<BlackHolePlayer>();
	    players.add(new BlackHolePlayer("Player 1", Color.RED));
	    players.add(new BlackHolePlayer("Player 2", Color.BLUE));
	    move = 0;
	}
	BlackHoleNumber number = new BlackHoleNumber(players.get(move % players.size()), move / players.size() + 1);
	move++;
	System.out.println(move);
	return number;
    }

    public static boolean checkSize(int size) {
	return BlackHole.triangularValue(size) % 2 == 1;
    }

    public static int triangularValue(int size) {
	return (size + 1) * size / 2;
    }

    public int getSize() {
	return size;
    }

    private void generateBoxes() {
	boxes = new ArrayList<BlackHoleBox>();
	for (int i = 0; i < triangularValue(size); i++) {
	    BlackHoleBox box = new BlackHoleBox();
	    boxes.add(box);
	    getChildren().add(box);
	}

	int boxWidth = 40;
	int width = size;
	int counter = 0;
	for (int i = 0; i < boxes.size(); i++) {
	    boxes.get(i).setLayoutX(boxWidth * counter + (size - width) * boxWidth / 2);
	    boxes.get(i).setLayoutY((size - width) * (boxWidth - 3));
	    if (width < size) {
		boxes.get(i).addNeighbor(boxes.get(i - width));
		boxes.get(i).addNeighbor(boxes.get(i - width - 1));
	    }
	    if (counter == width - 1) {
		width--;
		counter = 0;
	    } else {
		boxes.get(i).addNeighbor(boxes.get(i + 1));
		counter++;
	    }
	}
    }
}
