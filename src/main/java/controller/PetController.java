package controller;

import model.*;
import view.MainView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import main.Main;

import javax.swing.*;

import java.awt.Dimension;
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
        Pet pet = shelter.getPetById(petId);
        
        if (pet == null) {
            JOptionPane.showMessageDialog(view, 
                "Error: Pet with ID " + petId + " was not found in the shelter.",
                "Pet Not Found",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (pet.isAdopted()) {
            // Special dialog for already adopted pets
            String message = "This pet has already been adopted!\n\n" +
                             "Name: " + pet.getName() + "\n" +
                             "Species: " + pet.getSpecies() + "\n" +
                             "Type: " + pet.getType() + "\n" +
                             "Age: " + pet.getAge() + "\n\n" +
                             "Would you like to see other available pets?";
                             
            int choice = JOptionPane.showConfirmDialog(
                view,
                message,
                "Pet Already Adopted",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                // Show available pets
                showAvailablePets();
            }
            return;
        }
        
        // Pet is available for adoption
        if (shelter.adoptPet(petId)) {
            view.refreshPetList(shelter.getPets());
            JOptionPane.showMessageDialog(view, 
                "Congratulations! You've successfully adopted " + pet.getName() + ".",
                "Adoption Successful",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, 
                "An error occurred during the adoption process.",
                "Adoption Error",
                JOptionPane.ERROR_MESSAGE);
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
        // Create a Gson builder with configuration to handle circular references
        GsonBuilder gsonBuilder = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()  // Only include fields with @Expose annotation
            .setPrettyPrinting();                    // Make the JSON output more readable
        
        Gson gson = gsonBuilder.create();
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timestamp + "_pets.json";
        
        try (FileWriter writer = new FileWriter("src/main/resources/" + filename)) {
            gson.toJson(shelter.getPets(), writer);
            lastSavedFile = filename; // Store the last saved filename
            JOptionPane.showMessageDialog(view, "Pets saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error saving pets to file: " + e.getMessage());
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
                    ExoticAnimalAdapter wrappedExoticPet = new ExoticAnimalAdapter(pet, shelter);
                    wrappedExoticPet.sync();
                    shelter.addAnimal(wrappedExoticPet);
                });
            }
        }
        view.refreshPetList(shelter.getPets());
        JOptionPane.showMessageDialog(view, "Pets loaded from files.");
    }

    private void handleAddPet() {
        Pet pet = view.getPetFromForm();
        addPet(pet);
    }

    private void handleAdoptPet() {
        int petId = view.getPetIdForAdopt();
        if (petId != -1) adoptPet(petId);
    }

    private void handleRemovePet() {
        int petId = view.getPetIdForRemoval();
        if (petId != -1) removePet(petId);
    }

    private void handleSortPets() {
        String criteria = view.getSortCriteria();
        sortPets(criteria);
    }

    private void handleSavePets() {
        savePetsToFile();
    }

    private void handleLoadPets() {
        loadPetsFromFile();
    }
    
    /**
     * Shows a list of available pets (not adopted) in a dialog
     */
    private void showAvailablePets() {
        StringBuilder availablePetsInfo = new StringBuilder();
        boolean hasAvailablePets = false;
        
        availablePetsInfo.append("Available Pets for Adoption:\n\n");
        
        for (Pet pet : shelter.getPets()) {
            if (!pet.isAdopted()) {
                availablePetsInfo.append("ID: ").append(pet.getId())
                    .append(" | Name: ").append(pet.getName())
                    .append(" | Species: ").append(pet.getSpecies())
                    .append(" | Age: ").append(pet.getAge())
                    .append("\n");
                hasAvailablePets = true;
            }
        }
        
        if (!hasAvailablePets) {
            availablePetsInfo.append("Sorry, there are currently no pets available for adoption.");
        }
        
        JTextArea textArea = new JTextArea(availablePetsInfo.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(
            view,
            scrollPane,
            "Available Pets",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}