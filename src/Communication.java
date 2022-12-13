
public abstract class Communication {
	
	protected String firstNumber;
	protected String secNumber;
	protected int day;
	protected int month;
	protected int year;
	
	
	public Communication(String firstNumber, String secNumber, int day, int month, int year) {
		this.firstNumber = firstNumber;
		this.secNumber = secNumber;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public abstract void printInfo();
	
	/*Η συγκεκριμένη κλάση είναι abstract και υλοποιεί abstract μεθόδους
	 * διότι θα υπάρχει επικάλυψη της printInfo και θα καλεστεί μόνο από 
	 * υποκλάσεις της Communication.
	 */
	

}
