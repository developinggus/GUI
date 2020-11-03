package application;
/**
 * Subclass of account for a Checking Account.
 * 
 * @author Joseph Hawkins, Gustavo Garcia
 *
 */ 
public class Checking extends Account {
	private boolean directDeposit;
	
	/**
	 * Initializes a Checking account object.
	 * @param holder holder of the account
	 * @param balance Balance of the account
	 * @param dateOpen Date the account was opened
	 * @param directDeposit Boolean statement for whether they have direct deposit
	 */
	public Checking(Profile holder, double balance, Date dateOpen, boolean directDeposit) {
		super(holder,balance,dateOpen);
		this.directDeposit = directDeposit;
	}
	
	
	/**
	 * Calculates the monthly interest for the checking account.
	 * 
	 * @return monthlyInterest total monthly interest for the account
	 */
	@Override
	public double monthlyInterest() {
		double monthly_interest_rate = 0.0005 / 12;
		return monthly_interest_rate * getBalance();
	}
	
	/**
	 * Calculates the monthly fee for the checking account.
	 * @return monthlyFee The monthly fee for the account.
	 */
	@Override
	public double monthlyFee() {
		if ( getBalance() >= 1500 || directDeposit == true) {
			return 0;
		}
		
		return 25;
	}
	
	/**
	 * Getter for if an account has direct deposit
	 * @return true if account has direct deposit, false otherwise.
	 */
	public boolean getDirectDeposit() {
		return directDeposit;
	}
	
	/**
	 * Formats the account information into a string
	 * @return string of account data
	 */
	@Override
	public String toString() {
		String full_name = this.getHolder().getFname() + " " + this.getHolder().getLname();
		String balance_to_string = "$" + String.format("%,.2f",this.getBalance());
		String date_to_string = this.getDate().toString();
		
		return full_name + "* " + balance_to_string + "*" + date_to_string;  
	}
}