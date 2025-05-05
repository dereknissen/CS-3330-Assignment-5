package model;

import com.google.gson.annotations.Expose;

/**
 * Adapter class to convert ExoticAnimal objects to Pet objects.
 */
public class ExoticAnimalAdapter extends Pet {
    
    @Expose(serialize = false)
    private transient ExoticAnimal exoticAnimal;
    
    @Expose(serialize = false)
    private transient Shelter<Pet> shelter;
    
    /**
     * Creates a new ExoticAnimalAdapter.
     * @param exoticAnimal The ExoticAnimal to adapt.
     * @param shelter The shelter where the pet is housed.
     */
    public ExoticAnimalAdapter(ExoticAnimal exoticAnimal, Shelter<Pet> shelter) {
        super(exoticAnimal.getAnimalName(), 
              exoticAnimal.getCategory(), 
              exoticAnimal.getSubSpecies(), 
              exoticAnimal.getYearsOld(), 
              false, 
              generateNumericId(exoticAnimal.getUniqueId()));
        this.exoticAnimal = exoticAnimal;
        this.shelter = shelter;
    }
    
    /**
     * Generates a numeric ID from a string ID by hashing it
     * @param stringId The string ID to convert
     * @return A numeric ID
     */
    private static int generateNumericId(String stringId) {
        if (stringId == null) return 0;
        // Try to parse as integer first
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            // If not a number, use hashCode as fallback
            return Math.abs(stringId.hashCode()) % 100000; // Limit to 5 digits
        }
    }
    
    /**
     * Syncs the exotic animal data with the adapter.
     */
    public void sync() {
        setName(exoticAnimal.getAnimalName());
        setSpecies(exoticAnimal.getCategory());
        setType(exoticAnimal.getSubSpecies());
        setAge(exoticAnimal.getYearsOld());
        setId(generateNumericId(exoticAnimal.getUniqueId()));
    }
    
    /**
     * @return The ExoticAnimal being adapted.
     */
    public ExoticAnimal getExoticAnimal() {
        return exoticAnimal;
    }
}