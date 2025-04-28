package model;

import java.util.ArrayList;

public class Shelter <t extends Pet> {
	
	ArrayList<Pet> animals = new ArrayList<Pet>();
	
	public void addAnimal(t pet) {
		animals.add(pet);
	}
	
	
}
