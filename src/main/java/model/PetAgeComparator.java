package model;

import java.util.Comparator;

public class PetAgeComparator implements Comparator<Pet> {
    
	/**
	 * Compares age of pet 1 to pet 2
	 */
	@Override
    public int compare(Pet p1, Pet p2) {
        return Integer.compare(p1.getAge(), p2.getAge());
    }
}
