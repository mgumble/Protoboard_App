package com.example.emilie.practiceapplication.Parser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class  Component implements Parcelable{
	public String Name;
    public String type;
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
                 type = line[1];
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

    protected Component(Parcel in) {
        Name = in.readString();
        Value = in.readString();
    }

    public static final Creator<Component> CREATOR = new Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Name);
		dest.writeList(NetList);
		dest.writeList(Terminals);
		dest.writeString(Value);
	}
}
