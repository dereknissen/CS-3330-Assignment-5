package view;

import model.Pet;
import controller.PetController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main GUI view for the Pet Adoption Center application.
 */
public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
    private JTextField nameField, speciesField, typeField, ageField, idField;
    private JComboBox<String> sortComboBox;
    private JTextArea petListArea;
    private JButton addPetButton, adoptPetButton, removePetButton, sortButton, saveButton, loadButton, viewDetailsButton;
    private PetController controller;

    public MainView() {
        setTitle("Pet Adoption Center");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField(10);
        speciesField = new JTextField(10);
        typeField = new JTextField(10);
        ageField = new JTextField(10);
        idField = new JTextField(10);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Species:"));
        formPanel.add(speciesField);
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Sort By:"));
        sortComboBox = new JComboBox<>(new String[]{"name", "age", "species"});
        formPanel.add(sortComboBox);
        add(formPanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addPetButton = new JButton("Add Pet");
        adoptPetButton = new JButton("Adopt Pet");
        removePetButton = new JButton("Remove Pet");
        sortButton = new JButton("Sort Pets");
        saveButton = new JButton("Save Pets");
        loadButton = new JButton("Load Pets");
        viewDetailsButton = new JButton("View Details");

        buttonPanel.add(addPetButton);
        buttonPanel.add(adoptPetButton);
        buttonPanel.add(removePetButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(viewDetailsButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Pet List Area
        petListArea = new JTextArea(15, 40);
        petListArea.setEditable(false);
        add(new JScrollPane(petListArea), BorderLayout.SOUTH);

        // Add action listener for View Details
        viewDetailsButton.addActionListener(e -> {
            int petId = getIdFromField("view details");
            if (petId != -1) {
                controller.viewPetDetails(petId);
            }
        });
    }

    /**
     * Sets the controller for this view.
     * @param controller The PetController instance.
     */
    public void setController(PetController controller) {
        this.controller = controller;
    }

    /**
     * Retrieves pet data from the form to create a new Pet object.
     * @return A new Pet object, or null if input is invalid.
     */
    public Pet getPetFromForm() {
        try {
            String name = nameField.getText().trim();
            String species = speciesField.getText().trim();
            String type = typeField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            int id = Integer.parseInt(idField.getText().trim());

            if (name.isEmpty() || species.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name, species, and type cannot be empty!");
                return null;
            }
            if (age < 0 || id < 0) {
                JOptionPane.showMessageDialog(this, "Age and ID cannot be negative!");
                return null;
            }
            return new Pet(name, species, type, age, false, id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age and ID must be valid numbers!");
            return null;
        }
    }

    /**
     * Gets the pet ID for adoption.
     * @return The pet ID, or -1 if invalid.
     */
    public int getPetIdForAdopt() {
        return getIdFromField("adopt");
    }

    /**
     * Gets the pet ID for removal.
     * @return The pet ID, or -1 if invalid.
     */
    public int getPetIdForRemoval() {
        return getIdFromField("remove");
    }

    /**
     * Retrieves the pet ID from the ID field.
     * @param action The action being performed (for error messages).
     * @return The pet ID, or -1 if invalid.
     */
    private int getIdFromField(String action) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            if (id < 0) {
                JOptionPane.showMessageDialog(this, "ID cannot be negative!");
                return -1;
            }
            return id;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid ID to " + action + ".");
            return -1;
        }
    }

    /**
     * Gets the selected sorting criteria.
     * @return The sorting criteria.
     */
    public String getSortCriteria() {
        return (String) sortComboBox.getSelectedItem();
    }

    /**
     * Displays details of a pet in a dialog.
     * @param pet The pet to display.
     */
    public void displayPetDetails(Pet pet) {
        if (pet != null) {
            JOptionPane.showMessageDialog(this, pet.toString());
        }
    }

    /**
     * Refreshes the pet list display.
     * @param pets The list of pets to display.
     */
    public void refreshPetList(List<Pet> pets) {
        petListArea.setText("");
        if (pets != null) {
            for (Pet pet : pets) {
                petListArea.append(pet.toString() + "\n");
            }
        }
    }

    /**
     * @return addPetButton
     */
    public JButton getAddPetButton() {
    	return addPetButton;
    }
    
    /**
     * @return adoptPetButton
     */
    public JButton getAdoptPetButton() {
    	return adoptPetButton;
    }
    
    /**
     * @return removePetButton
     */
    public JButton getRemovePetButton() {
    	return removePetButton;
    }
    
    /**
     * @return sortButton
     */
    public JButton getSortButton() {
    	return sortButton;
    }
    
    /**
     * @return saveButton
     */
    public JButton getSaveButton() {
    	return saveButton;
    }
    
    /**
     * @return loadButton
     */
    public JButton getLoadButton() {
    	return loadButton;
    }
}