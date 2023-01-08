
public class PhoneCall extends Communication{

	private int duration;

	public PhoneCall(String firstNumber, String secNumber, int day, int month, int year, int duration) {
		super(firstNumber, secNumber, day, month, year);
		this.duration = duration;
	}
	
	//Επικάλυψη της printInfo
	public void printInfo() {
		System.out.println("This phone call has the following info");
		System.out.println("Between " + firstNumber + " --- " + secNumber);
		System.out.println("On " + year + "/" + month + "/" + day);
		System.out.println("Duration: " + duration);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
