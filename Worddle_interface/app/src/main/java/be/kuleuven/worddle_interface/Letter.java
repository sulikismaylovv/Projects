package be.kuleuven.worddle_interface;

public class Letter {
    private boolean used;
    private boolean isPresent;
    private boolean wrongPlacement;
    private char letter ;

    public Letter(char x) {

        used = false;
        isPresent = false;
        wrongPlacement = false;
        letter = x;

    }

    public boolean getIsPresent() {
        return isPresent;
    }

    public boolean getIsUsed() {
        return used;
    }

    public boolean getWrongPlacement() {
        return wrongPlacement;
    }

    public char getLetter() {
        return letter;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setWrongPlacement(boolean wrongPlacement) {
        this.wrongPlacement = wrongPlacement;
    }

    public void setLetter(char letter22) {
        this.letter = letter22;
    }

    public String getLetterS(){
        return String.valueOf(letter);
    }

    //BoxY.setBackground(getResources().getDrawable(R.drawable.green_back));

}
