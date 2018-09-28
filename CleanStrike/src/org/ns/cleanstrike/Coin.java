package org.ns.cleanstrike;

public class Coin {
	private int coinId;
	private CoinType coinType;
	private int point;

	Coin (int coinId_, CoinType coinType_, int point_) {
		coinId = coinId_;
		coinType = coinType_;
		this.point = point_;
	}
	
	public CoinType getCoinType() {
		return this.coinType;
	}
	
	@Override
	public String toString() {
		return  "Coin ID: " + this.coinId + 
				", Coin Type: " + this.coinType.toString() + 
				", Points: " + this.point;
	}
}
