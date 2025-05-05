package controller;

import model.*;
import view.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import main.Main;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller class to manage interactions between the Shelter model and MainView.
 */
public class PetController {
    private Shelter<Pet> shelter;
    private MainView view;
    private String lastSavedFile = null; // Track the last saved file

    public PetController(Shelter<Pet> shelter, MainView view) {
        this.shelter = shelter;
        this.view = view;
        initController();
    }

    /**
     * Initializes action listeners for the GUI buttons.
     */
    private void initController() {
        view.getAddPetButton().addActionListener(e -> handleAddPet());
        view.getAdoptPetButton().addActionListener(e -> handleAdoptPet());
        view.getRemovePetButton().addActionListener(e -> handleRemovePet());
        view.getSortButton().addActionListener(e -> handleSortPets());
        view.getSaveButton().addActionListener(e -> handleSavePets());
        view.getLoadButton().addActionListener(e -> handleLoadPets());
    }

    /**
     * Adds a new pet to the shelter and refreshes the view.
     * @param pet The pet to add.
     */
    public void addPet(Pet pet) {
        if (pet != null) {
            if (shelter.getPetById(pet.getId()) != null) {
                JOptionPane.showMessageDialog(view, "Error: A pet with ID " + pet.getId() + " already exists.");
                return;
            }
            shelter.addAnimal(pet);
            view.refreshPetList(shelter.getPets());
        } else {
            JOptionPane.showMessageDialog(view, "Error: Invalid pet data.");
        }
    }

    /**
     * Adopts a pet by ID and updates the view.
     * @param petId The ID of the pet to adopt.
     */
    public void adoptPet(int petId) {
        if (shelter.adoptPet(petId)) {
            view.refreshPetList(shelter.getPets());
            JOptionPane.showMessageDialog(view, "Pet with ID " + petId + " has been adopted.");
        } else {
            JOptionPane.showMessageDialog(view, "Pet already adopted or not found.");
        }
    }

    /**
     * Removes a pet by ID and updates the view.
     * @param petId The ID of the pet to remove.
     */
    public void removePet(int petId) {
        Pet pet = shelter.getPetById(petId);
        if (pet != null) {
            shelter.getPets().remove(pet);
            view.refreshPetList(shelter.getPets());
            JOptionPane.showMessageDialog(view, "Pet with ID " + petId + " has been removed.");
        } else {
            JOptionPane.showMessageDialog(view, "Error: Pet not found.");
        }
    }

    /**
     * Displays details of a pet by ID.
     * @param petId The ID of the pet to view.
     */
    public void viewPetDetails(int petId) {
        Pet pet = shelter.getPetById(petId);
        if (pet != null) {
            view.displayPetDetails(pet);
        } else {
            JOptionPane.showMessageDialog(view, "Error: Pet not found.");
        }
    }

    /**
     * Saves the current list of pets to a timestamped JSON file.
     */
    public void savePetsToFile() {
        Gson gson = new Gson();
        String timestamp = new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        String filename = timestamp + " pets.json";
        try (FileWriter writer = new FileWriter("src/main/resources/" + filename)) {
            gson.toJson(shelter.getPets(), writer);
            lastSavedFile = filename; // Store the last saved filename
            JOptionPane.showMessageDialog(view, "Pets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error saving pets to file.");
        }
    }

    /**
     * Sorts pets based on the given criteria and updates the view.
     * @param criteria The sorting criteria ("name", "age", or "species").
     */
    public void sortPets(String criteria) {
        switch (criteria.toLowerCase()) {
            case "name":
                shelter.getPets().sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
                break;
            case "age":
                shelter.getPets().sort((p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));
                break;
            case "species":
                shelter.getPets().sort((p1, p2) -> p1.getSpecies().compareToIgnoreCase(p2.getSpecies()));
                break;
            default:
                JOptionPane.showMessageDialog(view, "Error: Invalid sort criteria.");
                return;
        }
        view.refreshPetList(shelter.getPets());
    }

    /**
     * Loads pets from JSON files and updates the view.
     */
    public void loadPetsFromFile() {
        shelter.clearShelter();
        // Load from the last saved file if it exists, otherwise use the original files
        if (lastSavedFile != null) {
            Gson gson = new Gson();
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/" + lastSavedFile))) {
                List<Pet> savedPets = gson.fromJson(reader, new TypeToken<List<Pet>>(){}.getType());
                if (savedPets != null) {
                    savedPets.forEach(shelter::addAnimal);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error loading from saved file.");
            }
        } else {
            // Load from original files if no saved file exists
            List<Pet> regPets = Main.readRegPets("src/main/resources/animals/pets.json");
            if (regPets != null) {
                regPets.forEach(shelter::addAnimal);
            }
            List<ExoticAnimal> exoticPets = Main.readExoticPets("src/main/resources/animals/exotic_animals.json");
            if (exoticPets != null) {
                exoticPets.forEach(pet -> {
                    ExoticAnimalAdapter wrappedExoticPet = new ExoticAnimalAdapter(pet);
                    wrappedExoticPet.sync();
                    shelter.addAnimal(wrappedExoticPet);
                });
            }
        }
        view.refreshPetList(shelter.getPets());
        JOptionPane.showMessageDialog(view, "Pets loaded from files.");
    }

    /**
     * Handles add pet button from view to controller
     */
    private void handleAddPet() {
        Pet pet = view.getPetFromForm();
        addPet(pet);
    }

    /**
     * Handles adopt pet button from view to controller
     */
    private void handleAdoptPet() {
        int petId = view.getPetIdForAdopt();
        if (petId != -1) adoptPet(petId);
    }

    /**
     * Handles remove pet button from view to controller
     */
    private void handleRemovePet() {
        int petId = view.getPetIdForRemoval();
        if (petId != -1) removePet(petId);
    }

    /**
     * Handles sorting pets from view to controller
     */
    private void handleSortPets() {
        String criteria = view.getSortCriteria();
        sortPets(criteria);
    }

    /**
     * Save pets to file button from view to controller
     */
    private void handleSavePets() {
        savePetsToFile();
    }

    /**
     * Load pets from file
     */
    private void handleLoadPets() {
        loadPetsFromFile();
    }
}