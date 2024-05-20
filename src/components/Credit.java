package components;

import java.util.Date;

// 1.3.3 Creation of the Transfert, Credit, Debit classes
public class Credit extends Flow {

	public Credit(String comment, int id, double amount, int targetAccount, boolean effect, Date date) {
		super(comment, id, amount, targetAccount, effect, date);
	}

}
