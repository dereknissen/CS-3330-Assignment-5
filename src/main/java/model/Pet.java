package model;

import com.google.gson.annotations.Expose;

public class Pet implements Comparable<Pet> {

    @Expose
    private String name;
    @Expose
    private String species;
    @Expose
    private String type;
    @Expose
    private int age;
    @Expose
    private int id;
    @Expose
    boolean adopted;

    /**
     * Default constructor
     * @param name
     * @param species
     * @param type
     * @param age
     * @param adopted
     * @param id
     */
    public Pet(String name, String species, String type, int age, boolean adopted, int id) {
        this.name = name;
        this.species = species;
        this.type = type;
        this.age = age;
        this.adopted = adopted;
        this.id = id;
    }

    /**
     * Constructor
     */
    public Pet() {
        super();
    }

    /**
     * Checks if animal is adopted
     * @return true if adopted false otherwise
     */
    public boolean isAdopted() {
        return adopted;
    }

    /**
     * @return pet id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets pet id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set pet adoption status
     * @param adopted
     */
    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    /**
     * @return pet name
     */
    public String getName() {
        return name;
    }

    /**
     * Set pet name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return pet species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Set pet species
     * @param species
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * @return pet age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set pet age
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return pet type
     */
    public String getType() {
        return type;
    }

    /**
     * Set pet type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Convert pet data to string
     * @return pet data string
     */
    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Species: " + species + ", Age: " + age + ", id: " + id
                + ", Adopted: " + adopted + "]";
    }

    /**
     * Compare pet
     */
    @Override
    public int compareTo(Pet other) {
        return this.getName().compareToIgnoreCase(other.getName());
    }
}