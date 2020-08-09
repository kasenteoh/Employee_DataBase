
public class Employee implements Comparable<Employee> {
	private String id;
	private String name;
	private double pay;
	private String position;
	private char gender;
	
	public Employee(String name, String id, double pay, String position, char gender){
		this.id = id;
		this.name = name;
		this.pay = pay; 
		this.position = position;
		this.gender = gender;
	}
	
	/**
     * Accesses the id of the Employee
     * @return the Employee's id
     */
    public String getId() {
        return id;
    }
   
    /**
     * Accesses the name of the Employee
     * @return the Employee's name
     */
    public String getName() {
        return name;
    }
   
    /**
     * Access the salary of the Employee
     * @return the Employee's salary
     */
    public double getPay() {
        return pay;
    }
   
    /**
     * Access the position of the Employee
     * @return the Employee's position
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * Access the position of the Employee
     * @return the Employee's position
     */
    public char getgender() {
        return gender;
    }
   
    /**
     * Sets the id of the Employee
     * @param id the Employee's id
     */
    public void setID(String id) {
        this.id = id;
    }
   
    /**
     * Sets the name of the Employee
     * @param name the Employee's name
     */
    public void setName(String name) {
        this.name = name;
    }
   
    /**
     * Sets the pay of the Employee
     * @param pay the Employee's pay
     */
    public void setPay(double pay) {
        this.pay = pay;
    }
   
    /**
     * Sets the position of the Employee
     * @param position the Employee's position
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    /**
     * Sets the gender of the Employee
     * @param gender the Employee's position
     */
    public void setGender(char gender) {
        this.gender = gender;
    }
	
    
	@Override
	public String toString() {
		String result = "Name: " + name + "\nID: " + id + "\nPay: $" + pay + "\nPosition: "
				+ position + "\nGender: " + gender + "\n\n";
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Employee)) {
			return false;
		} else {
			Employee E = (Employee) o;
			if(!E.id.equals(" ")) {
				return this.id.equals(E.id);
			}else {
				return this.name.equals(E.name);
			}
		}
	}
	
	
	public int compareTo(Employee otherEmployee) {
		int thisID = Integer.valueOf(this.id);
		int otherID = Integer.valueOf(otherEmployee.id);
		
		if(thisID < otherID) {
			return -1;
		} else if (thisID == otherID) {
			return 0;
		}
		return 1;
	}
	
	@Override
	public int hashCode() {
		String key = name + id; // define key for this class
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	
	public int hashCodeName() {
		String key = id; // define key for this class
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	
	public int hashCodeID() {
		String key = id; // define key for this class
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	
}
