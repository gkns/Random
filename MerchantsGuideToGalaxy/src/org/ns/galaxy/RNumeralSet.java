package org.ns.galaxy;

public enum RNumeralSet {
	//(Character, Value,	isRepeatable, isSubtractible)
	I ('I', 1, 		true,	true),
	V ('V', 5, 		false,	false),
	X ('X', 10, 	true,	true),
	L ('L', 50, 	false, 	false),
	C ('C', 100, 	true, 	true),
	D ('D', 500,	false, 	false),
	M ('M', 1000, 	true, 	true),
	
	NULL ('Z', 0,		false,	false);
	
	private final Character numeral;
	private final Integer value;
	private final boolean isRepeatable;
	private final boolean isSubtractable;

	private RNumeralSet(Character numeral_, Integer value_,
			boolean isRepeatable_, boolean isSubtractable_) {
		this.numeral = numeral_;
		this.value = value_;
		this.isRepeatable = isRepeatable_;
		this.isSubtractable = isSubtractable_;
		
	}

	public Character numeral() {
		return this.numeral;
	}

	public Integer value() {
		return this.value;
	}

	public boolean isRepatable() {
		return this.isRepeatable;
	}

	public boolean isSubtractable() {
		return this.isSubtractable;
	}
	
	public static RNumeralSet valueOf(Character character) {
		return RNumeralSet.valueOf(String.valueOf(character));
	}

	public static boolean isSubtractibleFrom(RNumeralSet A, RNumeralSet B) {
		if (!B.isSubtractable)
			return false;
		switch (B) {
		case I:
			if (A.numeral == 'V' || A.numeral == 'X')
				return true;
		case X:
			if (A.numeral == 'L' || A.numeral == 'C')
				return true;
		case C:
			if (A.numeral == 'D' || A.numeral == 'M')
				return true;
		}
		return false;
	}

	/*public static void main (String[] args)
	{
		System.out.println(new RomanNumeral("I").toInteger());
	}*/
}