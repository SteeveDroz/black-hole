package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class BlackHole extends AnchorPane {
    private List<BlackHolePlayer> players;
    private int move;
    private boolean playable;

    private int size;
    private List<BlackHoleBox> boxes;
    private int freeBoxes;

    public BlackHole() throws BadSizeException {
	initialize();
	setFocusTraversable(true);
	requestFocus();
	setEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent event) {
		if (event.getCode().equals(KeyCode.X)) {
		    initialize();
		}
	    }
	});
    }

    private void initialize() {
	getChildren().clear();
	generateBoxes();

	players = new ArrayList<BlackHolePlayer>();
	players.add(new BlackHolePlayer("Player 1", Color.RED));
	players.add(new BlackHolePlayer("Player 2", Color.BLUE));
	// players.add(new BlackHolePlayer("Player 3", Color.YELLOW));

	this.freeBoxes = triangularValue(size);
	move = 0;
	playable = true;
    }

    public BlackHoleNumber nextNumber() {
	BlackHoleNumber number = new BlackHoleNumber(players.get(move % players.size()), move / players.size() + 1);
	move++;
	return number;
    }

    public boolean isPlayable() {
	return playable;
    }

    public boolean checkSize(int size) {
	return BlackHole.triangularValue(size) % players.size() == 1;
    }

    public static int triangularValue(int size) {
	return (size + 1) * size / 2;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
	initialize();
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
	playable = false;
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
