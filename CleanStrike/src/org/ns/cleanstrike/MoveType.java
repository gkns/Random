package org.ns.cleanstrike;

public enum MoveType {
	MOVE_STRIKE (1, "Strike"),
	MOVE_MULTISTRIKE(2, "Multistrike"),
	MOVE_RED_STRIKE(3, "Red strike"),
	MOVE_STRIKER_STRIKE(4, "Striker strike"),
	MOVE_DEFUNCT_STRIKE(5, "Defunct coin"),
	MOVE_NONE(6, "None");
	
	private final int moveId;
	private final String moveName;
	
	MoveType(int moveId_, String moveName_) {
		this.moveId = moveId_;
		this.moveName = moveName_;
	}
	
	@Override
	public String toString() {
		return moveName;
	}
}
