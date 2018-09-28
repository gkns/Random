package org.ns.cleanstrike;

import java.util.ArrayList;

public class Player {
	private int playerId;
	private String playerName;
	private ArrayList<Integer> moves;
	private int score;
	private boolean hasWon, isTied;
	
	public Player(int id_, String playerName_) {
		this.playerId = id_;
		this.playerName = playerName_;
		this.score = 0;
		this.hasWon = this.isTied = false;
		this.moves = new ArrayList<Integer>();
	}
	
	public String getName() {
		return this.playerName;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void addPoints(int points) {
		this.score += points;
	}
	
	public boolean hasWon(Player otherPlayer_) {
		// winning player should have winningScore points and 
		// it should be minimum winningThreshold points more than the opponent.
		if ((GamePlay.WINNING_SCORE >= this.getScore()) && 
				(GamePlay.WINNING_THRESHOLD >= this.getScore() - otherPlayer_.getScore())) {
			hasWon = true;
		}
		return hasWon;
	}
	
	public boolean isTied(Player otherPlayer_, Board board_) {
		if (this.hasWon(otherPlayer_)) {
			isTied = false;
			return isTied;
		} else {
			// draw means the other player hasn't won and the board has no coins left
			if (board_.getCoinsIn().size() == 0 && !otherPlayer_.hasWon(this)) {
				isTied  = true;
				return isTied;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Player ID: " + this.playerId
				+ " Player Name: " + this.playerName;
	}
}
