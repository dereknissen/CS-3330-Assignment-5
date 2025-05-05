package model;

/**
 * Adapter class to convert an ExoticAnimal into a Pet for use in the shelter.
 */
public class ExoticAnimalAdapter extends Pet {
	
	private ExoticAnimal animal = null;
	private Shelter<Pet> shelter;
	
	/**
	 * Constructs an ExoticAnimalAdapter with a reference to the shelter.
	 * @param animal The ExoticAnimal to adapt.
	 * @param shelter The shelter to check for ID conflicts.
	 */
	public ExoticAnimalAdapter(ExoticAnimal animal, Shelter<Pet> shelter) {
		this.animal = animal;
		this.shelter = shelter;
	}
	
	/**
	 * This method will sync the Pet parent class to the attributes inside of the exotic animal.
	 */
	public void sync() {
		this.setAge(animal.getYearsOld());
		this.setAdopted(false);
		this.setName(animal.getAnimalName());
		this.setSpecies(animal.getSubSpecies());
		this.setType(animal.getCategory());
		
		// Extract numeric ID from uniqueId
		String temp = "";
		for (int i = 0; i < animal.getUniqueId().length(); i++) {
			if (Character.isDigit(animal.getUniqueId().charAt(i))) {
				temp += animal.getUniqueId().charAt(i);
			}
		}
		int idNumber = Integer.parseInt(temp);
		
		// Check if the ID already exists in the shelter
		while (shelter.getPetById(idNumber) != null) {
			// Find the maximum ID in the shelter and increment
			int maxId = 0;
			for (Pet pet : shelter.getPets()) {
				if (pet.getId() > maxId) {
					maxId = pet.getId();
				}
			}
			idNumber = maxId + 1;
		}
		
		this.setId(idNumber);
	}
}