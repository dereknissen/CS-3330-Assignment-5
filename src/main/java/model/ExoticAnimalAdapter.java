package model;

public class ExoticAnimalAdapter extends Pet {
	
	
	public ExoticAnimalAdapter(ExoticAnimal animal) {
		
		this.setName(animal.getAnimalName());
		this.setSpecies(animal.getSubSpecies());
		this.setType(animal.getCategory());
		this.setAge(animal.getYearsOld());
		this.setAdopted(false);
		
	}
}
