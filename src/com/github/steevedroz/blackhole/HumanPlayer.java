package com.github.steevedroz.blackhole;

import java.util.List;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HumanPlayer extends BlackHolePlayer {
    private BlackHole blackHole;

    public HumanPlayer(String name, Color color) {
	super(name, color);
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	// TODO Auto-generated method stub
	return 0;
    }

    public void setBlackHole(BlackHole blackHole) {
	this.blackHole = blackHole;
	for (int i = 0; i < this.blackHole.getBoxes().size(); i++) {
	    BlackHoleBox box = this.blackHole.getBoxes().get(i);
	    final int position = i;

	    box.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
		@Override
		public void handle(Event event) {
		    if (box.getNumber() == null && blackHole.getCurrentPlayer() == HumanPlayer.this
			    && blackHole.isPlayable()) {
			try {
			    blackHole.setNumber(HumanPlayer.this, position);
			} catch (IllegalMoveException e) {
			    Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("An error occured");
			    alert.setHeaderText("Fatal error!");
			    alert.setContentText(e.getMessage());
			    alert.showAndWait();
			    Platform.exit();
			}
		    }
		}
	    });
	}
    }
}
