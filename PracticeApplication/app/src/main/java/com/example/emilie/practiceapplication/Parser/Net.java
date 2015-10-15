package com.example.emilie.practiceapplication.Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Net {
	public int IntName;

	public List<Terminal> terminalList;
	public List<Net> NetList;
	
	public Net (Terminal terminal, List<Net> netlist)
	{
		
		NetList = netlist;
		this.terminalList = new ArrayList<Terminal>();
		terminalList.add(terminal);
		NetList.add(this);
		IntName = NetList.size();
		
	}
	
	
	public void updateNetList(Terminal terminalAdd) {
	
		Iterator<Net>  netListIterator = NetList.iterator();
		if(netListIterator.hasNext()) // Checks to see if there are nets
		{
			while(netListIterator.hasNext())
			{
				Net currentNet = netListIterator.next();
				Iterator<Terminal> currentNetTerminal = currentNet.terminalList.iterator(); // Get a Nets terminals and create an iterator
				if (currentNetTerminal.hasNext())
				{
					while (currentNetTerminal.hasNext())
					{
						Terminal currentTerminal = currentNetTerminal.next();
						//True if they are not the in the Same net and they have equivalent Terminals or 
						if ((!(currentTerminal.CurrentNet == terminalAdd.CurrentNet) &&(currentTerminal.myNode.areEqual(terminalAdd.myNode))) 
							 || (currentTerminal.CurrentNet != terminalAdd.CurrentNet && terminalAdd.getName().equals("Wire") && currentTerminal.getParent().equals(terminalAdd.getParent()))) //There is a matching terminal
						{ 
							Iterator<Terminal> OldNetIterator = terminalAdd.CurrentNet.terminalList.iterator();
							Net temp = terminalAdd.CurrentNet;
							while(OldNetIterator.hasNext())
							{
								Terminal tempTerminal = OldNetIterator.next();
								currentNet.terminalList.add(tempTerminal);
								tempTerminal.CurrentNet = currentNet;
								NetList.remove(temp);
								return;
							}
						}
					}
				}
				else // A Net on Netlist is empty
				{
					NetList.remove(currentNet);
					System.err.format("Net on List was Empty removing");
				}
			} // end while
		}
		else 
		{
			System.err.format("Something Bad happened when updating Net List\n");
		}
		
	}
}
