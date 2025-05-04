package main;

import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Shelter petShelter = new Shelter();
		
		// Add regular pets to pet shelter
		List<Pet> regPets = readRegPets("src/main/resources/animals/pets.json");
		for (Pet pet : regPets) {
			
			// Add animal
			petShelter.addAnimal(pet);
		}
		
		// Add exotic pets to pet shelter
		List<ExoticAnimal> exoticPets = readExoticPets("src/main/resources/animals/exotic_animals.json");
		for (ExoticAnimal pet : exoticPets) {
			
			// Use adapter to convert to pet
			ExoticAnimalAdapter wrappedExoticPet = new ExoticAnimalAdapter(pet);
			wrappedExoticPet.sync();
			
			// Add animal
			petShelter.addAnimal(wrappedExoticPet);
		}
		
		System.out.println(petShelter.toString());
		
	}
	
	/**
	 * Uses gson to read a CSV file with pets. Returns a list of Pet objects.
	 * 
	 * @param filepath
	 * @return
	 */
	private static List<Pet> readRegPets(String filepath) {
		Gson gson = new Gson();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line;
			String petData="";
	        while ((line = br.readLine()) != null) { // while the next line exists
	            petData = petData + line; // add the line to the string
	        }
	        br.close();
	        
	        java.lang.reflect.Type listType = new TypeToken<ArrayList<Pet>>() {}.getType(); // got from ekin's repo
	        List<Pet> pets = gson.fromJson(petData, listType); // json string to list of pets
	        
	        return pets;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Uses gson to read a CSV file consisting of exotic pets. Returns a list of ExoticAnimal objects.
	 * 
	 * @param filepath
	 * @return
	 */
	private static List<ExoticAnimal> readExoticPets(String filepath) {
		Gson gson = new Gson();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line;
			String petData="";
	        while ((line = br.readLine()) != null) { // while the next line exists
	            petData = petData + line; // add the line to the string
	        }
	        br.close();
	        
	        java.lang.reflect.Type listType = new TypeToken<ArrayList<ExoticAnimal>>() {}.getType(); // got from ekin's repo
	        List<ExoticAnimal> pets = gson.fromJson(petData, listType); // json string to list of pets
	        
	        return pets;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
