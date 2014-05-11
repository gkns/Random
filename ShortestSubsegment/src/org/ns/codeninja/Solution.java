package org.ns.codeninja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Solution {

	/**
	 * @param args
	 curr_begin, curr_min_len, input_int, key_int.values());
	 */
	public static int getSubSeqEnd(Integer curr_begin, Integer prev_begin, Integer curr_min_len, ArrayList<Integer> input_int, ArrayList<Integer> key_int)
	{
		//check if minimum no. words required are left in the string to scan
		if ((curr_begin + key_int.size() -1)  > input_int.size())
			return -1;
		//ensure curr_begin starts only from an index more than (prev_begin+curr_min_len) otherwise previous was the minimum subsegment
		if (curr_begin == (prev_begin+curr_min_len-1))
			return -1;
		
		HashMap<Integer, Integer> key_flags = new HashMap<>();
		for (int i=curr_begin; i<input_int.size(); i++)
		{
			if ((i-curr_begin)>=curr_min_len)
				return -1;
			Integer curr_word = input_int.get(i);
			if (key_int.contains(curr_word) && key_flags.get(curr_word) == null)
				key_flags.put(curr_word, 1);
			if (key_flags.size() == key_int.size())
				return i;
		}
		return -1;
	}
	public static void main(String[] args)  {
		String input = "";
		String[] key = null;
		Integer n_keys=0;
		try{
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//input = br.readLine();
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();
		if (input.length() > 200000)
			System.exit(0);
		input = input.replaceAll("[^A-Za-z]+", "+").replaceAll("\\+", " ").trim();
		n_keys = sc.nextInt();//Integer.parseInt(br.readLine());
		
		//preliminary check to see if the worst case no. of keys cannot go more than no. of words in input
		if (n_keys > (input.length()-1))
			System.exit(0);
			
		key = new String[n_keys];
		for (int i=0;i<n_keys; i++)
			key[i] = sc.next();//br.readLine();
		}
		catch (Exception ex)
		{
			System.out.println("Total number of characters in a paragraph should not be more than 200,000.");
			System.exit(0);
		}
		ArrayList<Integer> input_int = new ArrayList<Integer>(); //integer array representation of input
		ArrayList<Integer> select_indices = new ArrayList<Integer>(); //possible start/end indices of the subsegment;
		//map representing key words and their mapped integer values
		HashMap<String, Integer> key_int = new HashMap<String, Integer>();
		
		for (int i=0; i<key.length; i++)
		{
			String curr_key = key[i].toLowerCase();
			curr_key = curr_key.replaceAll("[^a-zA-Z]", "").trim();
			if(!key_int.containsKey(curr_key) && (curr_key.compareTo("") !=0) )
				key_int.put(curr_key, -1);//mark as not present by default
		}
		//Scan input string assign an integer for every word:
		HashMap<String, Integer> wordmap = new HashMap<String, Integer>();
		String curr_word = "";
		Integer k=1;
		String[] input_parts = input.split(" ");
		String input_part_lower=null;
		for (int i=0;i<input_parts.length; i++)
		{
			//Constraint check
			if (input_parts[i].length() >= 15)
				System.exit(0);
			input_parts[i] = input_parts[i].replaceAll("[^a-zA-Z]", "");
			input_part_lower = input_parts[i].toLowerCase();
			if (!wordmap.containsKey(input_part_lower))
				wordmap.put(input_part_lower, k++);
			//Integer array representation of input
			input_int.add(wordmap.get(input_part_lower));
			//update key_int with the integer value of each words if it's not already updated
			if (key_int.containsKey(input_part_lower))
			{
				//Update this as a possible start/end index of the subsegment
				select_indices.add(input_int.size()-1);
				if (key_int.get(input_part_lower) == -1)
				{
					key_int.remove(input_part_lower);
					key_int.put(input_part_lower, wordmap.get(input_part_lower));
				}
				
			}
		}
		//Constraints check
		if (n_keys < 1)
			System.exit(0);
		//Error check if key is not in the Text, no point in proceeding.
		if (key_int.containsValue(-1))
		{
			System.out.println("NO SUBSEGMENT FOUND");
			System.exit(0);
		}
		//Now start at select indices and go till the subsequence is found fully
		Integer min_begin=0, min_end=input_int.size()-1, curr_begin = -1, prev_begin=-1, curr_min_len = Integer.MAX_VALUE, possible_end=-1;
		ArrayList<Integer> key_int_arr  = new ArrayList<>();
		key_int_arr.addAll(key_int.values());
		Iterator it = select_indices.iterator();
		while (it.hasNext())
		{
			curr_begin = (Integer)it.next();
			possible_end = getSubSeqEnd(curr_begin, prev_begin, curr_min_len, input_int, key_int_arr);
			if (possible_end >= 0)
			{
				curr_min_len = possible_end - curr_begin;
				min_begin = curr_begin;
				min_end = possible_end;	
			}
			prev_begin = curr_begin;
		}
		
		int n_words = min_end-min_begin + 1;
		int start_i = 0;
		
		if (min_begin < 0 || min_end >= input_parts.length)
		{
			System.out.println("NO SUBSEGMENT FOUND");
			System.exit(0);
		}
		
		for (int i=min_begin; i<=min_end; i++)
		{
			System.out.print(input_parts[i]);
			if (i != min_end)
				System.out.print(" ");
		}
	}
}
