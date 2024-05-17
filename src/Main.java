import components.*;
import java.util.*;



public class Main {
	
	static List<Client> clients;
	static List<Account> accounts;
	static HashMap<Integer, Account> hash;

	
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

	// 1.2.3 Creation of the tablea account
	public static List<Account> loadAccounts(List<Client> clients) {
		List<Account> accounts = new ArrayList<>();
		
		for (Iterator<Client> iterator = clients.iterator(); iterator.hasNext();) {
			Client client = iterator.next();
			accounts.add(new CurrentAccount("current", client));
			accounts.add(new SavingsAcount("saving", client));
		}
		
		return accounts;
	}
	
	public static void displayAccounts(List<Account> accounts) {
		accounts.stream().forEach(account -> System.out.println(account.toString()));
	}
	
	// 1.3.1 Adaptation of the table of accounts
	public static HashMap<Integer, Account> createHashMap(List<Account> accounts) {
		HashMap<Integer, Account> hash = new HashMap<Integer, Account>();
		
		for (Iterator<Account> iterator = accounts.iterator(); iterator.hasNext();) {
			Account account = iterator.next();
			hash.put(account.getAccountNumber(), account);
		}
		
		return hash;
	}

	public static void displayHash(HashMap<Integer, Account> hash) {
		hash.entrySet().stream().sorted(Map.Entry.comparingByValue(
				(o1, o2) -> Double.compare(o1.getBalance(), o2.getBalance()))).forEach(s -> System.out.println(s.toString()));
	}
	
	
	public static void main(String[] args) {
		clients = loadClients(5);
		//displayClient(clients);
		
		accounts = loadAccounts(clients);
		
		//displayAccounts(accounts);
		
		hash = createHashMap(accounts);
			
		displayHash(hash);

	}
}
