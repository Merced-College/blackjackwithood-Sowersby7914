
package cardGame;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;
    private int score;
    private int wins;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<Card>(); // Fixed: Cards -> cards
        this.score = 0;
        this.wins = 0;
    }

    // added wins tracker
    public void addWin() {
        this.wins++;
    }

    // returns wins
    public int getWins() {
        return wins;
    }

    // gets hand value
    public int getHandValue() {
        int sum = 0;
        int aces = 0;
        // counts aces in hands
        for (Card card : cards) {
            if (card.getValue() == 11) {
                aces++;
            } else {
                sum += card.getValue();
            }
        }
        // counts aces as ones
        for (int i = 0; i < aces; i++) {
            if (sum + 11 <= 21) {
                sum += 11;
            } else {
                sum += 1;
            }
        }
        return sum;
    }

    // adds card to hand
    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public void clearHand() {
        cards.clear();
    }

    public void updateScore(int points) {
        this.score += points;
        System.out.print(name + "'s score is " + score);
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "player: " + name + " cards = " + cards; // Fixed: hand -> cards

    }
}
