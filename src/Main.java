import components.*;
import java.util.*;

public class Main {
	
	static List<Client> clients;
	
	// 1.1.2  Creation of main class for tests
	public static List<Client> loadClients(int numbersOfClients) {
		List<Client> clients = new ArrayList<>();
		
		for (int i = 1; i <= numbersOfClients ; i++) {
			clients.add(new Client("firstname" + i, "name" + i));
		}
		
		return clients;
	}
	
	public static void displayClient(List<Client> clients) {
		clients.stream().forEach(client -> System.out.println(client.toString()));
	}

	
	public static void main(String[] args) {
		clients = loadClients(5);
		
		displayClient(clients);
	}
}
