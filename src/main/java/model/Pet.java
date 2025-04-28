package model;

public class Pet {
	
	private String name;
	private String species;
	private String type;
	private int age;
	private int id;
	boolean adopted;
	
	public Pet(String name, String species, String type, int age, boolean adopted, int id) {
		this.name = name;
		this.species = species;
		this.type = type;
		this.age = age;
		this.adopted = adopted;
		this.id = id;
	}
	
	public Pet() {
		super();
	}
	
	public boolean isAdopted() {
		return adopted;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAdopted(boolean adopted) {
		this.adopted = adopted;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Pet [name=" + name + ", species=" + species + ", type=" + type + ", age=" + age + ", id=" + id
				+ ", adopted=" + adopted + "]";
	}
		
}
