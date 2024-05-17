package components;

import java.util.Date;

// 1.3.3 Creation of the Transfert, Credit, Debit classes
public class Debit extends Flow {

	public Debit(String comment, int id, double amount, int targetAccount, boolean effect, Date date) {
		super(comment, id, amount, targetAccount, effect, date);
	}

}
