package org.ns.cleanstrike.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.ns.cleanstrike.GamePlay;

class TestGamePlay {
	private static GamePlay game;

	@Before
	void setup() {
		game = new GamePlay();
		game.init();
	}
	
	@Test
	void testNineBlackOneRedAndStrickerAreThere() {
		
	}

}
