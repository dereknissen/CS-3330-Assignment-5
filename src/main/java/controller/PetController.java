package controller;

import model.Pet;
import model.Shelter;
import view.MainView;

import java.util.List;

public class PetController {
    private Shelter<Pet> shelter;
    private MainView view;

    public PetController(Shelter<Pet> shelter, MainView view) {
        this.shelter = shelter;
        this.view = view;
        initController();
    }

    private void initController() {
        // Attach GUI event handlers when GUI is ready
    }

    private void addPet(Pet pet) {
        if(pet != null) {
        	shelter.addAnimal(pet);
        }
        else {
        	System.out.println("Error: No pet found");
        }
    }

    private void adoptPet() {

    }

    private void removePet() {

    }
    
    private void viewPetDetails() {

    }

    private void savePetsToFile() {
        // Save current pet list to JSON using Gson
    }
    
    private void sortPets(String criteria) {
        // Use Comparators to sort based on criteria (name, age, species)
    }

    private void loadPetsFromFile() {
        // Load pets and exotic pets from JSON
    }

    
}
