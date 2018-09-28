package org.ns.cleanstrike;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
	private ArrayList<Coin> coinsIn;
	Board (List coinsIn_) {
		this.coinsIn = new ArrayList<Coin>();
		this.coinsIn.addAll(coinsIn_);
	}
	
	public List getCoinsIn() {
		return this.coinsIn;
	}
	
	public boolean removeCoin(CoinType coinType) {
		Iterator<Coin> coinIterator = coinsIn.iterator();
		boolean success = false;
		while (coinIterator.hasNext()) {
			Coin currCoin = coinIterator.next();
			//currently only black
			if (currCoin.getCoinType() == coinType) {
				coinIterator.remove();
				success = true;
				break;
			}
		}
		return success;
	}
}
