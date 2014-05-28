package org.ns.galaxy;

import java.util.Arrays;
import java.util.HashMap;

public class InputParser{
	
	private String input;
	private String input_lcase;
	private String parsed_input;
	private String rnumerals;
	private String ignumeral;
	private String igNumeral;
	private String[] temp;
	private String temp_s;
	private String rnumeral;
	private String metal;
	float credit_value;
	
	private static HashMap<String, RomanNumeral> igNumeralMapping;
	private static HashMap<String, Float> metals;
	
	public InputParser() {
		igNumeralMapping = new HashMap<>();
		metals = new HashMap<>();
	}
	public boolean parseInput(String input_)
	{
		input = input_;
		input_lcase = input.toLowerCase();
		rnumerals = Arrays.toString(RNumeralSet.values()).replaceAll("[^A-Z\\[\\]]", "");
		rnumerals = rnumerals.replace("NULL", "");
		
		if (input_lcase.contains("?")) //process as a Query
			processQuery();
		else
			processInput();
		return true;
	}
	
	public boolean processQuery()
	{
		temp = input_lcase.split(" ");
		rnumeral = "";
		metal = null;
		parseRNumeralAndCommodity();
		
		RomanNumeral rNumeral = new RomanNumeral(rnumeral);
		Integer value = rNumeral.toInteger();
		
		if (metal != null && !metals.containsKey(metal.toLowerCase()) && value!=-1)
			System.out.println(ignumeral + "is " + value);
		else if (metal != null && metals.containsKey(metal.toLowerCase()) && value != -1)
			System.out.println(ignumeral + metal + " is " + value*metals.get(metal.toLowerCase()));
		else
			System.out.println("I have no idea what you are talking about");
		
		return true;
	}
	public boolean processInput()
	{
	
		if (input.matches(".* is .*"+rnumerals+"+"))
		{
			 igNumeral = input_lcase.split(" is ")[0].replaceAll("[^a-z]", "");
			 rnumeral = input.split(" is ")[1].replaceAll("[^A-Z]", "");
			 igNumeralMapping.put(igNumeral, new RomanNumeral(rnumeral));
			 return true;
		}
		else if (input_lcase.matches(".*"+"is .* credits"))
		{
			
			temp = input_lcase.split(" ");
			rnumeral = "";
			metal = null;
			parseRNumeralAndCommodity();
			credit_value = Float.parseFloat(input_lcase.replaceAll("[^0-9.]", ""));
			RomanNumeral rNumeral = new RomanNumeral(rnumeral);
			if (metal != null)
				metals.put(metal.toLowerCase().replaceAll("[^a-z]", ""), (credit_value/rNumeral.toInteger()));
			return true;
		}
		else
			return false;
	}
	public boolean parseRNumeralAndCommodity()
	{
		temp = input.split(" ");
		rnumeral = "";
		ignumeral = "";
		metal = null;
		
		boolean flag = false; //indicates whether a recognizeable igNumeral mapping has been seen, used to extract the metal name correctly. 
		for (int i=0; i<temp.length; ++i)
		{
			temp_s = temp[i].replaceAll("[^A-Za-z0-9.]", "");
			if (igNumeralMapping.containsKey(temp_s.toLowerCase()))
			{
				ignumeral += temp_s + " ";
				rnumeral += igNumeralMapping.get(temp_s.toLowerCase()).toString();
				flag = true;
			}
			else if (metal == null && flag == true)
				metal=temp_s.replaceAll("[^a-zA-Z]", "");
		}
		return true;
	}

}
