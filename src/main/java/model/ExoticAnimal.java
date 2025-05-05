package model;

public class ExoticAnimal extends Pet {
	
	private String uniqueId;
	private String animalName;
	private String category;
	private String subSpecies;
	private int yearsOld;
	
	/**
	 * Default constructor
	 */
	public ExoticAnimal() {
		super();
	}

	/**
	 * @return pet id
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets pet id
	 * @param uniqueId
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return animal name
	 */
	public String getAnimalName() {
		return animalName;
	}

	/**
	 * Sets animal name
	 * @param animalName
	 */
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	/**
	 * @return pet category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets pet category
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return pet subspecies
	 */
	public String getSubSpecies() {
		return subSpecies;
	}

	/**
	 * Set pet subspecies
	 * @param subSpecies
	 */
	public void setSubSpecies(String subSpecies) {
		this.subSpecies = subSpecies;
	}

	/**
	 * @return pet age in years
	 */
	public int getYearsOld() {
		return yearsOld;
	}

	/**
	 * Set pet age in years
	 * @param yearsOld
	 */
	public void setYearsOld(int yearsOld) {
		this.yearsOld = yearsOld;
	}
	
	/**
	 * Converts pet data to a string
	 */
	public String toString() {
		return super.toString();
	}
}
