package cardGame;

public class Card {
    private String suit;
    private String name;
    private int value;

    // determines the card value/suit/value/picture
    public Card(String suit, String name, int value, String picture) {
        this.suit = suit;
        this.name = name;
        this.value = value;

    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " of " + suit + " (value " + value + ")";
    }
}