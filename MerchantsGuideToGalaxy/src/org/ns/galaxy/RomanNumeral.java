package org.ns.galaxy;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RomanNumeral {
	private String rnumeral;
	private Integer value;
	
	public RomanNumeral(String rnumeral_) {
		rnumeral = rnumeral_.toUpperCase().replaceAll("[^A-Z]", ""); //Anything except A-Z will be discarded.
		value = -1;
	}
	@Override
	public String toString() {
		return rnumeral;
	}
	public boolean checkRepetitions() {
		Character character;
		String str;
		RNumeralSet[] values = RNumeralSet.values();
		for (int i=0; i < values.length; i++)
		{
			character = values[i].numeral();
			str = String.valueOf(character);
			if (Pattern.matches(".*"+str+"{2,}", rnumeral)) //a{2,} ==> 2 or more successive appearance of 'a'
			{
				if (RNumeralSet.valueOf(str).isRepatable())
				{
					if (Pattern.matches(".*"+str+"{4,}", rnumeral))//more than 3 successive occurences are invalid
							return false;
				}
				else
					return false;
			}
		}
		return true;
	}
	
	public boolean checkSubtractionRules() {
		RNumeralSet rNumeral1, rNumeral2, rNumeral3;
		for (int i=2; i<rnumeral.length(); ++i)
		{
			rNumeral1 = RNumeralSet.valueOf(rnumeral.charAt(i-1));
			rNumeral2 = RNumeralSet.valueOf(rnumeral.charAt(i));
			if (rNumeral2.value() > rNumeral1.value())
				return RNumeralSet.isSubtractibleFrom(rNumeral2, rNumeral1);
				
		}
		return true;
	}

	public int toInteger() {
		if (!(checkRepetitions() && checkSubtractionRules()))
			return -1; // OR throw new NumberFormatException("Invalid input");
		int value = 0;
		RNumeralSet lastNumber = RNumeralSet.NULL;

		for (int i = rnumeral.length() - 1; i >= 0; i--) {
			RNumeralSet rNumeral = RNumeralSet.valueOf(rnumeral.charAt(i));

			switch (rNumeral) {
			case M:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;

			case D:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;

			case C:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;

			case L:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;

			case X:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;

			case V:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;

			case I:
				value = convertToInt(rNumeral, lastNumber, value);
				lastNumber = rNumeral;
				break;
			}
		}
		return value;
	}

	public static int convertToInt(RNumeralSet rNumeral, RNumeralSet lastNumber, int lastValue) {
		if (lastNumber.value() > rNumeral.value()) {
				return lastValue - rNumeral.value();
		} else {
			return lastValue + rNumeral.value();
		}
	}
	/*public static void main(String[] args)
	{
		System.out.println(new RomanNumeral("XIV").toInteger());
	}*/
}