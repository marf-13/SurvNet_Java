import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


public class GraphFrame extends JFrame{
	
	private BasicVisualizationServer<String, String> visual;
	private JTextField dimensionText;
	private double displayedDiameter;
	
	public GraphFrame(Registry suspReg) {
		
		//Δημιουργία μη-προσανατολισμένου αραιού γράφου.
		Graph<String, String> suspectGraph = new UndirectedSparseGraph<String, String>();		
		
		/*Διατρέχουμε την registry που πήραμε ως παράμετρο για να 
		 * αναθέσουμε στις κορυφές τα κωδικοποιημένα ονόματα των υπόπτων.
		 */
		int num = 0;
		for(Suspect s1: suspReg.allSuspects) {
			suspectGraph.addVertex(s1.getCodeName());
			for(Suspect s2: s1.getPartners()) {
				num++;
				suspectGraph.addEdge("Edge " + num, s1.getCodeName(), s2.getCodeName());
		}}
			
		//Visualization του γράφου
		visual = new VisualizationImageServer<String, String>(new CircleLayout<String, String>(suspectGraph), new Dimension(400, 350));
		//Για την εμφάνιση των ονομάτων των κόμβων
		visual.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>()); 
		this.add(visual);
		
		//Υπολογισμός διαμέτρου και προσθήκη της στο παράθυρο
		displayedDiameter = DistanceStatistics.diameter(suspectGraph);
		dimensionText = new JTextField("Diameter = " + displayedDiameter);
		this.add(dimensionText, BorderLayout.PAGE_END);
		
		//Χρησιμοποιούμε την pack() για να εμφανίζεται ο γράφος σωστά, προσαρμόζοντας τις διαστάσεις του παραθύρου
		this.pack();
		
		//Βασικές ρυθμίσεις παραθύρου
		this.setVisible(true);
		this.setSize(470, 430);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Suspects Network");
		
	}

}
