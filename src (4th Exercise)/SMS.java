
public class SMS extends Communication{
	
	private String smsContent;

	public SMS(String firstNumber, String secNumber, int day, int month, int year, String smsContent) {
		super(firstNumber, secNumber, day, month, year);
		this.smsContent = smsContent;
	}
	
	//Επικάλυψη της printInfo
	public void printInfo() {
		System.out.println("This SMS has the following info");
		System.out.println("Between " + firstNumber + " --- " + secNumber);
		System.out.println("On " + year + "/" + month + "/" + day);
		System.out.println("Text: " + smsContent);
		
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	

}
