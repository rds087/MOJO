import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

class HotPick extends JDialog {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String mystate = QuickStart9eHotlink.mystate;
String gurudwara = null;
String myGurudwaraPic = null;
JPanel jpanel = new JPanel();
JPanel jpanel2 = new JPanel();
String[][] stateTemples = {

			    {"Alaska","The Sikh Society of Gurudwara","Pics/alaska.jpg"},
				{"Arizona","Arizona Sikh Gurudwara","Pics/arizona.jpg"},
				{"San Jose","Gurudwara Sahib Of San Jose","Pics/california.jpg"},
				{"Florida","Gurdwara Nanaksar Florida","Pics/florida.jpg"},
				{"Chicago"," Gurdwara Sahib of Chicago","Pics/IIinous.jpg"},
				{"Indianapolis","Sikh Satsang of Indianapolis","Pics/indiana.jpg"},
				{"Maryland","Guru Nanak Foundation of America","Pics/maryland.jpg"},
				{"Massachusetts","Gurudwara Guru Nanak Darbar","Pics/massachusetts.jpg"},
				{"Michigan","Sikh Society of Michigan","Pics/michigan.jpg"},
				{"Louis","Sikh Study Circle","Pics/missouri.jpg"},
				{"Charlotte","Gurudwara Charlotte","Pics/carolina.jpg"},
				{"Nevada","Reno Sikh Temple","Pics/nevada.jpg"},
				{"New Jersey","Nanak Naam Jahaj Gurudwara","Pics/jersey.jpg"},
				{"New York","Guru Gobind Singh Sikh Center ","Pics/NewYork.jpg"},
				{"Ohio","Gurudwara Sahib Bedford","Pics/ohio.jpg"},
				{"Houston","Gurudwara Sahib Houston","Pics/texas.jpg"},
				{"Vancouver","Guru Ramdass Gurdwara Sahib","Pics/van.jpg"},
				
				{"Renton","Gurudwara Singh Sabha Of Washington","Pics/renton.jpg"},
				{"Bothell","Gurudwara Sikh Centre of Seattle","Pics/seattle.jpg"},
				{"Austin","Gurudwara Sahib Austin","Pics/austin.jpg"},
				{"San Antonio","Sikh Center of San Antonio","Pics/san.jpg"},
				{"Los Angeles","Sikh Gurdwara of Los Angeles","Pics/los_angeles.jpg"},
				{"San Diego","Sikh Foundation San Diego","Pics/sandiego.jpg"},
				{"Albuquerque","Albuquerque Sikh Gurudwara","Pics/albe.jpg"},
				{"Espanola","Sikh Dharma International Espanola","Pics/esplan.jpg"},
				{"Commerce City","Colorado Singh Sabha","Pics/colorado.jpg"},
				{"Louisville","Gurdwara Sahib of Louisville","Pics/Esplano.jpg"},
				{"Minneapolis","Sikh Society of Minnesota","Pics/minosita.jpg"},
				{"Oklahoma City","Sikh Gurdwara of Oklahoma","Pics/okha.jpg"},
				{"Millbourne","Philadelphia Sikh Society","Pics/phil.jpg"},
				{"Richmond","Richmond Gurdwara","Pics/richmond.jpg"},
				{"Taylorsville","Sikh Temple of Utah","Pics/uath.jpg"},
				{"Salem","Dashmesh Darbar Sikh Temple","Pics/darbar.jpg"},
				{"Wisconsin","Sikh Religious Society of Wisconsin","Pics/wis.jpg"},
				{"West Des Moines","Iowa Sikh Temple","Pics/lowa.jpg"},
				{"Olathe","Gurdwara Nanak Darbar Sahib","Pics/olathe.jpg"},
				{"Durham","The Sikh Gurudwara of North Carolina","Pics/durham.jpg"},
				{"Roswell","Sewa Gurudwara Sahib","Pics/roswell.png"},
				};
HotPick() throws IOException {
    setTitle("This was your pick");
setBounds(50,50,400,400);
addWindowListener(new WindowAdapter() {
  public void windowClosing(WindowEvent e) {
        setVisible(false);
      }
});
for (int i = 0;i<51;i++)  {
    if (stateTemples[i][0].equals(mystate)) {
  gurudwara = stateTemples[i][1];
      myGurudwaraPic = stateTemples[i][2];
      break;
    }
}
JLabel label = new JLabel(mystate+":   ");
JLabel label2 = new JLabel(gurudwara);
ImageIcon birdIcon = new ImageIcon(myGurudwaraPic);
JLabel birdLabel = new JLabel(birdIcon);
jpanel2.add(birdLabel);
jpanel.add(label);
jpanel.add(label2);
getContentPane().add(jpanel2,BorderLayout.CENTER);
getContentPane().add(jpanel,BorderLayout.SOUTH);
}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
	    if (path != null) {
	        return new ImageIcon(path);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
	
}