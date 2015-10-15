
package com.example.emilie.practiceapplication.Parser;

import java.util.List;

public class Wire extends Component
{
	public Terminal FirstTerminal;
	public Terminal SecondTerminal;
	//public List<Net> NetList;
	
	public Wire (String[] wireString,List<Net> nets)
	{
		NetList = nets;
		FirstTerminal = new Terminal(Integer.parseInt(wireString[1]),Integer.parseInt(wireString[2]),this,"Wire");
		SecondTerminal = new Terminal(Integer.parseInt(wireString[3]),Integer.parseInt(wireString[4]),this,"Wire");
		FirstTerminal.CurrentNet.updateNetList(FirstTerminal);
	}
}
