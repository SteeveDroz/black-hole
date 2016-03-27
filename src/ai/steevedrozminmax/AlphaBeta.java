package ai.steevedrozminmax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.steevedroz.blackhole.BlackHole;
import com.github.steevedroz.blackhole.BlackHoleBox;
import com.github.steevedroz.blackhole.BlackHoleNumber;
import com.github.steevedroz.blackhole.BlackHolePlayer;
import com.github.steevedroz.blackhole.IllegalMoveException;

public class AlphaBeta {
    private BlackHolePlayer active;
    private BlackHolePlayer opponent;
    private int size;

    public AlphaBeta(BlackHolePlayer active, BlackHolePlayer opponent, int size) {
	this.active = active;
	this.opponent = opponent;
	this.size = size;
    }

    public Move getMove(List<BlackHoleBox> boxes, int depth, int minOrMax, int parentValue)
	    throws IllegalMoveException {
	if (depth == 0 || isFinal(boxes)) {
	    return evaluate(boxes);
	}

	int numberOfFilledBoxes = 0;
	for (BlackHoleBox box : boxes) {
	    if (box.getNumber() != null) {
		numberOfFilledBoxes++;
	    }
	}
	int nextNumber = numberOfFilledBoxes / 2 + 1;

	List<Move> bestMoves = new ArrayList<Move>();
	bestMoves.add(new Move());
	bestMoves.get(0).value = -minOrMax * Integer.MAX_VALUE;
	for (int i = 0; i < boxes.size(); i++) {
	    if (boxes.get(i).getNumber() == null) {
		List<BlackHoleBox> boxesClone = cloneOf(boxes);
		boxesClone.get(i).setNumber(new BlackHoleNumber(minOrMax > 0 ? active : opponent, nextNumber));
		Move testMove = getMove(boxesClone, depth - 1, -minOrMax, bestMoves.get(0).value);
		if (testMove.value == bestMoves.get(0).value) {
		    testMove.box = i;
		    bestMoves.add(testMove);
		} else if (testMove.value * minOrMax > bestMoves.get(0).value * minOrMax) {
		    bestMoves.clear();
		    testMove.box = i;
		    bestMoves.add(testMove);
		    if (bestMoves.get(0).value * minOrMax > parentValue * minOrMax) {
			break;
		    }
		}
	    }
	}
	Random random = new Random();
	return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    public boolean isFinal(List<BlackHoleBox> boxes) {
	int empty = 0;
	for (BlackHoleBox box : boxes) {
	    if (box.getNumber() == null) {
		empty++;
		if (empty > 1) {
		    return false;
		}
	    }
	}
	return empty == 1;
    }

    public Move evaluate(List<BlackHoleBox> boxes) {
	int value = 0;
	for (BlackHoleBox box : boxes) {
	    if (box.getNumber() == null) {
		continue;
	    }
	    boolean surrounded = true;
	    if (box.getNumber().getPlayer() == active) {
		for (BlackHoleBox neighbor : box.getNeighbors()) {
		    if (neighbor.getNumber() == null) {
			value -= box.getNumber().getValue() / 2;
			surrounded = false;
		    }
		}
		if (surrounded) {
		    value += box.getNumber().getValue();
		}
	    } else if (box.getNumber().getPlayer() == opponent) {
		for (BlackHoleBox neighbor : box.getNeighbors()) {
		    if (neighbor.getNumber() == null) {
			value += box.getNumber().getValue() / 2;
			surrounded = false;
		    }
		}
		if (surrounded) {
		    value -= box.getNumber().getValue();
		}
	    }
	}
	return new Move(value);
    }

    private List<BlackHoleBox> cloneOf(List<BlackHoleBox> boxes) {
	List<BlackHoleBox> boxesClone = new ArrayList<BlackHoleBox>();
	for (BlackHoleBox box : boxes) {
	    boxesClone.add(box.cloneOf());
	}
	BlackHole.setNeighbors(boxesClone, size);
	return boxesClone;
    }
}
