package com.github.steevedroz.blackhole;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String HUMAN_PLAYER = "Human Player";

    @Override
    public void start(Stage primaryStage) {
	BorderPane root = new BorderPane();
	Scene scene = new Scene(root, 800, 800);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	HBox box = new HBox(5);
	ComboBox<String> playerPicker1 = new ComboBox<String>();
	ComboBox<String> playerPicker2 = new ComboBox<String>();

	playerPicker1.getItems().add(HUMAN_PLAYER);
	playerPicker2.getItems().add(HUMAN_PLAYER);
	playerPicker1.setValue(HUMAN_PLAYER);
	playerPicker2.setValue(HUMAN_PLAYER);

	Button go = new Button("GO");
	ComboBox<Integer> sizePicker = new ComboBox<Integer>();

	File ai = new File("ai/");
	File[] aiPlayers = ai.listFiles();
	for (File file : aiPlayers) {
	    playerPicker1.getItems().add(file.getName());
	    playerPicker2.getItems().add(file.getName());
	}

	for (int i = 4; i < 16; i++) {
	    if (BlackHole.triangularValue(i) % 2 == 1) {
		sizePicker.getItems().add(i);
	    }
	    sizePicker.setValue(6);
	}

	go.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {

	    @Override
	    public void handle(Event event) {
		try {
		    BlackHole blackHole = new BlackHole();
		    blackHole.setSize(sizePicker.getValue());
		    registerPlayer(playerPicker1.getValue(), 1, Color.RED, blackHole);
		    registerPlayer(playerPicker2.getValue(), 2, Color.BLUE, blackHole);
		    root.setCenter(blackHole);
		    blackHole.start();
		} catch (IllegalMoveException e) {
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("An error occured");
		    alert.setHeaderText("Fatal error!");
		    alert.setContentText(e.getMessage());
		    alert.showAndWait();
		    primaryStage.close();
		}
	    }
	});

	box.getChildren().add(playerPicker1);
	box.getChildren().add(new Label("vs"));
	box.getChildren().add(playerPicker2);
	box.getChildren().add(new Label("Size:"));
	box.getChildren().add(sizePicker);
	box.getChildren().add(go);

	root.setCenter(box);
	primaryStage.setScene(scene);
	primaryStage.setTitle("Black Hole");
	primaryStage.show();
    }

    public static void main(String[] args) {
	launch(args);
    }

    private void registerPlayer(String selected, int id, Color color, BlackHole blackHole) {
	if (selected == HUMAN_PLAYER) {
	    blackHole.registerPlayer(new HumanPlayer("Human player " + id, color));
	    return;
	}
	BlackHolePlayer player = null;
	try {
	    Class<?> playerClass = Class.forName("ai." + selected + ".Player");
	    Constructor<?> playerConstructor = playerClass.getConstructor(new Class[] { String.class, Color.class });
	    player = (BlackHolePlayer) playerConstructor.newInstance(new Object[] { selected + " " + id, color });
	    blackHole.registerPlayer(player);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	} catch (SecurityException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
    }
}
