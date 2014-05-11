package org.ns.marsrover;

import java.awt.Point;

public class Rover {
	static Point move_offset_table[];
	
	Plateau plateau;
	Compass compass;
	
	Point current_pos;
	Point prev_pos;
	Facing current_facing;
	
	public Point getRoverPos ()
	{
		return current_pos;
	}
	
	public Facing getRoverFacing ()
	{
		return current_facing;
	}
	
	Rover (Plateau plateau_, Facing current_facing_, Point current_pos_)
	{
		plateau = plateau_;
		compass = new Compass();
		setCurrent_facing(current_facing_);
		setCurrent_pos(current_pos_);
		
		move_offset_table = new Point[Facing.values().length];
		
		Point temp = new Point(0,1); 
		move_offset_table[Facing.NORTH.ordinal()] = temp;
		
		temp = new Point(1,0);
		move_offset_table[Facing.EAST.ordinal()] = temp;
		
		temp = new Point(0,-1);
		move_offset_table[Facing.SOUTH.ordinal()] = temp;
		
		temp = new Point(-1,0);
		move_offset_table[Facing.WEST.ordinal()] = temp;
	}
	
	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public Point getCurrent_pos() {
		return current_pos;
	}

	public void setCurrent_pos(Point current_pos) {
		this.current_pos = current_pos;
	}

	public Point getPrev_pos() {
		return prev_pos;
	}

	public void setPrev_pos(Point prev_pos) {
		this.prev_pos = prev_pos;
	}

	public Facing getCurrent_facing() {
		return current_facing;
	}

	public void setCurrent_facing(Facing current_facing) {
		this.current_facing = current_facing;
	}

	public void roverMove (char move_code)
	{
		switch (move_code)
		{
		case 'L': roverMove(current_facing, Rotation.LEFT); break;
		case 'R': roverMove(current_facing, Rotation.RIGHT); break;
		case 'M': roverMove(current_facing); break;
		default : System.out.println("Illegal movement code");
		}
	}
	
	private Point getMoveOffset(Facing current_facing_) {
		Point move_offset = move_offset_table[current_facing_.ordinal()];
		return move_offset;
	}
	
	private void roverMove (Facing current_facing_)
	{
		Point move_offset = getMoveOffset(current_facing_);
		setPrev_pos(current_pos);
		current_pos.x += move_offset.x;
		current_pos.y += move_offset.y;
		
		if (!plateau.isValidPos(current_pos))
		{
			current_pos = prev_pos;
			System.out.println("Movement out of bounds detected, Retracting to previous position");
		}
		
		if (!plateau.setRoverPosition(current_pos, prev_pos))
		{
			System.out.println("Rover position collission detected, Retracting to previous position");
		}
		
	}

	private void roverMove (Facing current_facing_, Rotation rotation)
	{
		setCurrent_facing(compass.getNewFacing(current_facing_, rotation));
	}
}
