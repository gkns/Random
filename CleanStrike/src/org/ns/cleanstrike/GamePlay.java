package org.ns.cleanstrike;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GamePlay {
	public static int WINNING_THRESHOLD=3;
	public static int WINNING_SCORE=5;
	public static int DEFUNCT_COIN_SCORE = -2;
	
	private static final String coinDataFile = "resources/Coins.csv";
	private static final String playersDataFile = "resources/Players.csv";
	private static final String movesDataFile = "resources/Moves.csv";
	
	private static GamePlay game;
	private List<Player> players;
	List<Coin> coins;

	public List<Player> getPlayers() {
		return players;
	}

	public List<MoveType> getMoves() {
		return moves;
	}

	public Board getBoard() {
		return board;
	}

	private List<MoveType> moves;
	private Board board;
	
	public void init() {
		// Get coins
		String coinsData = game.readCSV(coinDataFile);
		coins = game.parseCoinsData(coinsData);

		// Get Players
		String playersData = game.readCSV(playersDataFile);
		players = game.parsePlayerData(playersData);

		// Get Moves
		String movesData = game.readCSV(movesDataFile);
		moves = game.parseMovesData(movesData);

		// Fill the board, add all the coins initially
		board = new Board(coins);
	}
	
	public boolean play(Player currentPlayer, Player otherPlayer) {
		/*
		 * Iterate over moves
		 * Assumption 1: There are only two players supported at the moment. 
		 * Assumption 2: Player one always starts.
		 * Assumption 3: A strike implies same player gets a repeat chance and next move is his move.
		 */
		int i = 0; // to iterate over moves
		int swapCount = 0;
		boolean isSwapped = false;

		System.out.println("Players: " + game.players.toString());
		System.out.println("Moves: " + game.moves.toString());
		System.out.println("Coins: " + game.board.getCoinsIn().toString());

		while (i < game.moves.size() && game.board.getCoinsIn().size() > 0) {
			MoveType move = game.moves.get(i);
			switch (move) {
			case MOVE_STRIKE: {
				currentPlayer.addPoints(game.getCoinTypeForMoveType(MoveType.MOVE_STRIKE).getPoint());
				if (!game.board.removeCoin(game.getCoinTypeForMoveType(MoveType.MOVE_STRIKE))) {
					System.err.println("Error removing coin");
				}
				break;
			}
			case MOVE_MULTISTRIKE: {
				currentPlayer.addPoints(game.getCoinTypeForMoveType(MoveType.MOVE_MULTISTRIKE).getPoint());
				game.board.removeCoin(game.getCoinTypeForMoveType(MoveType.MOVE_MULTISTRIKE));
				break;
			}
			case MOVE_RED_STRIKE: {
				currentPlayer.addPoints(game.getCoinTypeForMoveType(MoveType.MOVE_RED_STRIKE).getPoint());
				if (!game.board.removeCoin(game.getCoinTypeForMoveType(MoveType.MOVE_RED_STRIKE))) {
					System.err.println("Error removing coin");
				}
				break;
			}
			case MOVE_STRIKER_STRIKE: {
				currentPlayer.addPoints(game.getCoinTypeForMoveType(MoveType.MOVE_STRIKER_STRIKE).getPoint());
				// Pass to next player
				Player temp = currentPlayer;
				currentPlayer = otherPlayer;
				otherPlayer = temp;
				swapCount++;
				break;
			}
			case MOVE_DEFUNCT_STRIKE: {
				currentPlayer.addPoints(DEFUNCT_COIN_SCORE);
				// Pass to next player
				Player temp = currentPlayer;
				currentPlayer = otherPlayer;
				otherPlayer = temp;
				swapCount++;
				break;
			}
			case MOVE_NONE: {
				// Pass to next player
				Player temp = currentPlayer;
				currentPlayer = otherPlayer;
				otherPlayer = temp;
				swapCount++;
			}
			}
			i++;
		}
		if (swapCount%2 != 0) {
			isSwapped = true;
		}

		return isSwapped;
	}

	public static void main(String[] args) {
		game = new GamePlay(); 
		game.init();
		Player currentPlayer = game.players.get(0);
		Player otherPlayer = game.players.get(1);
		boolean winnerIsSwapped = game.play(currentPlayer, otherPlayer);
		
		System.out.println(winnerIsSwapped);

		if (winnerIsSwapped) {
			Player temp = currentPlayer;
			currentPlayer = otherPlayer;
			otherPlayer = temp;
		}

		if (currentPlayer.hasWon(otherPlayer)) {
			System.out.println(currentPlayer.getName() + " won the game. Final score: " + 
					currentPlayer.getScore() + "-" + otherPlayer.getScore());
		} else if (otherPlayer.hasWon(currentPlayer)) {
			System.out.println(otherPlayer.getName() + " won the game. Final score: " + 
					otherPlayer.getScore() + "-" + currentPlayer.getScore());
		} else if (currentPlayer.isTied(otherPlayer, game.board)) {
			System.out.println("The game is tied. Final score: "
						+ currentPlayer.getScore() + "-" + otherPlayer.getScore());
		} else {
			System.out.println("Not enough moves to determine the outcome!");
			if (winnerIsSwapped) { //Just to display scores in Player1-Player2 format
				Player temp = currentPlayer;
				currentPlayer = otherPlayer;
				otherPlayer = temp;
			}
			System.out.println("Final score: " + currentPlayer.getScore() + "-" + otherPlayer.getScore());
		}
	}

	private CoinType getCoinTypeForMoveType (MoveType moveType) {
		CoinType coinType = CoinType.COIN_TYPE_INVALID;
		switch (moveType) {
		case MOVE_STRIKE:
			return CoinType.COIN_TYPE_BLACK;
		// Since we don't have multiple colors, return just black, which has 1 point
		case MOVE_MULTISTRIKE:
			return CoinType.COIN_TYPE_BLACK;
		case MOVE_RED_STRIKE:
			return CoinType.COIN_TYPE_RED;
		case MOVE_STRIKER_STRIKE:
			return CoinType.COIN_TYPE_STRIKER;
		}
		return coinType;
	}
	
	private List<MoveType> parseMovesData(String movesData_) {
		List<MoveType> moves = new ArrayList<>();
		String[] csvLines = movesData_.split(System.lineSeparator());
		for (String line: csvLines) {
			line = line.toLowerCase();
			if (line.contains(MoveType.MOVE_RED_STRIKE.toString().toLowerCase())) {
				moves.add(MoveType.MOVE_RED_STRIKE);
			} else if (line.contains(MoveType.MOVE_MULTISTRIKE.toString().toLowerCase())) {
				moves.add(MoveType.MOVE_MULTISTRIKE);
			} else if (line.contains(MoveType.MOVE_STRIKER_STRIKE.toString().toLowerCase())) {
				moves.add(MoveType.MOVE_STRIKER_STRIKE);
			} else if (line.contains(MoveType.MOVE_DEFUNCT_STRIKE.toString().toLowerCase())) {
				moves.add(MoveType.MOVE_DEFUNCT_STRIKE);
			} else if (line.contains(MoveType.MOVE_STRIKE.toString().toLowerCase())) {
				moves.add(MoveType.MOVE_STRIKE);
			}
		}
		return moves;
	}
	
	private List<Player> parsePlayerData(String playersData_) {
		List<Player> players = new ArrayList<>();
		String[] csvLines = playersData_.split(System.lineSeparator());
		for (String line : csvLines) {
			StringTokenizer st = new StringTokenizer(line, ",");
			Player player = new Player(Integer.parseInt(st.nextToken()), st.nextToken());
			players.add(player);
		}
		return players;
		
	}
	
	private List<Coin> parseCoinsData (String coinsData_) {
		List<Coin> coins = new ArrayList<>();
		String[] csvLines = coinsData_.split(System.lineSeparator());
		for (String line : csvLines) {
			StringTokenizer st = new StringTokenizer(line, ",");
			Coin coin = new Coin (Integer.parseInt(st.nextToken()),
					parseCoinType(st.nextToken()), Integer.parseInt(st.nextToken()));
			coins.add(coin);
		}
		return coins;
	}
	
	private CoinType parseCoinType(String coinType) {
		if (CoinType.COIN_TYPE_BLACK.toString().toLowerCase().contains(coinType.toLowerCase())) {
			return CoinType.COIN_TYPE_BLACK;
		} else if (CoinType.COIN_TYPE_WHITE.toString().toLowerCase().contains(coinType.toLowerCase())) {
			return CoinType.COIN_TYPE_WHITE;
		} else if (CoinType.COIN_TYPE_RED.toString().toLowerCase().contains(coinType.toLowerCase())) {
			return CoinType.COIN_TYPE_RED;
		} else if (CoinType.COIN_TYPE_STRIKER.toString().toLowerCase().contains(coinType.toLowerCase())) {
			return CoinType.COIN_TYPE_STRIKER;
		}
		return null;
	}
	
	private String readCSV(String filePath) {
		StringBuilder csvData = new StringBuilder();
		String ret="";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File csvFile = new File(classLoader.getResource(filePath).getFile());
		FileReader fr = null;
		try {
			fr = new FileReader(csvFile);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Input file: " +  filePath + " not found");
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String line="";
		do {
			try {
				line = br.readLine();
				if (line == null || line.length() == 0) {
					break;
				}
				csvData.append(line).append(System.lineSeparator());
			} catch (IOException e) {
				System.err.println("ERROR: Cannot read file: " + filePath);
				e.printStackTrace();
			}
		} while (line.length() != 0);
		ret = csvData.toString();
		return ret;
	}

}