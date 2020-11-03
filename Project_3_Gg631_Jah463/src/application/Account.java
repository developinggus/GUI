package application;
/**
 * This is an abstract class that defines the common features of all account types.
 * @author Joseph Hawkins, Gustavo Garcia
 *
 */
public abstract class Account {
	private Profile holder;
	private double balance;
	private Date dateOpen;
	
	/**
	 * Initialized the account	
	 * @param holder holder of the account
	 * @param balance balance of the account
	 * @param dateOpen date the account was opened
	 */
	public Account(Profile holder, double balance, Date dateOpen) {
		this.holder = holder;
		this.balance = balance;
		this.dateOpen = dateOpen;
	}
	
	/**
	 * Decrease balance by amount
	 * @param amount amount the balance will be decreased by
	 */
	public void debit(double amount) {
		this.balance = this.balance - amount;
	}
	
	/**
	 * Increased balance by amount
	 * @param amount amount the balance will be increased by
	 */
	public void credit(double amount) {
		 this.balance = this.balance + amount;
	}
	
	/**
	 * Formats the account information into a string
	 * @return string of account data
	 */
	@Override
	public abstract String toString();
	
	/**
	 * Calculates the monthly interest of the account
	 * @return interest monthly interest of an account
	 */
	public abstract double monthlyInterest();
	
	/**
	 * Returns the monthly fee of the account
	 * @return monthlyFee 
	 */
	public abstract double monthlyFee();
	
	/**
	 * Getter method for balance.
	 * @return balance Current balance of account.
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Setter method for balance
	 * @param balance New balance for the account
	 */
	private void setBalance(double balance) {
		this.balance = balance;
	}
	
	/**
	 * Compares two accounts. 
	 * @param obj Potential account to compare.
	 * @return true if they are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account item = (Account)obj; // Cast to account item

			if ((this.holder.equals(item.getHolder())) ) {
				return true;
			}
		}
		return false; 
	}
	
	/**
	 * Gets date account was opened.
	 * @return openDate the date when account was opened.
	 */
	public Date getDate() {
		return dateOpen;
	}
	
	/**
	 * Gets the profile of the account holder.
	 * @return profile this is info about the account holder.
	 */
	public Profile getHolder() {
		return holder;
	}
	
	public static void main (String[] args) {
	
	
	}
}
