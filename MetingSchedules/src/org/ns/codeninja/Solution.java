package org.ns.codeninja;

//This program can be solved using another space efficient way as well.
//But this required to use sorting. 
//The below version is a constant space and constant linear time algorithm

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//Representing each time begining/end as minutes, there are 60*24=1440 such points.
		//Can be made more space efficient by using bit fields, byte[180], avoiding here for better readability
		int[] timeline = new int[1440]; //default values will be 0
		
		
		//to hold the possible meeting schedule start, Stores indices pointing to timeline
		//not being used currently
		//ArrayList<Integer> possible_sched_start = new ArrayList<>();
		//possible_sched_start.add(0); //just to make sure free time search starts from the beginning
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		String[] input_parts = input.split(" ");
		Integer start=-1, end=0;
		
		Integer n_busy_slots = Integer.parseInt(input_parts[0]);
		Integer meeting_dur = Integer.parseInt(input_parts[1]);
		
		//Constraint check;
		if (n_busy_slots > 100)
			System.exit(0);
		//Scan busy slots and mark in the timeline 
		for (int i=0; i<n_busy_slots; i++)
		{
			input = br.readLine();
			input_parts = input.split(" ");
			start = Integer.parseInt(input_parts[0]) * 60 + Integer.parseInt(input_parts[1]);
			end = Integer.parseInt(input_parts[2]) * 60 + Integer.parseInt(input_parts[3]);
			//System.out.println(start + " " + end);
			for (int j=start; j<end; j++)
				timeline[j] = 1; //mark as busy
			//possible_sched_start.add(end); //it can start from the ending time since all schedules are interval:  [start,end)
		}
		start=0;
		int free_count=0;
		boolean flag=false;
		for (int i=0; i<timeline.length; i++)
		{
			if (timeline[i] == 0)
			{
				free_count++;
				if (free_count == meeting_dur)
				{
					System.out.print(String.format("%02d", start/60) + " " + String.format("%02d", start%60));
					flag=!flag;
				}
				if (i == (timeline.length-1))
				{
					i=0;
					if (flag)
					{
						System.out.println(" " +  String.format("%02d", i/60) + " " + String.format("%02d", i%60));
						flag=!flag;
					}
				break;
				}
			}
			else
			{
				if ((i-start) >= 1 && (i-start >= meeting_dur))
				{
					System.out.println(" " +  String.format("%02d", i/60) + " " + String.format("%02d", i%60));
					flag=!flag;
				}
				start=i+1;
				free_count=0;
			}
		}
	}

}
