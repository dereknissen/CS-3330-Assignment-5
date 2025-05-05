package main;

import controller.PetController;
import model.*;
import view.MainView;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Main class to initialize and run the Pet Adoption Center application.
 */
public class Main {

    public static void main(String[] args) {
        Shelter<Pet> petShelter = new Shelter<>();

        // Load regular pets
        List<Pet> regPets = readRegPets("src/main/resources/animals/pets.json");
        if (regPets != null) {
            for (Pet pet : regPets) {
                petShelter.addAnimal(pet);
            }
        }

        // Load exotic pets with adapter
        List<ExoticAnimal> exoticPets = readExoticPets("src/main/resources/animals/exotic_animals.json");
        if (exoticPets != null) {
            for (ExoticAnimal pet : exoticPets) {
                ExoticAnimalAdapter wrappedExoticPet = new ExoticAnimalAdapter(pet, petShelter);
                wrappedExoticPet.sync();
                petShelter.addAnimal(wrappedExoticPet);
            }
        }

        SwingUtilities.invokeLater(() -> {
            MainView view = new MainView();
            PetController controller = new PetController(petShelter, view);
            view.setController(controller);
            view.refreshPetList(petShelter.getPets()); // Initial display
            view.setVisible(true);
        });
    }

    /**
     * Reads regular pets from a JSON file.
     * @param filepath The path to the JSON file.
     * @return A list of Pet objects, or an empty list if an error occurs.
     */
    public static List<Pet> readRegPets(String filepath) {
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            return gson.fromJson(br, new TypeToken<ArrayList<Pet>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Reads exotic pets from a JSON file.
     * @param filepath The path to the JSON file.
     * @return A list of ExoticAnimal objects, or an empty list if an error occurs.
     */
    public static List<ExoticAnimal> readExoticPets(String filepath) {
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            return gson.fromJson(br, new TypeToken<ArrayList<ExoticAnimal>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}