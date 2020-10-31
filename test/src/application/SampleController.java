package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;


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
    private TextField Amount;
    
    
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
    private Button withdrawButton;

    @FXML
    private Button depositButton;
    
    /**
     * clear account creation fields and reset all buttons/check boxes.
     * @param event clicking the clear button.
     */
    @FXML
    void clearTabOne(ActionEvent event) {
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
    void checkingRbClicked(MouseEvent event) {
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
    void savingsRbClicked(MouseEvent event) {
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
    void moneyMarketRbClicked(MouseEvent event) {
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
    	
    	return hasError;
    }
    
    
    /**
     * Open an account from user input.
     * @param event clicking open account button.
     */
    @FXML
    void openAccount(ActionEvent event) {
    	
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
    void closeAccount(ActionEvent event) {
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

}
