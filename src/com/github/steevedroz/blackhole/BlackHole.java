package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class BlackHole extends AnchorPane {
    private static List<BlackHolePlayer> players;
    private static int move;
    private static boolean playable;

    private int size;
    private List<BlackHoleBox> boxes;
    private int freeBoxes;

    public BlackHole(int size) throws BadSizeException {
	if (!BlackHole.checkSize(size)) {
	    throw new BadSizeException(
		    "A triangle with " + size + " spaces on the side contains an even number of spaces ("
			    + BlackHole.triangularValue(size) + ")");
	}
	this.size = size;
	this.freeBoxes = triangularValue(size);
	generateBoxes();

    }

    public static void initializePlayers() {
	if (players == null) {
	    players = new ArrayList<BlackHolePlayer>();
	    players.add(new BlackHolePlayer("Player 1", Color.RED));
	    players.add(new BlackHolePlayer("Player 2", Color.BLUE));
	    // players.add(new BlackHolePlayer("Player 3", Color.YELLOW));
	    move = 0;
	    playable = true;
	}
    }

    public static BlackHoleNumber nextNumber() {
	initializePlayers();
	BlackHoleNumber number = new BlackHoleNumber(players.get(move % players.size()), move / players.size() + 1);
	move++;
	return number;
    }

    public static boolean isPlayable() {
	return playable;
    }

    public static boolean checkSize(int size) {
	initializePlayers();
	return BlackHole.triangularValue(size) % BlackHole.players.size() == 1;
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
	    BlackHoleBox box = new BlackHoleBox(this);
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

    public void fillBox() {
	freeBoxes--;
	if (freeBoxes == 1) {
	    endOfGame();
	}
    }

    private void endOfGame() {
	BlackHole.playable = false;
	for (BlackHoleBox box : boxes) {
	    if (box.getNumber() == null) {
		box.blackHole();
		BlackHolePlayer winner = players.get(0);
		for (BlackHolePlayer player : players) {
		    System.out.println(player.getName() + ": " + player.getPoints() + " point(s)");
		    if (player.getPoints() < winner.getPoints()) {
			winner = player;
		    }
		}
		System.out.println(winner.getName() + " wins!");
	    }
	}
    }
}
