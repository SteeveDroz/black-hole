package com.github.steevedroz.blackhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class BlackHole extends AnchorPane {
    private Map<BlackHolePlayer, Integer> players;
    private List<BlackHolePlayer> orderedPlayers;
    private int activePlayer;
    private boolean playable;

    private int size;
    private List<BlackHoleBox> boxes;
    private int freeBoxes;
    private BlackHoleBox blackHole;

    public BlackHole() {
	initialize();
	setFocusTraversable(true);
	requestFocus();
	players = new HashMap<BlackHolePlayer, Integer>();
	orderedPlayers = new ArrayList<BlackHolePlayer>();
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

	activePlayer = 0;

	this.freeBoxes = triangularValue(size);
	playable = true;
	blackHole = null;
    }

    public void registerPlayer(BlackHolePlayer player) {
	players.put(player, 1);
	orderedPlayers.add(player);
	if (player instanceof HumanPlayer) {
	    HumanPlayer humanPlayer = (HumanPlayer) player;
	    humanPlayer.setBlackHole(this);
	}
    }

    public void start() {
	for (BlackHolePlayer player : orderedPlayers) {
	    player.setSize(size);
	}
	if (!(orderedPlayers.get(0) instanceof HumanPlayer)) {
	    new Thread(new Runnable() {
		@Override
		public void run() {
		    setNumber(orderedPlayers.get(0), orderedPlayers.get(0).play(cloneOfBoxes()));
		}
	    }).start();
	}
    }

    public BlackHolePlayer getCurrentPlayer() {
	return orderedPlayers.get(activePlayer % orderedPlayers.size());
    }

    public BlackHoleNumber getNumberFromPlayer(BlackHolePlayer player) {
	return new BlackHoleNumber(player, players.get(player));
    }

    public void setNumber(BlackHolePlayer player, int position) {
	boxes.get(position).setNumber(getNumberFromPlayer(player));
	players.put(player, players.get(player) + 1);
	if (playable) {
	    activePlayer++;
	    BlackHolePlayer nextPlayer = getCurrentPlayer();
	    if (!(nextPlayer instanceof HumanPlayer)) {
		new Thread(new Runnable() {

		    @Override
		    public void run() {
			setNumber(nextPlayer, nextPlayer.play(boxes));
		    }
		}).start();
	    }
	}
    }

    public boolean isPlayable() {
	return playable;
    }

    public boolean checkSize(int size) {
	if (players.size() == 0) {
	    return false;
	}
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
	BlackHole.setNeighbors(boxes, size);
    }

    public static void setNeighbors(List<BlackHoleBox> boxes, int size) {
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

    public List<BlackHoleBox> getBoxes() {
	return boxes;
    }

    public BlackHoleBox getBlackHole() {
	return blackHole;
    }

    private void endOfGame() {
	playable = false;
	StringBuilder str = new StringBuilder();
	for (BlackHoleBox box : boxes) {
	    if (box.getNumber() == null) {
		blackHole = box;
		BlackHolePlayer winner = null;
		for (BlackHolePlayer player : players.keySet()) {
		    player.askForPoints(this);
		    str.append(player.getName()).append(": ").append(player.getPoints()).append(" point");
		    if (player.getPoints() != 1) {
			str.append("s");
		    }
		    str.append(System.lineSeparator());
		    if (winner == null || player.getPoints() < winner.getPoints()) {
			winner = player;
		    }
		}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText(str.toString());
			alert.setHeaderText("Results");
			alert.setTitle("End game");
			alert.show();
		    }
		});
	    }
	}
    }

    private List<BlackHoleBox> cloneOfBoxes() {
	List<BlackHoleBox> clone = new ArrayList<BlackHoleBox>();
	for (BlackHoleBox box : boxes) {
	    clone.add(box.cloneOf());
	}
	BlackHole.setNeighbors(clone, size);
	return clone;
    }
}
