package ai.steevedrozminmax;

import java.util.List;

import com.github.steevedroz.blackhole.BlackHoleBox;
import com.github.steevedroz.blackhole.BlackHolePlayer;
import com.github.steevedroz.blackhole.HumanPlayer;

import javafx.scene.paint.Color;

public class Player extends BlackHolePlayer {
    private int depth;

    public Player(Color color, int playerId) {
	super("SteeveDroz min-max", color, playerId);
	depth = 5;
    }

    @Override
    public int play(List<BlackHoleBox> boxes) {
	AlphaBeta alphaBeta = new AlphaBeta(this, new HumanPlayer(Color.YELLOW, 0), size);
	return alphaBeta.getMove(boxes, depth, 1, Integer.MAX_VALUE).box;
    }

}
