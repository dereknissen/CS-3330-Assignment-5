package model;

import com.google.gson.annotations.Expose;

public class ExoticAnimal extends Pet {

    @Expose
    private String uniqueId;
    @Expose
    private String animalName;
    @Expose
    private String category;
    @Expose
    private String subSpecies;
    @Expose
    private int yearsOld;

    public ExoticAnimal() {
        super();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubSpecies() {
        return subSpecies;
    }

    public void setSubSpecies(String subSpecies) {
        this.subSpecies = subSpecies;
    }

    public int getYearsOld() {
        return yearsOld;
    }

    public void setYearsOld(int yearsOld) {
        this.yearsOld = yearsOld;
    }

    @Override
    public String toString() {
        return "ExoticAnimal [uniqueId=" + uniqueId + ", animalName=" + animalName + ", category=" + category
                + ", subSpecies=" + subSpecies + ", yearsOld=" + yearsOld + "]";
    }
}