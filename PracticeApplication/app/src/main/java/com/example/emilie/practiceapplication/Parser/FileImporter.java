package com.example.emilie.practiceapplication.Parser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileImporter {
	public String Schematic;
	
	public String LibraryPath;
	
public FileImporter(String schematic ,String library)
	{
		//File path = new File("C:\\Users\\Mose\\Desktop\\LTSpice\\Schematics\\");
		//String absolutePath = path.getAbsolutePath();
		//Schematic = absolutePath.concat(schematic); // Make the schematic path the current bath
		Schematic = schematic;
		LibraryPath = library; //Hardcoded path to Library
	}


public List<String[]> readSchematic()
{
	List<String[]> records = new ArrayList<String[]>();
  try
  {
    BufferedReader reader = new BufferedReader(new FileReader(Schematic));
    String line;
    while ((line = reader.readLine()) != null)
    {
      String[] component = new String[100];
	  int start = 0;
	  int count = 0;
	  int end;
	  boolean flag = false;

	  //System.out.println(line);
	  while(!flag)
	  {
		  String Cord;
    	  end = line.indexOf(" ", start);
    	  if (end != -1)
    	  {
    		  Cord = line.substring(start, end);
    		  start = end + 1;
    	  }
    	  else // We are at the end of the string
    	  {
    		  Cord = line.substring(start);
    		  flag = true;
    	  }
    	  //System.out.println(Cord);
    	  component[count] = Cord;
    	  count = count + 1; //increment through the array
	  }
		records.add(component);
    }
    reader.close();
    return records;
  } catch (IOException e) {
	  e.printStackTrace();
  }
//  catch (Exception e)
//  {
//    System.err.format("Exception occurred trying to read '%s'.", Schematic);
//    e.printStackTrace();
//    return null;
//  }
	return records;
}

public List<String[]> readLibrary(String componentName)
{
  List<String[]> records = new ArrayList<String[]>();
    componentName = componentName.replace("\\\\","/");
  String componentlib = LibraryPath.concat(componentName).concat(".asy");
  try
  {	
    BufferedReader reader = new BufferedReader(new FileReader(componentlib));
    
    String line;
    while ((line = reader.readLine()) != null)
    {
      String[] component = new String[100];
	  int start = 0;
	  int count = 0;
	  int end;
	  boolean flag = false;
	  
	  //System.out.println(line);
	  while(!flag)
	  {
		  String Cord;
    	  end = line.indexOf(" ", start);
    	  if (end != -1)
    	  {
    		  Cord = line.substring(start, end);
    		  start = end + 1;
    	  }
    	  else // We are at the end of the string
    	  {
    		  Cord = line.substring(start);
    		  flag = true;
    	  }
    	  //System.out.println(Cord);
    	  component[count] = Cord;
    	  count = count + 1; //increment through the array
	  }
	  records.add(component);
    }
    reader.close();
    return records;
  } catch (IOException e) {
	  e.printStackTrace();
  }
	return records;
}
}

