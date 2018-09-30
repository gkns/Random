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
		if ((this.getScore() >= GamePlay.WINNING_SCORE) && 
				((this.getScore() - otherPlayer_.getScore())) >= GamePlay.WINNING_THRESHOLD) {
			hasWon = true;
		}
		return hasWon;
	}
	
	public boolean isTied(Player otherPlayer_, Board board_) {
		isTied = false;
		if (this.hasWon(otherPlayer_) || otherPlayer_.hasWon(this)) {
			isTied = false;
		} else {
			// draw means the board has no coins left
			// and the highest scorer doesn't have minimum winning criteria
			// minimum of 5 points and leading by atleast 3 point
			if (board_.getCoinsIn().size() == 0) {
				isTied  = true;
			}
		}
		return isTied;
	}
	
	@Override
	public String toString() {
		return "Player ID: " + this.playerId
				+ " Player Name: " + this.playerName;
	}
}
