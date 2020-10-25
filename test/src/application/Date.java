package application;
/**
 * Date encapsulates year, month, day integer data fields.
 * @author Joseph Hawkins, Gustavo Garcia
 *
 */
public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;
	
	static int MAX_VALID_YEAR = 9999;
	static int MIN_VALID_YEAR = 1000;
	
	/**
	 * Checks if a year is a leap year
	 * @param year Integer value of the year.
	 * @return true if year is a leap year, false otherwise.
	 */
	static boolean isLeapYear(int year) {
		return (((year % 4 == 0) && 
				 (year % 100 != 0)) ||
				 (year % 400 == 0));
	}

	/**
	 * Constructor class for date
	 * @param year Year account was made
	 * @param month Month account was made
	 * @param day Day account was made.
	 */
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * String constructor
	 * @param date string format of a date mm/dd/yyyy
	 */
	public Date(String date) {
		this.month = Integer.parseInt(date.split("/")[0]);
		this.day = Integer.parseInt(date.split("/")[1]);
		this.year = Integer.parseInt(date.split("/")[2]);
	}
	
	/**
	 * Compares two dates.
	 * @param date This is an object that encapsulates month/day/year 
	 * @return -1 if this.date comes before date, 0 if same date, 1 if this.date comes after date.
	 */
	@Override
	public int compareTo(Date date) {
		
		//different years
		if ( this.year < date.year ) {
			return -1;
		}
		else if(this.year > date.year) {
			return 1;
		}
		
		//different months same year
		if(this.month < date.month) {
			return -1;
		}
		else if(this.month > date.month) {
			return 1;
		}
		
		//different day same month and year
		if(this.day < date.day) {
			return -1;
		}
		else if(this.day > date.day) {
			return 1;
		}
		
		//same month/day/year
		if ( (this.year == date.year) && (this.month == date.month) && (this.day == date.day) ) {
			return 0;
		}
		
		//something is wrong
		return 2;
	}
	
	/**
	 * Gives a string version of the date.
	 * @return str in format mm/dd/yyyy
	 */
	@Override
	public String toString() {
		return this.month + "/" + this.day + "/" + this.year;
	}
	
	/**
	 * Checks if a date is valid.
	 * out of range only i think
	 * @return true if date is valid, false otherwise.
	 */
	public boolean isValid() {
		
		if(year > MAX_VALID_YEAR || 
			year < MIN_VALID_YEAR) {
			return false;
		}
		
		if(month > 12 || month < 1) {
			return false;
		}
		
		if(day > 31 || day < 1) {
			return false;
		}
		
		//Handle February with and without leap year.
		if(month == 2) {
			if(isLeapYear(year)) {
				return (day <= 29);
			}
			else {
				return (day <= 28);
			}
		}
		
		if(month ==  4 || month == 6 || month == 9 || month == 11) {
			return (day <= 30);
		}
		return true;
	}
	
	/**
	 * Checks if a date is equal to another.
	 * @param obj is the date to be compared.
	 * @return true if the dates are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Date) {
			Date date = (Date) obj;
			
			if( (this.year == date.year) &&
				(this.month == date.month) &&
				(this.day == date.day)) {
				return true;
				
			}
		
		}
		
		return false;		
	}
	
	public static void main (String[] args) {
		Date date1 = new Date(2001, 3, 4);
		Date date2 = new Date(2001, 3, 4);
		Date date3 = new Date(2001, 3, 4);

		System.out.println(date1.equals(date2));
		System.out.println(date2.equals(date1));

		System.out.println(date1.equals(date3));
		System.out.println(date3.equals(date2));
		System.out.println(date3.equals(date3));
		
		Date date4 = new Date("1/02/1995");
		System.out.println(date4.toString());


		
	}
}