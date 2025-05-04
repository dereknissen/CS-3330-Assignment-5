package model;

import java.util.Comparator;

public class PetSpeciesComparator implements Comparator<Pet> {
    @Override
    public int compare(Pet p1, Pet p2) {
        return p1.getSpecies().compareToIgnoreCase(p2.getSpecies());
    }
}
