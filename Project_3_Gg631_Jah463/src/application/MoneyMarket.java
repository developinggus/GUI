package application;
/**
 * This is a specific type of Account.
 * @author Joseph Hawkins, Gustavo Garcia
 *
 */
public class MoneyMarket extends Account {
	private int withdrawals;
	
	/**
	 * Initializes a money market account
	 * @param holder holder of the account
	 * @param balance balance of the account
	 * @param dateOpen date the account was opened
	 */
	public MoneyMarket(Profile holder, double balance, Date dateOpen) {
		super(holder,balance,dateOpen);
		withdrawals = 0;
	}
	
	/**
	 * Returns the total monthly interest for the account
	 */
	@Override
	public double monthlyInterest() {
		double monthly_interest_rate = 0.0065 / 12;
		return monthly_interest_rate * getBalance();
	}
	
	/**
	 * Returns the monthly fee for the account
	 */
	@Override
	public double monthlyFee() {
		if ( getBalance() >= 2500 && withdrawals <= 6) {
			return 0;
		}
		return 12;
	}
	
	/**
	 * Sets the number of withdrawals
	 * @param withdrawals Number of withdrawals for the given account.
	 */
	public void setWithdrawals(int withdrawals) {
		this.withdrawals = withdrawals;
	}
	
	/**
	 * Returns number of withdrawals
	 * @return withdrawals Number of withdrawals made.
	 */
	public int getWithdrawals() {
		return withdrawals;
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