package org.ns.cleanstrike;

public enum CoinType {
	COIN_TYPE_BLACK (0, "BLACK", 1),
	COIN_TYPE_WHITE (1, "WHITE", 2),
	COIN_TYPE_RED (2, "RED", 3),
	COIN_TYPE_STRIKER (3, "STRIKER", -1),
	COIN_TYPE_INVALID(4, "INVALID", 0);
	
	private final Integer typeCode;
	private final String coinTypeName;
	private final int point;
	
	CoinType (int typeCode_, String typeName, int point_) {
		this.typeCode = typeCode_;
		this.coinTypeName = typeName;
		this.point = point_;
	}
	
	public int getPoint() {
		return this.point;
	}
}
