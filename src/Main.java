import components.*;
import java.util.*;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Main {
	
	static List<Client> clients;
	static List<Account> accounts;
	static HashMap<Integer, Account> hash;
	static Flow[] flows;
	
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
	
	// 1.3.4 Creation of the flow array
	public static Flow[] loadFlows() {
		Flow[] flows = new Flow[12];
		
		LocalDate localDate = LocalDate.now().plusDays(2);
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		flows[0] = new Debit("debit", 1, 50.0, 1, false, date);
		
		int index = 1;
		// Credits to current accounts
		for (int i = 1; i < 10; i+=2) {
			flows[index] = new Credit("credit", index, 100.50, i, false, date);
			index++;
		}
		
		// Credits to savings accounts
		for (int i = 2; i < 11; i+=2) {
			flows[index] = new Credit("credit", index, 1500, i, false, date);
			index++;
		}
		
		flows[index] = new Transfert("transfer", index, 50.0, 2, false, date, 1);
		
		return flows;
		
	}
	
	// 1.3.5 Updating accounts
	public static void applyFlow(HashMap<Integer, Account> accounts, Flow[] flows) {
		Predicate<Double> isnegativePredicate = i -> (i < 0);
		
		for (int i = 0; i < flows.length; i++) {
			int accountsTargetID = flows[i].getTargetAccount();
			Account targetAccount = accounts.get(accountsTargetID);
			
			targetAccount.setBalance(flows[i]);
			
			if (flows[i] instanceof Transfert transfert) {
				int issuingAccountID = transfert.getIssuingAccount();
				Account issuingAccount = accounts.get(issuingAccountID);
				issuingAccount.setBalance(transfert);
			}
		}
		
		accounts.values().stream().forEach(acc -> {
			if (isnegativePredicate.test(acc.getBalance())) {
				System.out.println("Negative balance of account number: " + acc.getAccountNumber());
			}
		});
	}
	
	// 2.1 JSON file of flows
	public static Flow[] loadFlows(String path) {
		Flow[] flows = new Flow[12];
		Path filePath = Paths.get(path);
    	JSONParser parser = new JSONParser();
    	SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
  	
	    try {
	    	FileReader tFileReader = new FileReader(filePath.toFile());
	    	JSONArray objects = (JSONArray) parser.parse(tFileReader);
	    	
	    	for (Object obj : objects) {
	    		JSONObject flow = (JSONObject) obj;

	    	    String comment = (String) flow.get("comment");
	    	    int id = ((Long) flow.get("id")).intValue();
	    	    Double amount = (Double) flow.get("amount");
	    	    int targetAccount = ((Long) flow.get("targetAccount")).intValue();
	    	    boolean effect = (boolean) flow.get("effect");
	    	    Date date = formatter.parse((String) flow.get("date"));
	    	    
	    	    switch (comment) {
				case "debit": {
					flows[id - 1] = new Debit(comment, id, amount, targetAccount, effect, date);
					break;
				}
				case "credit": {
					flows[id - 1] = new Credit(comment, id, amount,targetAccount, effect, date);
					break;
				}
				case "transfer": {
					int issuingAccount = ((Long) flow.get("issuingAccount")).intValue();
					flows[id - 1] = new Transfert(comment, id, amount, targetAccount, effect, date, issuingAccount);
					break;
				}
				default: 
					throw new IllegalArgumentException("Unexpected value: " + comment);
	    	    }
	    	}
	    } catch (Exception e) {
			System.err.println("Incorrect file path" + e);
		}
		
		
		return flows;
	}
	
	public static List<Account> loadAccounts(String path) throws SAXException, IOException {
		Path filePath = Paths.get(path);
		List<Account> accounts = new ArrayList<>();
		
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(filePath.toFile());
			doc.normalize();
			
			NodeList nodeList = doc.getElementsByTagName("account");
			Client client = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				String label = "";
				Node node = nodeList.item(i);
				NodeList children = node.getChildNodes();
				
				// A refacto
				for (int j = 0; j < children.getLength(); j++) {
					if (children.item(j).getNodeType() == 1 && "label".equals(children.item(j).getNodeName())) {
						label = children.item(j).getTextContent();
					}
					if (children.item(j).getNodeType() == 1 && "client".equals(children.item(j).getNodeName())) {
						NodeList childrenClient = children.item(j).getChildNodes();
						String firstname = "";
						String lastname = "";
						int clientNumber = 0;
						
						for (int k = 0; k < childrenClient.getLength(); k++) {
							if (childrenClient.item(k).getNodeType() == 1 && "firstname".equals(childrenClient.item(k).getNodeName()))
								firstname = childrenClient.item(k).getTextContent();
							if (childrenClient.item(k).getNodeType() == 1 && "lastname".equals(childrenClient.item(k).getNodeName()))
								lastname = childrenClient.item(k).getTextContent();
							if (childrenClient.item(k).getNodeType() == 1 && "clientNumber".equals(childrenClient.item(k).getNodeName()))
								clientNumber = Integer.valueOf(childrenClient.item(k).getTextContent());
						}
						if (client == null || !(client.getFirstName().equals(firstname)) || !(client.getLastName().equals(lastname))) {
							client = new Client(firstname, lastname);
							client.setClientNumber(clientNumber);
						}
					}
				}
				if ("current".equals(label))
					accounts.add(new CurrentAccount(label, client));
				else {
					accounts.add(new SavingsAcount(label, client));
				}	
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return accounts;
	}
	
	public static void main(String[] args) throws SAXException, IOException {
		clients = loadClients(5);
		displayClient(clients);
		
		//accounts = loadAccounts(clients);
		accounts = loadAccounts("accounts.xml");
		//displayAccounts(accounts);
		
		hash = createHashMap(accounts);
		
		//flows = loadFlows();
		flows = loadFlows("flow.json");
		applyFlow(hash, flows);
		
		displayHash(hash);
		
	}
}
