import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public  class Main
{
	public static void main(String[] args) {

      if(Admin_Page.createAndShowUI()) // Login successfull
      {
    	  System.out.println("LOGIN !");
		QuickStart9eHotlink qstart = new QuickStart9eHotlink();
		qstart.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("Thanks, Quick Start exits");
				System.exit(0);
			}
		});
		qstart.setVisible(true);
		//env = map.getExtent();
		}
	}
}