package model;

import java.util.ArrayList;

public class Shelter <t extends Pet> {
	
	ArrayList<t> animals = new ArrayList<t>();
	
	public void addAnimal(t pet) {
		animals.add(pet);
	}
	
	public String toString() {
		return animals.toString();
	}
	
}
