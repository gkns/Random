package org.ns.galaxy;

import static org.junit.Assert.*;

import org.junit.Test;

public class RomanNumeralTest {

	@Test
	public void testCheckRepetitions() {
		assertEquals(true, new RomanNumeral("I").checkRepetitions());
		assertEquals(false, new RomanNumeral("IIII").checkRepetitions());
		assertEquals(false, new RomanNumeral("VV").checkRepetitions());
	}

	@Test
	public void testCheckSubtractionRules() {
		//fail("Not yet implemented");
	}

	@Test
	public void testToInteger() {
		assertEquals(1, new RomanNumeral("I").toInteger());
		assertEquals(2, new RomanNumeral("II").toInteger());
		assertEquals(9, new RomanNumeral("IX").toInteger());
		assertEquals(13, new RomanNumeral("XIII").toInteger());
		assertEquals(14, new RomanNumeral("XIV").toInteger());
		assertEquals(15, new RomanNumeral("XV").toInteger());
		assertEquals(19, new RomanNumeral("XIX").toInteger());
		assertEquals(20, new RomanNumeral("XX").toInteger());
		assertEquals(1900, new RomanNumeral("MCM").toInteger());
		assertEquals(1975, new RomanNumeral("MCMLXXV").toInteger());
		assertEquals(1989, new RomanNumeral("MCMLXXXIX").toInteger());
		assertEquals(1999, new RomanNumeral("MCMXCIX").toInteger());
		assertEquals(2000, new RomanNumeral("MM").toInteger());
		assertEquals(2001, new RomanNumeral("MMI").toInteger());
	}

}
