package components;

// 1.2.1 Creation of the account class
public class Account {
	protected String label;
	protected double balance = 0;
	protected int accountNumber;
	protected Client client;
	
	private static int accountCount = 1;
	
	public Account(String label, Client client) {
		this.label = label;
		this.client = client;
		
		this.accountNumber = accountCount;
		accountCount++;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}

	public void setBalance(double amount) {
		this.balance += amount;
	}
	
	// 1.3.5 Updating accounts
	public void setBalance(Flow flow) {
		if (flow instanceof Debit) {
			this.balance -= flow.getAmount();
		}
		else if (flow instanceof Credit) {
			this.balance += flow.getAmount();
		}
		else if (flow instanceof Transfert transfer) {
			if (this.accountNumber == transfer.getTargetAccount()) {
				this.balance += transfer.getAmount();
			}
			else if (this.accountNumber == transfer.getIssuingAccount()) {
				this.balance -= flow.getAmount();
			}
		}
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public int getAccountNumber() {
		return this.accountNumber;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public String toString() {
		return "Label: " + this.label +
			   "\nBalance: " + this.balance +
			   "\nAccount Number: " + this.accountNumber +
			   "\nClient:\n" + this.client.toString();
	}
}
