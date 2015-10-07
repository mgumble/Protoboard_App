package com.example.emilie.practiceapplication;
public class Terminal {
	public Node myNode;    // The location the Terminal is at 
	public Net CurrentNet;   // The Net associated with the Terminal
	private Component Parent; // Looks at the component the terminal is connect too
	private String Name;
	
	
	public Terminal()
	{
		myNode = new Node(0,0);
		setParent(new Component()); // Parent is a blank component
		setName("");
	}
	
	public Terminal(int x, int y , Component component, String name)
	{
		myNode = new Node (x,y);
		setParent(component); 
		setName(name);
		CurrentNet = new Net(this, component.NetList);
		CurrentNet.updateNetList(this);
	}


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Component getParent() {
		return Parent;
	}

	public void setParent(Component parent) {
		Parent = parent;
	}
	
}
