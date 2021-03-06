package ai.systematic;

import java.util.List;

import com.github.steevedroz.blackhole.BlackHoleBox;
import com.github.steevedroz.blackhole.BlackHolePlayer;

import javafx.scene.paint.Color;

public class Player extends BlackHolePlayer {

    private int boxCounter;

    public Player(Color color, int playerId) {
	super("Systematic", color, playerId);
	boxCounter = 0;
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	while (boxes.get(boxCounter % boxes.size()).getNumber() != null) {
	    boxCounter++;
	}
	return boxCounter;
    }

}
