package com.github.steevedroz.blackhole;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    BorderPane root = new BorderPane();
	    Scene scene = new Scene(root, 400, 400);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	    ComboBox<Integer> sizePicker = new ComboBox<Integer>();
	    sizePicker.getItems().add(null);
	    for (int i = 3; i < 15; i++) {
		if (BlackHole.checkSize(i)) {
		    sizePicker.getItems().add(i);
		}
	    }
	    sizePicker.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {
		@Override
		public void handle(Event event) {
		    try {
			if (sizePicker.getValue() != null) {
			    root.setCenter(new BlackHole(sizePicker.getValue()));
			}
		    } catch (BadSizeException e) {
			sizePicker.setValue(null);
		    }
		}
	    });

	    root.setCenter(sizePicker);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Black Hole");
	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	launch(args);
    }
}
