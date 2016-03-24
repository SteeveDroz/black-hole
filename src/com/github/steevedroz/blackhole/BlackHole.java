package com.github.steevedroz.blackhole;

import javafx.scene.layout.AnchorPane;

public class BlackHole extends AnchorPane {
    private int size;

    public BlackHole(int size) throws BadSizeException {
	if (!BlackHole.checkSize(size)) {
	    throw new BadSizeException(
		    "A triangle with " + size + " spaces on the side contains an even number of spaces ("
			    + BlackHole.triangularValue(size) + ")");
	}
	this.size = size;
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
}
