package ai.steevestrominmax;

import java.util.List;

import com.github.steevedroz.blackhole.BlackHoleBox;
import com.github.steevedroz.blackhole.BlackHolePlayer;

import javafx.scene.paint.Color;

public class Player extends BlackHolePlayer {
    private List<BlackHoleBox> boxes;

    public Player(String name, Color color) {
	super(name, color);
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	this.boxes = boxes;
	return 0;
    }

}
