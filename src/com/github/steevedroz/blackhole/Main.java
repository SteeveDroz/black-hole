package com.github.steevedroz.blackhole;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	BorderPane root = new BorderPane();
	Scene scene = new Scene(root, 800, 800);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	BlackHole blackHole = new BlackHole();
	// blackHole.registerPlayer(new RandomPlayer("Player 1", Color.RED));
	// blackHole.registerPlayer(new RandomPlayer("Player 2", Color.BLUE));

	HBox box = new HBox(5);
	ComboBox<String> playerPicker1 = new ComboBox<String>();
	ComboBox<String> playerPicker2 = new ComboBox<String>();
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
	}

	go.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {

	    @Override
	    public void handle(Event event) {
		registerPlayer(playerPicker1.getValue(), 1, Color.RED, blackHole);
		registerPlayer(playerPicker2.getValue(), 2, Color.BLUE, blackHole);
		blackHole.setSize(sizePicker.getValue());
		root.setCenter(blackHole);
		blackHole.start();
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

	BlackHolePlayer player1 = null;
	try {
	    Class<?> playerClass = Class.forName("ai." + selected + ".Player");
	    Constructor<?> playerConstructor = playerClass.getConstructor(new Class[] { String.class, Color.class });
	    player1 = (BlackHolePlayer) playerConstructor.newInstance(new Object[] { selected + " " + id, color });
	    blackHole.registerPlayer(player1);
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
