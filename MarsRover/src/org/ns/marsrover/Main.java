package org.ns.marsrover;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	/**
	 * @param args
	 */
	int rover_count;
	static Point rover_pos;
	static ArrayList<Rover> rovers;
	static ArrayList<String> movements;
	static Plateau plateau;
	static File input_file;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("##Mars-Rovers##");
		if (args.length > 0)
		{
			if (args[0] != null)
			{
				if (new File(args[0]).exists())
				{
					input_file = new File(args[0]);
					System.out.println("Input File Found at: "+ input_file.getAbsolutePath());
				}
			}
		}
		else
		{
			input_file = new File("input.txt");
			input_file.createNewFile();
			System.out.println("Created sample input file at" + input_file.getAbsolutePath());
			FileWriter fout = new FileWriter(input_file);
			BufferedWriter bfout = new BufferedWriter(fout);
			bfout.write("5 5\n");
			bfout.write("1 2 N\n");
			bfout.write("LMLMLMLMM\n");
			bfout.write("3 3 E\n");
			bfout.write("MMRMMRMRRM\n");
			bfout.close();
			fout.close();
		}
		
		FileReader fread = new FileReader(input_file);
		BufferedReader bread = new BufferedReader(fread);
		
		String input_line;
		input_line = bread.readLine();
		int posx = Integer.parseInt(""+input_line.charAt(0));
		int posy = Integer.parseInt(""+input_line.charAt(2));
		char facing = '0';
		Facing faceing;
		plateau = new Plateau(new Point(posx,posy));
		movements = new ArrayList<String>();
		rovers = new ArrayList<Rover>();
		
		while (bread.ready())
		{
			input_line = bread.readLine();
			posx = Integer.parseInt(""+input_line.charAt(0));
			posy = Integer.parseInt(""+input_line.charAt(2));
			rover_pos = new Point (posx, posy);
			facing = input_line.charAt(4);
			switch (facing)
			{
			case 'N': faceing = Facing.NORTH; break;
			case 'E': faceing = Facing.EAST; break;
			case 'W': faceing = Facing.SOUTH; break;
			case 'S': faceing = Facing.WEST; break;
			default : System.out.println ("Illegal direction code."); bread.readLine(); continue;
			}
			input_line = bread.readLine();
			
			movements.add (input_line);
			rovers.add(new Rover (plateau, faceing, rover_pos));
		}
		String movement_i;
		Rover rover_i;
		for (int i=0; i<rovers.size(); ++i)
		{
			movement_i = movements.get(i);
			rover_i = rovers.get(i);
			for (int j=0; j<movement_i.length(); ++j)
				rover_i.roverMove(movement_i.charAt(j));
			rovers.set(i, rover_i);
		}
		System.out.println("Final rover positions: ");
		for (int i=0; i<rovers.size(); ++i)
		{
			rover_i = rovers.get(i);
			rover_pos = rover_i.getRoverPos();
			faceing = rover_i.getRoverFacing();
			switch (faceing)
			{
			case NORTH: facing = 'N'; break;
			case EAST: facing = 'E'; break;
			case WEST: facing = 'W'; break;
			case SOUTH: facing = 'S';
			}
			System.out.println(rover_pos.x + " " + rover_pos.y + " " + facing);
		}
		/*rovers = new ArrayList<Rover>();
		movements = new ArrayList<String>();
		String movement_i;
		Rover rover_i;
		rovers.add(new Rover(new Plateau(new Point(5, 5)),Facing.NORTH,new Point(1, 2)));
		movements.add("LMLMLMLMM");
		for (int i=0; i<rovers.size(); ++i)
		{
			movement_i = movements.get(i);
			rover_i = rovers.get(i);
			for (int j=0; j<movement_i.length(); ++j)
				rover_i.roverMove(movement_i.charAt(j));
			rovers.set(i, rover_i);
		}
		for (int i=0; i<rovers.size(); ++i)
		{
			rover_i = rovers.get(i);
			rover_pos = rover_i.getRoverPos();
			Facing faceing = rover_i.getRoverFacing();
			char facing = '0';
			switch (faceing)
			{
			case NORTH: facing = 'N'; break;
			case EAST: facing = 'E'; break;
			case WEST: facing = 'W'; break;
			case SOUTH: facing = 'S';
			}
			System.out.println(rover_pos.x + " " + rover_pos.y + " " + facing);
		}*/
		//System.out.print(new Compass().getNewFacing(Facing.NORTH, Rotation.LEFT));
	}

}
