import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SuspectFrame extends JFrame{
	
	private Registry localReg;
	private Suspect currentSuspect;
	
	private JPanel completePanel = new JPanel();
	
	private JTextField suspName, suspCodeName, numberEntry;
	private JTextArea numberField, smsField, partnerField, sugPartnerField, sameCountry;

	private JButton findSMS = new JButton("Find SMS"),
			returnButton = new JButton("Return to Search Screen");
	
	//Μέθοδος για την δημιουργία απλού panel που μπορεί να περιέχει ένα label και ένα JTextArea component
	private JPanel quickPanelCreator(String title, JTextArea t) {
		JPanel panel = new JPanel(); //Δημιουργεί panel
		if(!title.isBlank()) panel.add(new JLabel(title)); //Αν δεν υπάρχει τίτλος, δεν δημιουργεί label
		t.setBorder(BorderFactory.createLineBorder(Color.gray)); //Μορφοποιεί το JTextArea
		panel.add(t);
		panel.setBorder(BorderFactory.createLineBorder(Color.gray)); //Μορφοποιεί και το panel
		return panel;
		
	}
	
	public SuspectFrame(Suspect s, Registry reg) {

		/*Αναθέτουμε τις πληροφορίες των παραμέτρων σε τοπικές αναφορές για να μπορούν
		 * να χρησιμοποιηθούν από τον SMSButtonListener και τον ReturnButtonListener.
		 */
		localReg = reg;
		currentSuspect = s;
		
		//Δημιουργία panel (και γραφ. συστατικών) που περιέχει τις πληροφορίες του υπόπτου που αναζητήσαμε (infoPanel)
		
		JPanel infoPanel = new JPanel();
		suspName = new JTextField(s.getName());
		suspCodeName = new JTextField(s.getCodeName());
		numberField = new JTextArea(3, 10);
		
		for(String phoneNumber : s.getPhoneNumbers()) numberField.append(phoneNumber + "\n");
		
		//Μορφοποίηση των περιεχομένων και του ίδιου του infoPanel
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		suspCodeName.setPreferredSize(new Dimension(100, 20));
		suspName.setPreferredSize(new Dimension(100, 20));
		numberField.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		infoPanel.add(suspName);
		infoPanel.add(suspCodeName);
		infoPanel.add(numberField);
		
		completePanel.add(infoPanel); //Προσθήκη panel στο τελικό panel
		
		/*Δημιουργία panel (και γραφ. συστατικών) που επιτρέπει την αναζήτηση όλων των SMS μεταξύ του τηλεφωνου
		 * που γράφουμε στο text field και όλων των τηλεφωνικών αριθμών του υπόπτου που 
		 * αναζητήσαμε (και βρέθηκε) στο SearchFrame. (smsPanel)
		 */
		
		JPanel smsPanel = new JPanel();
		numberEntry = new JTextField(12);
		smsField = new JTextArea(9, 20);
		
		smsPanel.add(numberEntry);
		smsPanel.add(smsField);
		smsPanel.add(findSMS);
		
		//Δημιουργία ακροατή και σύνδεση με το κουμπί εύρεσης SMS.
		SMSButtonListener smsListener = new SMSButtonListener();
		findSMS.addActionListener(smsListener);
		
		//Μορφοποίηση περιεχομένων και του ίδιου του smsPanel
		smsField.setBorder(BorderFactory.createLineBorder(Color.gray));
		smsPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		completePanel.add(smsPanel); //Προσθήκη smsPanel στο τελικό panel
		
		/*Δημιουργία panel (μέσω της μεθόδου quickPanelCreator) που δείχνει τους
		 * partners του υπόπτου που αναζητήσαμε στο SearchFrame.
		 */
		
		partnerField = new JTextArea(9, 20);
		ArrayList<Suspect> alphabeticalPartners = s.getPartners();
		
		/* Σύμφωνα με το στιγμιότυπο, απαιτείται η εμφάνιση των partners αλφαβητικά.
		 * Άρα, κάνουμε override την ήδη υπάρχουσα μέθοδο sort, δημιουργώντας
		 * έναν custom comparator που λειτουργεί για αντικείμενα Suspect,
		 * για να μπορέσουμε να ταξινομήσουμε τους partners έχοντας κριτήριο το όνομα 
		 * και κατατάσσοντας τους σε αλφαβητική σειρά.
		 */
		Collections.sort(alphabeticalPartners, new Comparator<Suspect>(){
			public int compare(Suspect s1, Suspect s2) {
				return s1.getName().compareTo(s2.getName());
			}
			
		});
		
		for(Suspect p: alphabeticalPartners) partnerField.append(p.getName() + ", " + p.getCodeName() + "\n");
		completePanel.add(quickPanelCreator("Partners", partnerField));
		
		/*Δημιουργία panel (μέσω της μεθόδου quickPanelCreator) που περιέχει τους πιθανούς συνεργάτες 
		 * του υπόπτου που αναζητήσαμε και βρήκαμε στο SearchFrame.
		 */
		
		sugPartnerField = new JTextArea(5, 20);
		for(Suspect sp: s.getSuggestedPartners()) sugPartnerField.append(sp.getName() + "\n");
		completePanel.add(quickPanelCreator("Suggested Partners ----->", sugPartnerField));
		
		/*Δημιουργία panel (μέσω της μεθόδου quickPanelCreator) που δείχνει όλους τους υπόπτους
		 * που κατάγονται από την ίδια χώρα που κατάγεται και ο ύποπτος που αναζητήσαμε στο SearchFrame.
		 * (Στο αποτέλεσμα συμπεριλαμβάνεται και ο ύποπτος που αναζητήσαμε).
		 */
		
		sameCountry = new JTextArea(5, 30);
		
		sameCountry.append("Suspects coming from " + currentSuspect.getCountryOfOrigin() + "\n");
		for(Suspect susp: localReg.allSuspects)
			if(susp.getCountryOfOrigin().equals(currentSuspect.getCountryOfOrigin()))
				sameCountry.append(susp.getName() + "\n");
		
		completePanel.add(quickPanelCreator("", sameCountry));
		
		//Δημιουργία ακροατή επιστροφής, σύνδεση με κουμπί return και προσθήκη του κουμπιού στο τελικό panel
		ReturnButtonListener returnListen = new ReturnButtonListener();
		returnButton.addActionListener(returnListen);
		completePanel.add(returnButton);
		
		//Προσθήκη τελικού panel στο SuspectFrame
		this.setContentPane(completePanel);
		
		//Βασικές ρυθμίσεις παραθύρου
		this.setVisible(true);
		this.setSize(470, 680);
		this.setTitle("Suspect Page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
	class SMSButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			smsField.setText(null); //Εκκαθάριση των προηγούμενων περιεχομένων του smsField κάθε φορά που γίνεται αναζήτηση 
			
			ArrayList<SMS> messages = new ArrayList<>();
			
			for(String currentNumber: currentSuspect.getPhoneNumbers())
				messages.addAll(localReg.getMessagesBetween(currentNumber, numberEntry.getText()));
				
			for(SMS m: messages) smsField.append(m.getSmsContent() + "\n");
		}
		
	}
	
	class ReturnButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//Όταν πατηθεί το return button, επιστρέφει στο SearchFrame και εξαφανίζει το SuspectFrame
			SuspectFrame.this.dispose();
			new SearchFrame(localReg);
		}
		
	}

}
