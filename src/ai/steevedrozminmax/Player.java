package ai.steevedrozminmax;

import java.util.List;

import com.github.steevedroz.blackhole.BlackHoleBox;
import com.github.steevedroz.blackhole.BlackHolePlayer;
import com.github.steevedroz.blackhole.HumanPlayer;

import javafx.scene.paint.Color;

public class Player extends BlackHolePlayer {
    private int depth;

    public Player(String name, Color color) {
	super(name, color);
	depth = 5;
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	AlphaBeta alphaBeta = new AlphaBeta(this, new HumanPlayer("Dummy", Color.YELLOW), size);
	return alphaBeta.getMove(boxes, depth, 1, Integer.MAX_VALUE).box;
    }

}
