package components;

import java.util.Date;

//1.3.3 Creation of the Transfert, Credit, Debit classes
public class Transfert extends Flow{
	private int issuingAccount;
	
	public Transfert(String comment, int id, double amount, int targetAccount, boolean effect, Date date, int issuingAccount) {
		super(comment, id, amount, targetAccount, effect, date);
		this.issuingAccount = issuingAccount;
	}

	public int getIssuingAccount() {
		return issuingAccount;
	}

	public void setIssuingAccount(int issuingAccount) {
		this.issuingAccount = issuingAccount;
	}
}
