import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchFrame extends JFrame{
	
	//1o Βήμα. Δημιουργία υποδοχέα
	private JPanel panel = new JPanel();
	
	//2ο Βήμα. Δημιουργία γραφικών συστατικών
	private JTextField nameSuspect = new JTextField("Please enter suspect's name");
	private JButton searchButton = new JButton("Find");
	
	private Registry localReg;
	private ButtonListener b;
	
	public SearchFrame(Registry reg) {
		
		/*Αναθέτουμε το Registry σε μια τοπική μεταβλητή για να μπορεί να χρησιμοποιηθεί από
		 * το ButtonListener, έτσι ώστε να ανατρέξει την ArrayList με όλους τους υπόπτους.
		 */
		localReg = reg;
		
		
		//3o Βήμα. Τοποθέτηση γραφικών συστατικών στον υποδοχέα
		panel.add(nameSuspect);
		panel.add(searchButton);
		
		//4o Βήμα. Προσαρμογή υποδοχέα (panel) στο παράθυρο
		this.setContentPane(panel);
		
		//Δημιουργία ακροατή ButtonListener και σύνδεση του με το κουμπί αναζήτησης υπόπτου
		b = new ButtonListener();
		searchButton.addActionListener(b);
		
		
		//Βασικές ρυθμίσεις παραθύρου
		this.setVisible(true);
		this.setSize(350, 150);
		this.setTitle("Find Suspect");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			for(Suspect s: localReg.getAllSuspects()) {
				if(s.getName().equals(nameSuspect.getText())) { //Αν βρεθεί ύποπτος με αυτό το όνομα
					//Δημιουργεί νέο παράθυρο όπου στέλνει τον ύποπτο και την βάση Registry για να μπορεί να ανατρέξει πληροφορίες
					new SuspectFrame(s, localReg); 
					SearchFrame.this.dispose(); //Κλείνει το παράθυρο αναζήτησης
					return;
					}
			}
			
			//Αλλιώς, αν δεν έχει βρεθεί ο ύποπτος, συνεχίζει σε αυτή την εντολή, δημιουργώντας warning παράθυρο
			JOptionPane.showMessageDialog(null, "Suspect " + nameSuspect.getText() + " Not Found.");
			
			
			
		}
		
	}

}
