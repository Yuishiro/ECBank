package components;

import java.util.Date;

// 1.3.2 Creation of the Flow class
public abstract class Flow {
	private String comment;
	private int id;
	private double amount;
	private int targetAccount;
	private boolean effect;
	private Date date;
	
	public Flow(String comment, int id, double amount, int targetAccount, boolean effect, Date date) {
		this.comment = comment;
		this.id = id;
		this.amount = amount;
		this.targetAccount = targetAccount;
		this.effect = effect;
		this.date = date;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return this.comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTargetAccount() {
		return this.targetAccount;
	}

	public void setTargetaccount(int targetAccount) {
		this.targetAccount = targetAccount;
	}

	public boolean isEffect() {
		return effect;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
