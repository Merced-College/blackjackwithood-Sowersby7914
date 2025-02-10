/*
Gongyu Yan
Alejandro Perez
Ryan Sowersby
*/
package cardGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import cardGame.Card;
import cardGame.Player;

public class CardGame {

	private static ArrayList<Card> deckOfCards = new ArrayList<Card>();
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static Scanner scanner = new Scanner(System.in);

	// determine number of players in game
	public static void main(String[] args) {
		System.out.print("enter number of players (2-4): ");
		int numPlayers = scanner.nextInt();
		scanner.nextLine();

		if (numPlayers < 2 || numPlayers > 4) {
			System.out.println("invalid number of players. Setting to 2.");
			numPlayers = 2;
		}

		// creating the player profiles
		for (int i = 1; i <= numPlayers; i++) {
			System.out.print("Enter name for player " + i + ": ");
			String name = scanner.nextLine();
			players.add(new Player(name));
		}

		boolean playAgain = true;
		// play again feature
		while (playAgain) {

			deckOfCards.clear();
			for (Player player : players) {
				player.clearHand();
			}
			// if number of players is smaller than 2 or bigger than 4, player count
			// defaults to 2 players

			Scanner input = null;
			try {
				input = new Scanner(new File("cards.txt"));
			} catch (FileNotFoundException e) {
				// error
				System.out.println("error");
				e.printStackTrace();
				return;
			}

			while (input.hasNext()) {
				String[] fields = input.nextLine().split(",");
				// public Card(String cardSuit, String cardName, int cardValue, String
				// cardPicture) {
				Card newCard = new Card(fields[0], fields[1].trim(),
						Integer.parseInt(fields[2].trim()), fields[3]);
				deckOfCards.add(newCard);
			}

			shuffle();

			// for(Card c: deckOfCards)
			// System.out.println(c);

			// deal 2 cards to each player
			for (Player player : players) {
				for (int i = 0; i < 2; i++) {
					player.addCard(deckOfCards.remove(0));
				}
				System.out.println(player);
				System.out.println(player.getName() + " has a hand value of " + player.getHandValue());
			}
			System.out.println();

			determineWinner();

			// check for pairs
			for (Player player : players) {
				System.out.println(player.getName() + " has pairs: " + checkFor2Kind(player.getCards()));
			}
			// determining whether to continue the game or not
			System.out.print("Do you want to play another round? (y/n)");
			String response = scanner.nextLine().toLowerCase();
			playAgain = response.equals("y");

		}
	}

	// end main

	public static void shuffle() {

		// shuffling the cards by deleting and reinserting
		for (int i = 0; i < deckOfCards.size(); i++) {
			int index = (int) (Math.random() * deckOfCards.size());
			Card c = deckOfCards.remove(index);
			// System.out.println("c is " + c + ", index is " + index);
			deckOfCards.add(c);
		}
	}

	// check for 2 of a kind in the players hand
	public static boolean checkFor2Kind(ArrayList<Card> cards) {
		int count = 0;
		Card current = null;
		Card next = null;

		for (int i = 0; i < cards.size() - 1; i++) {
			current = cards.get(i);
			for (int j = i + 1; j < cards.size(); j++) {
				next = cards.get(j);
				if (current.equals(next)) {
					count++;
				}
			}
		}
		return count == 1;
	}

	// determining the winner of the hand
	public static void determineWinner() {
		int highestValidScore = 0;
		ArrayList<Player> winners = new ArrayList<>();

		for (Player player : players) {
			int currentScore = player.getHandValue();
			System.out.println(player.getName() + " has a hand value of " + currentScore);
			System.out.println();
			if (currentScore <= 21) {
				if (currentScore > highestValidScore) {
					highestValidScore = currentScore;
					winners.clear();
					winners.add(player);
				} else if (currentScore == highestValidScore) {
					winners.add(player);
				}
			}
		}

		if (winners.isEmpty()) {
			System.out.println("All players busted. No winner.");
		} else {
			System.out.println("Winner(s):");
			for (int i = 0; i < winners.size(); i++) {
				Player winner = winners.get(i);
				winner.addWin(); // Increment win counter for winner
				System.out.print(winner.getName() + " with score " + highestValidScore);
				if (i < winners.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println();

			// Display win statistics
			System.out.println("\nWin Statistics:");
			for (Player player : players) {
				System.out.println(player.getName() + ": " + player.getWins() + " wins");
			}
			System.out.println();
		}
	}
}
// end class