package components;


// 1.1.1 Creation of the client class
public class Client {
	private String firstName;
	private String lastName;
	private int clientNumber;
	
	private static int nextClientNumber = 1;
	
	public Client(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.clientNumber = nextClientNumber;
		
		nextClientNumber++;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return this.firstName;
		
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}
	
	public int getClientNumber() {
		return this.clientNumber;
	}
	
	public String toString() {
		return "First name: " + this.firstName + "\nLast Name: " + this.lastName + "\nClient Number: " + this.clientNumber;
	}

}
