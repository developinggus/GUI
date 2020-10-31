package application;
/**
 * This is an array-based container class.
 * The array holds different instances in Checking, Savings, MoneyMarket
 *
 * @author Joseph Hawkins, Gustavo Garcia
 *
 */
public class AccountDatabase {
	private Account[] accounts;
	private int size;

	/**
	 * Constructor for AccountDatabase, initializes to size 5
	 */
	public AccountDatabase() {
		this.accounts = new Account [5];
		this.size = 0;
	}

	/**
	 * Checks to see if account exists
	 * @param account The account object
	 * @return 1 if it exists, -1 otherwise
	 */
	private int find(Account account) {

		for(int i = 0; i < size; i++) {

			if( accounts[i].equals(account) ) {
				//THIS NEEDS TO BE REMOVED check 4:26 10/22 lecture
				if ( (accounts[i] instanceof Checking) && (account instanceof Checking )) {
					return i;
				}

				if ( (accounts[i] instanceof MoneyMarket) && (account instanceof MoneyMarket )) {
					return i;
				}

				if ( (accounts[i] instanceof Savings) && (account instanceof Savings )) {
					return i;
				}
			}
		}
		return -1; //Not found
	}

	/**
	 * Methods to grow the account list by 5
	 */
	private void grow() {
		Account[] tempAccounts = new Account[size + 5];

		for (int i = 0; i < size; i++) {
			Account t = accounts[i];
			tempAccounts[i] = t;
		}

		accounts = tempAccounts;
	}

	/**
	 * Adds a new account to the database.
	 * @param item New account to be added
	 * @return true if account adding was successful, false otherwise.
	 */
	public boolean add(Account item) {

		if ( find(item) != -1 ) { //Has to be the first of its kind
			return false;
		}

		if(!item.getDate().isValid()) {
			throw new NumberFormatException("Date is not valid.");
		}
		
		if( size < accounts.length ) {
			accounts[size] = item;
			size++;
		} else {
			grow();
			add(item);
		}

		return true;
	}

	/**
	 * Removes the accounts from the database
	 * @param item Account to be removed
	 * @return true if removed, false otherwise
	 */
	public boolean remove(Account item) {
		int accountIndex = find(item);

		if( accountIndex == -1 ) {
			return false;
		}

		accounts[accountIndex] = accounts[size - 1];
		accounts[size - 1] = null;
		size --;
		return true;
	}

	/**
	 * Increases account balance by amount
	 * @param account Account to be altered
	 * @param amount Amount the balance will be increased by
	 * @return Returns true if balance is increased, false otherwise
	 */
	public boolean deposit(Account account, double amount) {
		int accountIndex = find(account);

		if( accountIndex == -1 ) {
			return false;
		}

		this.accounts[accountIndex].credit(amount);

		return true;
	}

	/**
	 * Removes amount from account balance
	 * @param account Account to be altered
	 * @param amount Amount to be removed
	 * @return -1 if account does not exist, 0 if success, 1 if insufficient finds
	 */
	public int withdrawal(Account account, double amount) {
		int accountIndex = find(account);

		if ( accountIndex == -1 ) {
			return -1;
		}

		if ( this.accounts[accountIndex].getBalance() < amount ) {
			return 1;
		}

		this.accounts[accountIndex].debit(amount);

		if ( account instanceof MoneyMarket ) {
			MoneyMarket item = (MoneyMarket) this.accounts[accountIndex];
			item.setWithdrawals(item.getWithdrawals() + 1);
		}

		return 0;
	}

	/**
	 * Sorts the accounts by the date they were opened in ascending order.
	 *
	 */
	private void sortByDateOpen() {
		int n = size;

		for (int i = 0; i < n - 1; i++) {
			int earliest_date = i;

			for (int j = i+1; j < n; j++) {
				if ( (accounts[j].getDate() ).compareTo( accounts[earliest_date].getDate() ) < 0 ) {
					earliest_date = j;
				}
			}

			Account temp = accounts[earliest_date];
			accounts[earliest_date] = accounts[i];
			accounts[i] = temp;
		}
	}

	/**
	 * Sorts accounts by the last name
	 */
	private void sortByLastName() {
		int n = size;

		for (int i = 0; i < n - 1; i++) {
			int earliest_name = i;
			for (int j = i + 1; j < n; j++) {
				if ( (accounts[j].getHolder().getLname() )
						.compareTo( accounts[earliest_name].getHolder().getLname() ) < 0 ) {
					earliest_name = j;
				}
			}

			Account temp = accounts[earliest_name];
			accounts[earliest_name] = accounts[i];
			accounts[i] = temp;

		}
	}

	/**
	 * Sorts by the date they were opened. Then prints the accounts statements
	 */
	public String printByDateOpen() {
		String output = "";
		if(size == 0) {
			return "Database is empty.";
		}
		sortByDateOpen();
		//System.out.print("--Printing statements by date opened--");
		for (int i = 0; i < size; i++) {
			String account_info = accounts[i].toString();
			String header = "";
			String interest = "";
			String fee = "";
			String new_balance = "";

			if ( accounts[i] instanceof MoneyMarket ) {
				MoneyMarket item = (MoneyMarket) accounts[i];
				header = "*Money Market*" + account_info;

				if (item.getWithdrawals() == 1) {
					header = header + "*1 withdrawal*";
				} else {
					header = header + "*" + Integer.toString(item.getWithdrawals()) + " withdrawals*";
				}

				interest = "-interest: $ " + String.format("%,.2f", item.monthlyInterest());
				fee = "-fee: $ " + String.format("%,.2f", item.monthlyFee());
				accounts[i].debit(item.monthlyFee() - item.monthlyInterest());
				new_balance = "-new balance: $ " + String.format("%,.2f", accounts[i].getBalance());

			}

			if ( accounts[i] instanceof Checking ) {
				Checking item = (Checking) accounts[i];
				header = "*Checking*" + account_info;

				if (item.getDirectDeposit() == true) {
					header = header + "*direct deposit account*";
				}

				interest = "-interest: $ " + String.format("%,.2f", item.monthlyInterest());
				fee = "-fee: $ " + String.format("%,.2f", item.monthlyFee());
				accounts[i].debit(item.monthlyFee() - item.monthlyInterest());
				new_balance = "-new balance: $ " + String.format("%,.2f", accounts[i].getBalance());

			}

			if ( accounts[i] instanceof Savings ) {
				Savings item = (Savings) accounts[i];
				header = "*Savings*" + account_info;

				if (item.getLoyalty() == true) {
					header = header + "*special Savings account*";
				}

				interest = "-interest: $ " + String.format("%,.2f", item.monthlyInterest());
				fee = "-fee: $ " + String.format("%,.2f", item.monthlyFee());
				accounts[i].debit(item.monthlyFee() - item.monthlyInterest());
				new_balance = "-new balance: $ " + String.format("%,.2f", accounts[i].getBalance());
			}
			output = output + '\n' + '\n' + header + '\n' + interest + '\n'
					+ fee + '\n' + new_balance;
			/*
			System.out.println('\n');
			System.out.println(header);
			System.out.println(interest);
			System.out.println(fee);
			System.out.print(new_balance);
			*/
		}
		output = output + "\n" + "--end of printing--";
		/*
		System.out.println("");
		System.out.print("--end of printing--");
		*/
		return output;
	}

	/**
	 * Prints accounts sorted by last name
	 */
	public String printByLastName() {
		String output = "";
		if(size == 0) {
			return "Database is empty.";
		}
		sortByLastName();
		//System.out.print("--Printing statements by date opened--");
		for (int i = 0; i < size; i++) {
			String account_info = accounts[i].toString();
			String header = "";
			String interest = "";
			String fee = "";
			String new_balance = "";

			if ( accounts[i] instanceof MoneyMarket ) {
				MoneyMarket item = (MoneyMarket) accounts[i];
				header = "*Money Market*" + account_info;

				if (item.getWithdrawals() == 1) {
					header = header + "*1 withdrawal*";
				} else {
					header = header + "*" + Integer.toString(item.getWithdrawals()) + " withdrawals*";
				}

				interest = "-interest: $ " + String.format("%,.2f", item.monthlyInterest());
				fee = "-fee: $ " + String.format("%,.2f", item.monthlyFee());
				accounts[i].debit(item.monthlyFee() - item.monthlyInterest());
				new_balance = "-new balance: $ " + String.format("%,.2f", accounts[i].getBalance());

			}

			if ( accounts[i] instanceof Checking ) {
				Checking item = (Checking) accounts[i];
				header = "*Checking*" + account_info;

				if (item.getDirectDeposit() == true) {
					header = header + "*direct deposit account*";
				}

				interest = "-interest: $ " + String.format("%,.2f", item.monthlyInterest());
				fee = "-fee: $ " + String.format("%,.2f", item.monthlyFee());
				accounts[i].debit(item.monthlyFee() - item.monthlyInterest());
				new_balance = "-new balance: $ " + String.format("%,.2f", accounts[i].getBalance());

			}

			if ( accounts[i] instanceof Savings ) {
				Savings item = (Savings) accounts[i];
				header = "*Savings*" + account_info;

				if (item.getLoyalty() == true) {
					header = header + "*special Savings account*";
				}

				interest = "-interest: $ " + String.format("%,.2f", item.monthlyInterest());
				fee = "-fee: $ " + String.format("%,.2f", item.monthlyFee());
				accounts[i].debit(item.monthlyFee() - item.monthlyInterest());
				new_balance = "-new balance: $ " + String.format("%,.2f", accounts[i].getBalance());
			}
			output = output + '\n' + '\n' + header + '\n' + interest + '\n'
					+ fee + '\n' + new_balance;
			/*
			System.out.println('\n');
			System.out.println(header);
			System.out.println(interest);
			System.out.println(fee);
			System.out.print(new_balance);
			*/
		}
		output = output + "\n" + "--end of printing--";
		/*
		System.out.println("");
		System.out.print("--end of printing--");
		*/
		return output;
	}

	/**
	 * Prints all the accounts
	 */
	public String printAccounts() {
		if(size == 0) {
			return "Database is empty.";
		}
		
		String output = "--Listing accounts in the database--\n";
		for (int i = 0; i < size; i++) {

			if ( accounts[i] instanceof Checking ) {
				Checking item = (Checking) accounts[i];
				//System.out.print("*Checking*" + accounts[i].toString());
				output = output + "*Checking*" + accounts[i].toString();
				if (item.getDirectDeposit() == true) {
					//System.out.print("*direct deposit account*");
					output = output + "*direct deposit account*";
				}
				//System.out.println("");
				output = output + '\n';
			}

			if ( accounts[i] instanceof Savings ) {
				Savings item = (Savings) accounts[i];
				//System.out.print("*Savings*" + accounts[i].toString());
				output = output + "*Savings*" + accounts[i].toString();
				if (item.getLoyalty() == true) {
					//System.out.print("*special Savings accounts*");
					output = output + "*special Savings accounts*";
				}
				//System.out.println("");
				output = output + "\n";
			}

			if ( accounts[i] instanceof MoneyMarket ) {
				MoneyMarket item = (MoneyMarket) accounts[i];
				//System.out.print("*Money Market*" + accounts[i].toString());
				output = output + "*Money Market*" + accounts[i].toString();
				if (item.getWithdrawals() == 1) {
					//System.out.print("*1 withdrawal*");
					output = output + "*1 withdrawal*";
				} else {
					//System.out.print("*" + Integer.toString(item.getWithdrawals()) + " withdrawals*");
					output = output + "*" + Integer.toString(item.getWithdrawals()) + " withdrawals*";
				}
				//System.out.println("");
				output = output + '\n';
			}
		}
		//System.out.print("--end of listing--");
		output = output + "--end of listing--";
		return output;
	}


	public static void main (String[] args) {

	}
}
