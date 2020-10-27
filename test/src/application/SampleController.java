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
	 * 
	 * 
	 */
    
	private AccountDatabase accounts = new AccountDatabase();
	
	
	@FXML
    private TextField fName;

   
	@FXML
    private TextField lName;

   
    @FXML
    private TextField balance;

    
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
    private CheckBox directDeposit;

    @FXML
    private CheckBox isLoyal;
    
    @FXML
    private RadioButton rbMoneyMarket;
    
    @FXML
    private Button openAccountButton;


    /**
     * Runs main structure.
     */
    void run() {
    	
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
    private boolean validAccountInput() {
    	//check for missing user input.
    	
    	//missing account type
    	if(!rbChecking.isSelected() && !rbSavings.isSelected() && !rbMoneyMarket.isSelected() ) {
    		//output error about input missing for account type 
    		return false;
    	}
    	
    	//missing name fields
    	if(fName.getText().equals("") || lName.getText().equals("")) {
    		//output error about missing input
    		return false; 
    	}
    	
    	//missing or invalid balance
    	if(!isValidBalance(balance.getText())) {
    		//output error about missing/ improper balance input
    		return false;
    	}
    	
    	//missing or invalid date
    	if(!isValidDate(year.getText(), month.getText(), day.getText())) {
    		return false;
    	}
    	
    	return true;
    }
    
    
    /**
     * Open an account from user input.
     * @param event clicking open account button.
     */
    @FXML
    void openAccount(ActionEvent event) {
    	
    	if(!validAccountInput()) return;
    	
    	Profile p = new Profile(fName.getText(), lName.getText());
    	
    	Date d = new Date(Integer.parseInt(year.getText()), 
    			Integer.parseInt(month.getText()), 
    			Integer.parseInt(day.getText()));
    	
    	if(rbChecking.isSelected()) {
    		Checking acc = new Checking(p, Double.parseDouble(balance.getText()), 
    									d, directDeposit.isSelected());
    		messageArea.setText(acc.toString());
    	}
    	
    	if(rbSavings.isSelected()) {
    		Savings acc = new Savings(p, Double.parseDouble(balance.getText()), 
    									d, isLoyal.isSelected());
    		messageArea.setText(acc.toString());
    	}
    	
    	if(rbMoneyMarket.isSelected()) {
    		MoneyMarket acc = new MoneyMarket(p, Double.parseDouble(balance.getText()), d);
    		messageArea.setText(acc.toString());
    	}
    	
    	/**
    	 * when program starts initialize an account database
    	 * 
    	 * CHECK FOR VALID INPUTS
    	 * take in name, make profile
    	 * take in date, make date
    	 * take in balance, make
    	 * when account button is clicked make account and add to database
    	 */
    }

}
