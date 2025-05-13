package pouilleux.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings {
	final static String fileName = "resources/pouilleux/profile.data";
	
	/**
	 * Method that allow user to change the settings of the application.
	 * @param language
	 * @param displayMode
	 * @throws IOException 
	 */
	public static void updateSettings(int language, int displayMode) throws IOException {
		//Initialization:
		String temp = new String();
		FileWriter file = new FileWriter(fileName);
		
		//Get the values of the parameters and interpret them:
		switch (language) {
			case 0:
				temp ="en";
				break;
			case 1:
				temp = "fr";
				break;
			case 2:
				temp = "de";
				break;
		}
		
		//Build the new profile file:
		file.write(temp+"\n"+displayMode);
		
		//Closing the file:
		file.close();
	}
	
	public static int getDisplayMod() throws IOException {
		//Initialization:
		BufferedReader buffer = new BufferedReader(new FileReader(fileName));
		String tempString = new String();
		int tempInt;
		
		//Read the value of the parameters:
		buffer.readLine(); //The first line is ignored because is for the language.
		tempString = buffer.readLine();
		
		//Interpret the input:
		tempInt = Integer.parseInt(tempString.trim());
		
		//Close the reader:
		buffer.close();
		
		//Verify is the value is correct:
		if (tempInt>=0 && tempInt<=2) {
			return tempInt;
		} else {
			return 0;
		}
	}
	
	public static String getLanguage() throws IOException {
		//Initialization:
		BufferedReader buffer = new BufferedReader(new FileReader(fileName));
		String language = new String();
		
		//Get the first line:
		try {
			language = buffer.readLine();
		} catch (IOException e) {
			language = "en"; //If the software can't read the wanted language it set english by default.
		}
		
		//Closing the buffer:
		buffer.close();
		
		//Returning the language:
		return language;
	}
}
