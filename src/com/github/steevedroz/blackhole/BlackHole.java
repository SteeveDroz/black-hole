package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BlackHole extends AnchorPane {
    private int size;

    private List<BlackHoleBox> boxes;

    public BlackHole(int size) throws BadSizeException {
	if (!BlackHole.checkSize(size)) {
	    throw new BadSizeException(
		    "A triangle with " + size + " spaces on the side contains an even number of spaces ("
			    + BlackHole.triangularValue(size) + ")");
	}
	this.size = size;
	generateBoxes();
	getChildren().add(new Label("Taille : " + size));
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

	int width = size;
	int counter = width;
	for (int i = 0; i < boxes.size(); i++) {
	    if (width < size) {
		boxes.get(i).addNeighbor(boxes.get(i - width));
		boxes.get(i).addNeighbor(boxes.get(i - width - 1));
	    }
	    if (counter == 1) {
		width--;
		counter = width;
	    } else {
		boxes.get(i).addNeighbor(boxes.get(i + 1));
		counter--;
	    }
	}
    }
}
