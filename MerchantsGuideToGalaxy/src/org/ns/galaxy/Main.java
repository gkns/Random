package org.ns.galaxy;

public class Main {

public static void main(String[] args) {
		
		//TestInputs
		
		InputParser input = new InputParser();
		input.parseInput("glob is I");
		input.parseInput("prok is V");
		input.parseInput("pish is X");
		input.parseInput("tegj is L");
		input.parseInput("glob glob Silver is 34 Credits");
		input.parseInput("glob prok Gold is 57800 Credits");
		input.parseInput("pish pish Iron is 3910 Credits");
		
		input.parseInput("how much is pish tegj glob glob ?");
		input.parseInput("how many Credits is glob prok Silver?");
		input.parseInput("how many Credits is glob prok Gold ?");
		input.parseInput("how many Credits is glob prok Iron ?");
		input.parseInput("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
		
//		System.out.println(igNumeralMapping.toString());
//		System.out.println(metals.toString());
	}
}
