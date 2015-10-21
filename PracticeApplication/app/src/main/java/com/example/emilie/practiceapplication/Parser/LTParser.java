package com.example.emilie.practiceapplication.Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LTParser {
	public String Schematic;
	public String Library;

	public LTParser(String schematic, String library) {
		Schematic = schematic;
		Library = library;
	}

	public ArrayList<Component> test() {
		List<String[]> schematicResources;
		ArrayList<Component> ComponentList = new ArrayList<Component>();
		ArrayList<Net> Netlist = new ArrayList<Net>();
		String[] line;
		
		FileImporter importer = new FileImporter(Schematic,Library);
		schematicResources = importer.readSchematic();  //Reads the schematic file and imports the values into a list of string arrays
            Iterator<String[]> schematicIterator = schematicResources.iterator();
            line = schematicIterator.next();
            while (schematicIterator.hasNext())  // Iterates through the entire schematic file
            {
                if (line[0].equals("SYMBOL")) //indicating that a component is coming
                {
                    List<String[]> Componentinfo = new ArrayList<String[]>();
                    Componentinfo.add(line); // Add the first part of component info

                    line = schematicIterator.next();
                    while (line[0].equals("SYMATTR")) {
                        Componentinfo.add(line);  //Add attributes of that component
                        if (schematicIterator.hasNext()) {
                            line = schematicIterator.next();
                        } else {
                            break;
                        }
                    }// end while loop
                    Component newComponent = new Component(Componentinfo, importer, Netlist); //Create component with the info from schematic
                    ComponentList.add(newComponent); // Add the new component to the Componentlist
                } else if (line[0].equals("WIRE")) {
                    new Wire(line, Netlist);
                    line = schematicIterator.next();
                } else {
                    line = schematicIterator.next();
                }
            }// end while loop
		

		System.out.println("Done");
		return ComponentList;
	}
	public static void printComponentList(List<Component> List)
	{
		Iterator<Component> ListIterator = List.iterator();
		while(ListIterator.hasNext())
		{
			Component tempComponent = ListIterator.next();
			System.out.println("\nComponent: " + tempComponent.Name);
			Iterator<Terminal> TerminalIterator = tempComponent.getTerminals().iterator();
			while(TerminalIterator.hasNext())
			{
				Terminal tempTerminal = TerminalIterator.next();
				System.out.println("\n Terminal: " + tempTerminal.getName() + " is in Net : " + tempTerminal.CurrentNet.IntName);
			}
			
		}
	}


}
