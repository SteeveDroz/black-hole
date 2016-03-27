package ai.steevedrozminmax;

public class Move {
    public int box;
    public int value;

    public Move() {
    }

    public Move(int value) {
	this.value = value;
    }

    public Move(int box, int value) {
	this.box = box;
	this.value = value;
    }
}
