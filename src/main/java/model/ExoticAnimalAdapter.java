package model;

public class ExoticAnimalAdapter extends Pet {
	
	private ExoticAnimal animal = null;
	
	public ExoticAnimalAdapter(ExoticAnimal animal) {
		this.animal = animal;
	}
	
	/**
	 * This method will sync the Pet parent class to the attributes inside of the exotic animal
	 */
	public void sync() {
		this.setAge(animal.getYearsOld());
		this.setAdopted(false);
		this.setName(animal.getAnimalName());
		this.setSpecies(animal.getSubSpecies());
		this.setType(animal.getCategory());
		
		// Set weird unique id to an integer
		String temp = "";
	    for (int i = 0; i < animal.getUniqueId().length(); i++) {
	        if (Character.isDigit(animal.getUniqueId().charAt(i))) {
	            temp += animal.getUniqueId().charAt(i);
	        }
	    }
	    int idNumber = Integer.parseInt(temp);
	    
	    this.setId(idNumber);
	}
}
