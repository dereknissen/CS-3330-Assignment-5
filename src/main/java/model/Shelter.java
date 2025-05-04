package model;

import java.util.ArrayList;

/**
 * A generic class representing a shelter for pets.
 * @param <T> The type of pet, must extend the Pet class.
 */
public class Shelter<T extends Pet> {

    private ArrayList<T> animals = new ArrayList<>();

    /**
     * Adds a pet to the shelter.
     * @param pet The pet to add to the shelter.
     */
    public void addAnimal(T pet) {
        animals.add(pet);
    }

    /**
     * Retrieves a pet by its ID.
     * @param id The ID of the pet to find.
     * @return The pet with the specified ID, or null if not found.
     */
    public T getPetById(int id) {
        for (T pet : animals) {
            if (pet.getId() == id) {
                return pet; // Return the pet if found
            }
        }
        return null; // Return null if pet not found
    }

    /**
     * Updates the adoption status of a pet by its ID.
     * @param id The ID of the pet to adopt.
     * @return True if the pet was found and adopted, false otherwise.
     */
    public boolean adoptPet(int id) {
        T pet = getPetById(id); // Retrieve pet by ID
        if (pet != null) {
            pet.setAdopted(true); // Mark pet as adopted
            System.out.println("Pet adopted: " + pet.getName());
            return true;
        } else {
            System.out.println("Pet with ID " + id + " not found.");
            return false;
        }
    }

    /**
     * Gets the list of all pets in the shelter.
     * @return The list of pets.
     */
    public ArrayList<T> getPets() {
        return animals;
    }

    /**
     * Clears all pets from the shelter.
     */
    public void clearShelter() {
        animals.clear();
    }

    /**
     * Returns a string representation of the shelter's contents.
     * @return A string representation of the list of pets.
     */
    @Override
    public String toString() {
        return animals.toString();
    }
}