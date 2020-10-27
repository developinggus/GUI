package application;
/**
 * This handles the transactions and displays results to console.
 * @author Joseph Hawkins, Gustavo Garcia
 *
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class TransactionManager {

	/**
	 * Makes a specific account type based on the input type.	
	 * @param details These details help construct an account.
	 * @return account This is the account that was created by details given.
	 */
	private Account makeAccount(String[] details) {
		char accountType = details[0].charAt(1);
		Profile customer = new Profile(details[1], details[2]);
		double balance = 0;
		Date dateOpen = null;
		boolean accountExtra = false;
		
		if(details[0].charAt(0) != 'C' &&
			details[0].charAt(0) != 'W' && 
			details[0].charAt(0) != 'D' ) {
			
			try {
				Double.parseDouble(details[3]);
			}
			catch ( NumberFormatException e) {
				throw new InputMismatchException("Input data type mismatch.");
			}
			
			customer = new Profile(details[1], details[2]);
			balance = Double.parseDouble(details[3]);
			dateOpen = new Date(details[4]);
			if(accountType != 'M') {
				accountExtra = Boolean.parseBoolean(details[5]);
				if(!details[5].toLowerCase().equals("false") &&
				   !details[5].toLowerCase().equals("true") ) {
					throw new InputMismatchException("Input data type mismatch.");
				}
			}
		}
		
		switch(accountType) {
		
		case 'C'://checking account
			return new Checking(customer, balance, dateOpen, accountExtra);

		case 'S'://savings account
			return new Savings(customer, balance, dateOpen, accountExtra);

		case 'M'://money market account
			return new MoneyMarket(customer,balance, dateOpen);
		
		}
		return null;
	}
	
	/**
	 * Creates a minimal account for withdrawing and depositing. 
	 * Minimal account has name and type of account.
	 * 
	 * @param details string of the account details
	 * @return acc minimal account encapsulating details. 
	 */
	private Account withdrawDepositAccount(String[] details) {
		char accountType = details[0].charAt(1);
		Profile customer = new Profile(details[1], details[2]);
		double balance = 0;
		Date dateOpen = null;
		boolean accountExtra = false;
		
		if(details[0].charAt(0) != 'C') {
			customer = new Profile(details[1], details[2]);
			balance = Double.parseDouble(details[3]); // amount to withdraw or deposit
		}
		
		switch(accountType) {
		
		case 'C'://checking account
			return new Checking(customer, balance, dateOpen, accountExtra);

		case 'S'://savings account
			return new Savings(customer, balance, dateOpen, accountExtra);

		case 'M'://money market account
			return new MoneyMarket(customer,balance, dateOpen);
		
		}
		return null;
	}
	
	/**
	 * Checks if the second letter is correct for accessing account type {C, S, M}.
	 * @param c second letter to check for valid command.
 	 * @return true if the second letter is valid, false otherwise.
	 */
	private boolean correctSecondLetter(char c) {
		if( (c == 'C') || 
			(c == 'S') || 
			(c == 'M') ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if the input is a valid print command.
	 * @param input Input to be checked if it's a print command.
	 * @return true if it's a print command, false otherwise.
	 */
	private boolean isPrintCommand(String command) {
		if(command.equals("PA") ||
		   command.equals("PD") ||
		   command.equals("PN")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method to print account database.
	 * @param command is the ordering for printing accounts.
	 * @param accounts is the account database to print.
	 */
	private void printCommand(String command, AccountDatabase accounts) {
		
		if(command.equals("PA")) {
			accounts.printAccounts();
		}
		
		else if(command.equals("PD")) {
			accounts.printByDateOpen();
		}
		
		else if(command.equals("PN")) {
			accounts.printByLastName();
		}
		
	}
	
	/**
	 * Main running method.
	 */
	public void run() {
		System.out.println("Transaction processing starts.....");
		Scanner sc = new Scanner(System.in);
		String input = null;
		AccountDatabase accounts = new AccountDatabase();
		double amount = 0;
		char command = 0;
		
		while(true) {
			input = sc.nextLine();
			Account acc = null;
			String[] splitted = input.split("\\s+");
            
        	if(!splitted[0].equals("")) { // catches no input as invalid
            	command = splitted[0].charAt(0);
            	
            	if(command == 'Q') {//Quit program
            		System.out.println("Transaction processing completed.");
            		sc.close();
            		return;
            	}

            	// this only handles opening accounts not withdraw or deposit
            	else if(splitted[0].length() == 2) {
            		if(correctSecondLetter( splitted[0].charAt(1) )) {//check for correct second letter
                		try {
            			acc = makeAccount(splitted);//wrap this is exception to catch input errors
            		
                		}
                		catch (InputMismatchException e){
                			System.out.println("Input data type mismatch.");
                			continue;
                		}
                	}
            		else if(isPrintCommand(splitted[0])) {
            			command = 'P';
            		}
            		else {
            			command = 'X';
            		}
            	}
            	
            	else {
            		command = 'X';
            	}
            	
            }
        	
        	
        	switch(command) {

        	case 'O'://Open account
        		try {
        			
        			if(accounts.add(acc)) {
        				System.out.println("Account opened and added to the database.");
        			}
            		
        			else {
            			System.out.println("Account is already in the database.");
            		}
        		}
        		catch (NumberFormatException e){
        			System.out.println(acc.getDate().toString() + " is not a valid date!");
        		}

        		
        		break;
        	
        	case 'C'://Close account
        		if(accounts.remove(acc)) {
        			System.out.println("Account closed and removed from the database.");
        		}
        		else{
        			System.out.println("Account does not exist.");
        		}
        		break;
        	
        	case 'D'://Deposit
        		
        		try {
            		amount = Double.parseDouble(splitted[3]);
            		}
            	
        		catch (NumberFormatException e) {
            		System.out.println("Input data type mismatch.");
            		continue;
            	}
        		
        		acc = withdrawDepositAccount(splitted);
        		if(accounts.deposit(acc, amount)) {
        			System.out.println(String.format("%.2f", amount) + " deposited to account.");
        		}
        		
        		else {
        			System.out.println("Account does not exist.");
        		}
        		break;
        		
        	case 'W'://Withdraw
        		try {
        		amount = Double.parseDouble(splitted[3]);
        		}
        		catch (NumberFormatException e) {
        			System.out.println("Input data type mismatch.");
        			continue;
        		}
        		acc = withdrawDepositAccount(splitted);
        		if(accounts.withdrawal(acc, amount) == 0) { //successful deposit
        			System.out.println(String.format("%.2f", amount) + " withdrawn from account.");
        		}
        		
        		else if(accounts.withdrawal(acc, amount) == 1) {//withdraw amount higher than balance
        			System.out.println("Insufficient funds.");
        		}
        		
        		else {
        			System.out.println("Account does not exist.");//account not found
        		}
        		break;
        		
        	case 'P'://print list of accounts or account statements
    			printCommand(splitted[0], accounts);
    			System.out.println("");
        		break;
        		
        	case 'X'://no second letter
        	default:
    			System.out.println("Command '" + splitted[0] + "' not supported!");
        	}
		}
	}
	
	public static void main(String[] args) {
	}

}
