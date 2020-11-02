package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class SampleController {
	/*
	 * TO-DO
	 * Tab 2 & Tab 3
	 * Deposit/withdraw
	 * 
	 * Import/export / display similar to project 2 printing
	 * 
	 */
    
	private AccountDatabase accounts = new AccountDatabase();
		
	@FXML
    private TextField fName;
	
	@FXML
    private TextField lName;
	
	@FXML
    private TextField fName2;
	
	@FXML
    private TextField lName2;
    
	@FXML
    private TextField balance;
    
    @FXML
    private TextField amount;
        
    @FXML
    private TextField month;
    
    @FXML
    private TextField day;

    @FXML
    private TextField year;
    
    @FXML
    private TextArea messageArea;
        
    @FXML
    private ToggleGroup accountType;
        
    @FXML
    private RadioButton rbChecking;
    
    @FXML
    private RadioButton rbSavings;
 
    @FXML
    private RadioButton rbChecking2;

    @FXML
    private RadioButton rbSavings2;

    @FXML
    private RadioButton rbMoneyMarket2;

    @FXML
    private CheckBox directDeposit;

    @FXML
    private CheckBox isLoyal;
    
    @FXML
    private RadioButton rbMoneyMarket;
    
    @FXML
    private Button openAccountButton;
    
    @FXML
    private Button closeAccountButton;

    @FXML
    private Button clearTabOneButton;

    @FXML
    private MenuBar fileMenuButton;
    
    @FXML
    private MenuItem importButton;
    
    @FXML
    private Button withdrawButton;

    @FXML
    private Button depositButton;
    
    @FXML
    private Button printAccountsButton;
    
    @FXML
    private Button printAccountsByDateButton;
    
    @FXML
    private Button printAccountByLastNameButton;
    
    /**
     * clear account creation fields and reset all buttons/check boxes.
     * @param event clicking the clear button.
     */
    @FXML
    private void clearTabOne(ActionEvent event) {
    	fName.setText("");
    	lName.setText("");
    	balance.setText("");
    	month.setText("");
    	day.setText("");
    	year.setText("");
    	messageArea.setText("");
    	directDeposit.setSelected(false);
		directDeposit.setDisable(false);
		isLoyal.setSelected(false);
		isLoyal.setDisable(false);
		accountType.selectToggle(null);
    	
    }
    
    /**
     * When checking account is clicked the proper check boxes are disabled and cleared for direct deposit and isloyal. 
     * @param event clicking the checking radio button.
     */
    @FXML
    private void checkingRbClicked(MouseEvent event) {
		directDeposit.setSelected(false);
		directDeposit.setDisable(false);
		isLoyal.setSelected(false);
		isLoyal.setDisable(true);
    }
    
    /**
     * When savings account is clicked the proper check boxes are disabled and cleared for direct deposit and isloyal. 
     * @param event clicking the savings radio button.
     */
    @FXML
    private void savingsRbClicked(MouseEvent event) {
		directDeposit.setSelected(false);
		directDeposit.setDisable(true);
		isLoyal.setSelected(false);
		isLoyal.setDisable(false);
    }
    
    /**
     * When money market account is clicked check boxes are disabled and cleared for direct deposit and isloyal. 
     * @param event clicking the money market radio button.
     */
    @FXML
    private void moneyMarketRbClicked(MouseEvent event) {
    	directDeposit.setSelected(false);
		directDeposit.setDisable(true);
		isLoyal.setSelected(false);
		isLoyal.setDisable(true);
    }
    
    /**
     * checks if a potential str could be a valid balance for an account.
     * @param str potential balance of an account
     * @return true if str is a valid balance, false otherwise
     */
    private boolean isValidBalance(String str) {
    	if(str.equals("")) {
    		return false;
    	}
    	
    	
    	try {
    		double balance = Double.parseDouble(str);
    		if(balance >= 0) {
    			return true;
    		}
    		return false;
    	}
    	catch ( NumberFormatException e) {
    		return false;
    	}
    	
    }
    
    /**
     * checks if a potential triple of strings could be a valid date. 
     * 
     * @param year potential year
     * @param month potential month
     * @param day potential day
     * @return true if the date is valid, false otherwise.
     */
    private boolean isValidDate(String y, String m, String d) {
    	int yyyy, mm, dd;
    	try {
    		yyyy = Integer.parseInt(y);
    		mm = Integer.parseInt(m);
    		dd = Integer.parseInt(d);
    	}
    	catch ( NumberFormatException e) {
    		return false;
    	}
    	
    	Date date = new Date(yyyy, mm, dd);
    	
    	if(date.isValid()) {
    		return true;
    	}return false;
    }
   
    /**
     * checks for correct input is received when trying to create an account.
     * @return true if all input is correct, false otherwise.
     */
    private boolean invalidAccountInput() {
    	//currently just appends so output can be a bit cluttered
    	
    	boolean hasError = false;
    	//missing account type
    	if(!rbChecking.isSelected() && !rbSavings.isSelected() && !rbMoneyMarket.isSelected() ) {
    		//output error about input missing for account type 
    		messageArea.appendText("ERROR: Missing account type.\n");
    		hasError = true;
    	}
    	
    	//missing name fields
    	if(fName.getText().equals("") || lName.getText().equals("")) {
    		//output error about missing input
    		messageArea.appendText("ERROR: Incomplete name field.\n");
    		hasError = true;
    	}
    	
    	//missing or invalid balance
    	if(!isValidBalance(balance.getText())) {
    		//output error about missing/ improper balance input
    		messageArea.appendText("ERROR: Missing or improper balance.\n");
    		hasError = true;
    	}
    	
    	//missing or invalid date
    	if(!isValidDate(year.getText(), month.getText(), day.getText())) {
    		messageArea.appendText("ERROR: Missing or invalid date.\n");
    		hasError = true;
    	}
    	messageArea.appendText("\n");
    	return hasError;
    }
      
    /**
     * Open an account from user input.
     * @param event clicking open account button.
     */
    @FXML
    private void openAccount(ActionEvent event) {
    	
    	if(invalidAccountInput()) return;
    	
    	Profile p = new Profile(fName.getText(), lName.getText());
    	
    	Date d = new Date(Integer.parseInt(year.getText()), 
    			Integer.parseInt(month.getText()), 
    			Integer.parseInt(day.getText()));
    	
    	
    	if(rbChecking.isSelected()) {
    		Checking acc = new Checking(p, Double.parseDouble(balance.getText()), 
    									d, directDeposit.isSelected());
    		if(accounts.add(acc)) {
    			accounts.add(acc);
    			messageArea.appendText("Account opened and added to the database.\n");
    		}
    		else {
    			messageArea.appendText("Account is already in the database.\n");
    		}
    	}
    	
    	if(rbSavings.isSelected()) {
    		Savings acc = new Savings(p, Double.parseDouble(balance.getText()), 
    									d, isLoyal.isSelected());
    		if(accounts.add(acc)) {
    			accounts.add(acc);
    			messageArea.appendText("Account opened and added to the database.\n");
    		}
    		else {
    			messageArea.appendText("Account is already in the database.\n");
    		}
    	}
    	
    	if(rbMoneyMarket.isSelected()) {
    		MoneyMarket acc = new MoneyMarket(p, Double.parseDouble(balance.getText()), d);
    		if(accounts.add(acc)) {
    			accounts.add(acc);
    			messageArea.appendText("Account opened and added to the database.\n");
    		}
    		else {
    			messageArea.appendText("Account is already in the database.\n");
    		}

    	}
    	
    }
    
    /**
     * Close account with valid user input
     * @param event
     */
    @FXML
    private void closeAccount(ActionEvent event) {
    	if(invalidAccountInput()) return;
    	
    	Profile p = new Profile(fName.getText(), lName.getText());
    	
    	Date d = new Date(Integer.parseInt(year.getText()), 
    			Integer.parseInt(month.getText()), 
    			Integer.parseInt(day.getText()));
    	
    	if(rbChecking.isSelected()) {
    		Checking acc = new Checking(p, Double.parseDouble(balance.getText()), 
    									d, directDeposit.isSelected());
    		if(accounts.remove(acc)) {
        		messageArea.appendText("Account closed and removed from the database.\n");
    		}
    		else {
    			messageArea.appendText("Account does not exist.\n");
    		}
    	}
    	
    	if(rbSavings.isSelected()) {
    		Savings acc = new Savings(p, Double.parseDouble(balance.getText()), 
    									d, isLoyal.isSelected());
    		if(accounts.remove(acc)) {
        		messageArea.appendText("Account closed and removed from the database.\n");
    		}
    		else {
    			messageArea.appendText("Account does not exist.\n");
    		}

    	}
    	
    	if(rbMoneyMarket.isSelected()) {
    		MoneyMarket acc = new MoneyMarket(p, Double.parseDouble(balance.getText()), d);
    		if(accounts.remove(acc)) {
        		messageArea.appendText("Account closed and removed from the database.\n");
    		}
    		else {
    			messageArea.appendText("Account does not exist.\n");
    		}
    	}

    }
    
    /**
     * Print all the accounts
     */
    @FXML
    private void printTheAccounts(ActionEvent event) {
    	messageArea.appendText(accounts.printAccounts());
    }
    
    /**
     * Print accounts by date
     * @param event
     */
    @FXML
    private void printAccountsByDate(ActionEvent event) {
    	messageArea.appendText(accounts.printByDateOpen());
    }
    
    /**
     * Print accounts by last name
     * @param event
     */
    @FXML
    private void printAccountsByLastName(ActionEvent event) {
    	messageArea.appendText(accounts.printByLastName());
    }

    /**
     * imports file into account database
     * 
     * @param event clicking import button on third tab.
     */
    @FXML
    private void importFile(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Open Source File for the Import");
    	chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
    			new ExtensionFilter("All Files", "*.*"));
    	Stage stage = new Stage();
    	File sourceFile = chooser.showOpenDialog(stage);
    	
    	//Code to import process txt file and add accounts accordingly 
    	//all txt files will have a designated format.
    	return;
    }

    /**
     * export accounts from database to txt file
     * @param event clicking export menu button
     */
    @FXML
    private void exportFile(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Open Target File for the Export");
    	chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
    			new ExtensionFilter("All Files", "*.*"));
    	Stage stage = new Stage();
    	File targetFile = chooser.showSaveDialog(stage);
    	//export accounts database to a txt file
    }
    
    /**
     * checks if user input is valid for depositing or withdrawing.
     * @return true if fields are valid, false otherwise.
     */
    private boolean invalidDepositWithdrawInput() {
    	boolean hasError = false;
    	//missing account type
    	if(!rbChecking2.isSelected() && !rbSavings2.isSelected() && !rbMoneyMarket2.isSelected() ) {
    		//output error about input missing for account type 
    		messageArea.appendText("ERROR: Missing account type.\n");
    		hasError = true;
    	}
    	
    	//missing name fields
    	if(fName2.getText().equals("") || lName2.getText().equals("")) {
    		//output error about missing input
    		messageArea.appendText("ERROR: Incomplete name field.\n");
    		hasError = true;
    	}
    	
    	//missing or invalid balance
    	if(!isValidBalance(amount.getText())) {
    		//output error about missing/ improper balance input
    		messageArea.appendText("ERROR: Missing or improper $$$$ Amount.\n");
    		hasError = true;
    	}
    	
    	messageArea.appendText("\n");
    	return hasError;
    }
    
    /**
     * helper that breaks down the deposit by type of account.
     * @param p profile of account to deposit into
     * @param d	date account was opened
     * @return true if deposit is successful, false otherwise.
     */
    private boolean depositHelp(Profile p, Date d) {
    	if(rbChecking2.isSelected()) {
    		Checking acc = new Checking(p, 0 , d, false);
    		return accounts.deposit(acc, Double.parseDouble(amount.getText()));
    	}
    	
    	if(rbSavings2.isSelected()) {
    		Savings acc = new Savings(p, 0, d, false);
    		return accounts.deposit(acc, Double.parseDouble(amount.getText()));
    	}
    	
    	if(rbMoneyMarket2.isSelected()) {
    		MoneyMarket acc = new MoneyMarket(p, 0 , d);
    		return accounts.deposit(acc, Double.parseDouble(amount.getText()));
    	}
    	
    	return false;
    }
    
    /**
     * deposit a given amount in a pre-existing account.
     * @param event deposit radio button is pressed
     */
    @FXML
    private void deposit(ActionEvent event) {
    	
    	if(invalidDepositWithdrawInput()) {
    		return;
    	}
    	
    	Profile p = new Profile(fName2.getText(), lName2.getText());
    	Date d = new Date("09/28/1994");
    	boolean result = depositHelp(p, d);

    	if(result) {
        	messageArea.appendText(String.format("%,.2f", Double.parseDouble(amount.getText()))
        				+ " deposited to account.\n");
        	return;
    	}
    	messageArea.appendText("Account does not exist.\n");
    	return;
    }

    /**
     * helper that breaks down the withdrawals by type of account.
     * @param p profile of account to withdraw from
     * @param d	date account was opened
     * @return -1 if account doesn't exist, 0 if success, 1 if insufficient funds.
     */
    private int withdrawHelp(Profile p, Date d) {
    	 	
    	if(rbChecking2.isSelected()) {
    		Checking acc = new Checking(p, 0 , d, false);
    		return accounts.withdrawal(acc, Double.parseDouble(amount.getText()));	
    	}
    	
    	if(rbSavings2.isSelected()) {
    		Savings acc = new Savings(p, 0, d, false);
    		return accounts.withdrawal(acc, Double.parseDouble(amount.getText()));
        }
    	
    	if(rbMoneyMarket2.isSelected()) {
    		MoneyMarket acc = new MoneyMarket(p, 0 , d);
    		return accounts.withdrawal(acc, Double.parseDouble(amount.getText()));
    	}
    		
    	return -1;
    }
    
    /**
     * withdraw from account input from user.
     * @param event clicking withdraw button
     */
    @FXML
    private void withdraw(ActionEvent event) {

    	if(invalidDepositWithdrawInput()) {
    		return;
    	}
    	
    	Profile p = new Profile(fName2.getText(), lName2.getText());
    	Date d = new Date("09/28/1994");
    	int result = withdrawHelp(p, d);

    	switch(result){
    		case -1:
    			messageArea.appendText("Account does not exist.\n");
    			break;
    		case 0:
            	messageArea.appendText(String.format("%,.2f", Double.parseDouble(amount.getText()))
        				+ " withdrawn from account.\n");    			
            	break;
    		case 1:
            	messageArea.appendText("Insufficient funds.\n");
    			break;
    	}

    }
    
}
