import java.util.ArrayList;

public class Registry {
	
	ArrayList<Communication> communications = new ArrayList<>();
	ArrayList<Suspect> allSuspects = new ArrayList<>();
	
	//Μέθοδος που προσθέτει ύποπτο στην λίστα των υπόπτων
	public void addSuspect(Suspect aSuspect) {
		allSuspects.add(aSuspect);
	}
	
	/*Μέθοδος προσθήκης επικοινωνίας στην λίστα επικοινωνιών και 
	 * ταυτόχρονη προσθήκη και των δύο υπόπτων στην λίστα συνεργατών του άλλου
	 */
	public void addCommunication(Communication aCommunication) {
		communications.add(aCommunication);
		
		for(Suspect suspect1: allSuspects) {
			if(suspect1.getPhoneNumbers().contains(aCommunication.firstNumber)) { //Εντοπισμός του πρώτου τηλεφώνου
				for(Suspect suspect2: allSuspects) {
					if(suspect2.getPhoneNumbers().contains(aCommunication.secNumber)) { //Εντοπισμός του δεύτερου
						suspect1.addPossiblePartner(suspect2);
						suspect2.addPossiblePartner(suspect1);
					}
				}
			}
		}
	}
	
	/*Μέθοδος εντοπισμού υπόπτου με τους περισσότερους συνεργάτες. 
	 *Εδώ επιστρέφει τον τελευταίο που έχει την μέγιστη τιμή.*/
	public Suspect getSuspectWithMostPartners() {
		
		Suspect maxSuspect = null;
		int max = 0, currentMax;
		
		for(Suspect suspect: allSuspects) {
			currentMax = suspect.getPartners().size();
			if(currentMax >= max) { //Η συνθήκη currentMax>=max επιτρέπει να βρούμε τον τελευταίο max
				maxSuspect = suspect;
				max = currentMax;
			}
		}
		
		return maxSuspect;
	}
	
	//Μέθοδος που βρίσκει την κλήση μεταξύ δύο αριθμών με την μεγαλύτερη διάρκεια
	public PhoneCall getLongestPhoneCallBetween(String number1, String number2) {
		
		int maxDuration = 0;
		PhoneCall maxCall = null;
		
		for(Communication communication: communications) {
			if(communication instanceof PhoneCall) { //Αν εντοπίσει communication που είναι PhoneCall
				
				//Αφού εντοπιστεί PhoneCall, type casting για να μπορούμε να το χρησιμοποιήσουμε στην συνθήκη
				//και να αξιοποιήσουμε το duration.
				PhoneCall call = (PhoneCall) communication;
				
				if(call.firstNumber.equals(number1) && call.secNumber.equals(number2)) {
					if(call.getDuration()>maxDuration) {
						maxCall = call;
						maxDuration = call.getDuration();
					}
				}
			}
		}
		
		return maxCall;
	}
	
	//Μέθοδος που επιστρέφει λίστα μηνυμάτων μεταξύ δύο αριθμών που περιέχει συγκεκριμένα keywords
	public ArrayList<SMS> getMessagesBetween(String number1, String number2){
		
		ArrayList<SMS> allSuspiciousMessages = new ArrayList<>();
		
		//Πιθανή εναλλακτική λύση: String[] words = {"Bomb", "Attack", "Explosives", "Gun"};
		
		for(Communication communication: communications) {
			if(communication instanceof SMS) { //Αν εντοπιστεί περίπτωση υποκλάσης SMS
				
				SMS textMessage = (SMS) communication; //Μετατροπή typecasting για να χρησιμοποιηθεί η ιδιότητα content
				
				if(textMessage.firstNumber.equals(number1) && textMessage.secNumber.equals(number2)) {
					String content = textMessage.getSmsContent();
					if(content.contains("Bomb")||content.contains("Attack")
							||content.contains("Explosives")||content.contains("Gun")) {
						allSuspiciousMessages.add(textMessage);
					}
				}
			}
		}
		
		return allSuspiciousMessages;
		
	}
	
	//Μέθοδος που εκτυπώνει όλους τους υπόπτους που κατάγονται από μία συγκεκριμένη χώρα
	public void printSuspectsFromCountry(String country) {
		
		System.out.println("Suspects coming from " + country + ":");
		
		for(Suspect s: allSuspects) {
			if(s.getCountryOfOrigin().equals(country)) {
				System.out.println(s.getName() + " (" + s.getCodeName() + ")");
			}
		}
	}

}
