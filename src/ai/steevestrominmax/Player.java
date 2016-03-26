package ai.steevestrominmax;

import java.util.List;

import com.github.steevedroz.blackhole.BlackHole;
import com.github.steevedroz.blackhole.BlackHoleBox;
import com.github.steevedroz.blackhole.BlackHolePlayer;

import javafx.scene.paint.Color;

public class Player extends BlackHolePlayer {
    private List<BlackHoleBox> boxes;

    private int toBeDeleted;

    public Player(String name, Color color) {
	super(name, color);
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	this.boxes = boxes;
	BlackHole.setNeighbors(this.boxes, this.size);
	while (boxes.get(toBeDeleted % boxes.size()).getNumber() != null) {
	    toBeDeleted++;
	}
	return toBeDeleted;
    }

}
