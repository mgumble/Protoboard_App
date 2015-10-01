import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Component {
	public String Name;
	public List<Net> NetList;
	private List<Terminal> Terminals;
	private String Value;
	
	public Component()
	{
		Name="";
		setValue("");
	}
	public Component(List<String[]> ComponentInfo, FileImporter file, List<Net> netlist)
	{
		NetList = netlist;
		Iterator<String[]> ComponentInfoIterator = ComponentInfo.iterator();
		while(ComponentInfoIterator.hasNext())
		{
			 String[] line = ComponentInfoIterator.next();
			 if(line[0].equals("SYMBOL"))
			 {
				 setTerminals(file.readLibrary(line[1]), line);
			 }
			 else
			 {
				 if(line[1].equals("InstName"))
				 {
					 Name = line[2];
				 }
				 else if(line[1].equals("Value"))
				 {
					 Value = line[2];
				 }
			 }
		}			
	}
	public void setTerminals(List<String[]> Componentlibrary, String[] Component)
	{

		Iterator<String[]> ComponentLibIterator = Componentlibrary.iterator();
		List<Terminal> TempTerminals = new ArrayList<Terminal>();
		int x=0,y=0;
		String pinName="";
		boolean flag = false;
		while(ComponentLibIterator.hasNext())
		{
			String[] line = ComponentLibIterator.next();
			if(line[0].equals("PIN"))
			{
				/* Add the location of the component from the schematic to the offset value from the library 
				 * to find the X and Y location of each terminal
				 */
				x = Integer.parseInt(line[1]) + Integer.parseInt(Component[2]); 
				y = Integer.parseInt(line[2]) + Integer.parseInt(Component[3]);
			}
			if(line[0].equals("PINATTR"))
			{
				if(line[1].equals("PinName"))
				{
					pinName = line[2];
					flag = true;
				}
			}
			if (flag == true){
			Terminal tempTerminal = new Terminal(x,y,this, pinName);
			TempTerminals.add(tempTerminal);	
			flag = false;
			}
		}
		setTerminals(TempTerminals);
		
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public List<Terminal> getTerminals() {
		return Terminals;
	}
	public void setTerminals(List<Terminal> terminals) {
		Terminals = terminals;
	}
}
