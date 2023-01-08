import java.util.ArrayList;

public class Suspect {
	
	private String name;
	private String codeName;
	private String countryOfOrigin;
	private String activeCity;
	private ArrayList<String> phoneNumbers = new ArrayList<>();
	private ArrayList<Suspect> partners = new ArrayList<>();
	
	public Suspect(String name, String codeName, String countryOfOrigin, String activeCity) {
		this.name = name;
		this.codeName = codeName;
		this.countryOfOrigin = countryOfOrigin;
		this.activeCity = activeCity;
	}
	
	//Μέθοδος που προσθέτει έναν αριθμό στην λίστα των τηλεφώνων ενός υπόπτου
	public void addNumber(String number) {
		phoneNumbers.add(number);
	}
	
	//Μέθοδος που προσθέτει πιθανό συνεργάτη στην λίστα συνεργατών ενός υπόπτου
	public void addPossiblePartner(Suspect aSuspect) {
		if(!isConnectedTo(aSuspect)) partners.add(aSuspect);
	}
	
	//Μέθοδος που ελέγχει αν δύο ύποπτοι είναι συνδεδεμένοι
	public boolean isConnectedTo(Suspect aSuspect) {
		return partners.contains(aSuspect);
	}
	
	//Μέθοδος που βρίσκει τους κοινούς συνεργάτες δύο υπόπτων.
	public ArrayList<Suspect> getCommonPartners(Suspect aSuspect){
		
		ArrayList<Suspect> commonPartners = new ArrayList<>();
		/*Διατρέχουμε τις λίστες συνεργατών των δύο υπόπτων με εμφωλευμένη for
		 * Αν εντοπιστεί κάποιος συνεργάτης που είναι κοινός τον προσθέτουμε στην
		 * λίστα.
		 */
		for(Suspect suspectOriginal: partners) {
			for(Suspect suspectInput: aSuspect.getPartners()) {
				if(suspectOriginal.equals(suspectInput)) commonPartners.add(suspectOriginal);
			}
		}
		return commonPartners;
	}
	
	//Μέθοδος που εκτυπώνει τους συνεργάτες, προσθέτοντας το σύμβολο * αν είναι από την ίδια χώρα
	public void printPossiblePartners() {
		System.out.println("Possible Partners:");
		for(Suspect suspect: partners) {
			//Χρήση ternary operator μέσα στην println αντί για if, για την εμφάνιση του αστερίσκου
			System.out.println("Name: " + suspect.getName() + ", Codename: " + suspect.getCodeName()
			+ ((countryOfOrigin.equals(suspect.getCountryOfOrigin())) ? " *" : "" ));
		}
	}

	//Getters και setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getActiveCity() {
		return activeCity;
	}

	public void setActiveCity(String activeCity) {
		this.activeCity = activeCity;
	}

	public ArrayList<Suspect> getPartners() {
		return partners;
	}

	public void setPartners(ArrayList<Suspect> partners) {
		this.partners = partners;
	}

	public ArrayList<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	

	
	

}
