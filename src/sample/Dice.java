package sample;

public class Dice {

    public int statRoll() {
        int die1 = (int) (1 + Math.random() * 6);
        int die2 = (int) (1 + Math.random() * 6);
        int die3 = (int) (1 + Math.random() * 6);
        int die4 = (int) (1 + Math.random() * 6);

        //if die1 is the smallest
        if(die1 <= die2 && die1 <= die3 && die1 <= die4) {
            return die2+die3+die4;
        }
        //if die2 is the smallest
        else if(die2 <= die1 && die2 <= die3 && die2 <= die4) {
            return die1+die3+die4;
        }
        //if die3 is the smallest
        else if(die3 <= die1 && die3 <= die2 && die3 <= die4) {
            return die1+die2+die4;
        }
        //die 4 is the smallest
        else {
            return die1+die2+die3;
        }
    }
}
