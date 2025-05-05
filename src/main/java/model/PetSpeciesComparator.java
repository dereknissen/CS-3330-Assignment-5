package model;

import java.util.Comparator;

public class PetSpeciesComparator implements Comparator<Pet> {
    
	/**
	 * Compare species of pet 1 to pet 2
	 */
	@Override
    public int compare(Pet p1, Pet p2) {
        return p1.getSpecies().compareToIgnoreCase(p2.getSpecies());
    }
}
