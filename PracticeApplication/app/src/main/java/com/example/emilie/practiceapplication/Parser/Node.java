package com.example.emilie.practiceapplication.Parser;

import java.io.Serializable;

public class Node implements Serializable {
	private int x;
	private int y;
	
	public Node ()
	{
	  setX(0);
	  setY(0);
	}
	public Node (int x, int y)
	{
		setX(x);
		setY(y);
	}
	public boolean areEqual(Node other)
	{
		boolean answer;
		answer = this.getX() == other.getX();
		answer &= this.getY() == other.getY();
		return answer;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	

}
