package sample;

public class Ability {
    private String name;
    private String description;
    private int cost;
    private int numOfDice;
    private int diceSides;
    private int strengthPrereq;
    private int dexterityPrereq;
    private int wisdomPrereq;
    public static final int STRENGTH=1,DEXTERITY=2,WISDOM=3;
    private int damageType;

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Ability(String name, String description,
                   int cost, String damage, int sPrereq,
                   int dPrereq, int wPrereq, int damageType) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.strengthPrereq = sPrereq;
        this.dexterityPrereq = dPrereq;
        this.wisdomPrereq = wPrereq;
        this.damageType = damageType;
        //damage need to go from 2d6 -> 2 Dice, 6 Sides
        int dLoc = damage.indexOf("d");
        numOfDice = Integer.parseInt(damage.substring(0,dLoc));
        diceSides = Integer.parseInt(damage.substring(dLoc+1));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumOfDice() {
        return numOfDice;
    }

    public void setNumOfDice(int numOfDice) {
        this.numOfDice = numOfDice;
    }

    public int getDiceSides() {
        return diceSides;
    }

    public void setDiceSides(int diceSides) {
        this.diceSides = diceSides;
    }

    public int getStrengthPrereq() {
        return strengthPrereq;
    }

    public void setStrengthPrereq(int strengthPrereq) {
        this.strengthPrereq = strengthPrereq;
    }

    public int getDexterityPrereq() {
        return dexterityPrereq;
    }

    public void setDexterityPrereq(int dexterityPrereq) {
        this.dexterityPrereq = dexterityPrereq;
    }

    public int getWisdomPrereq() {
        return wisdomPrereq;
    }

    public void setWisdomPrereq(int wisdomPrereq) {
        this.wisdomPrereq = wisdomPrereq;
    }

    public int getDamageType() {
        return damageType;
    }

    public void setDamageType(int damageType) {
        this.damageType = damageType;
    }
}
