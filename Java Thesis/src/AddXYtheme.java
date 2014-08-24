import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import com.esri.mo2.cs.geom.BasePointsArray;
import com.esri.mo2.map.dpy.FeatureLayer;
import com.esri.mo2.ui.bean.Map;

class AddXYtheme extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map map;
	Vector<String> s2 = new Vector<String>();
	Vector<String> s3 = new Vector<String>();
	Vector<String> s4 = new Vector<String>();

	static String CityName[] = new String[100];
	static String StataName[] = new String[100];
	static String Population[] = new String[100];
	//static String GurudwaraDescription[] = new String[170];

	JFileChooser jfc = new JFileChooser();
	BasePointsArray bpa = new BasePointsArray();
	FeatureLayer XYlayer;
	private BufferedReader in;

	AddXYtheme() throws IOException {
		setBounds(50, 50, 520, 430);
		jfc.showOpenDialog(this);
		try {

			File file = jfc.getSelectedFile();
			FileReader fred = new FileReader(file);
			in = new BufferedReader(fred);
			String s; // = in.readLine();
			double x, y;
			int i = 0;
			String fetchString;
			while ((s = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(s, ",");
				x = Double.parseDouble(st.nextToken());
				y = Double.parseDouble(st.nextToken());

				fetchString = st.nextToken();
				CityName[i] = fetchString;
				 System.out.println("City Name : " +
						 CityName[i]);
				s2.addElement(CityName[i]);

				fetchString = st.nextToken();
				StataName[i] = fetchString;
				System.out.println("State Name : " + StataName[i]);
				s3.addElement(StataName[i]);

				fetchString = st.nextToken();
				Population[i] = fetchString;
			    System.out.println("Population " + Population[i]);
				s4.addElement(Population[i]);

				bpa.insertPoint(i, new com.esri.mo2.cs.geom.Point(x, y));
				i++;

			}
		} catch (IOException e) {
		} catch (NullPointerException e) {

		}
		XYfeatureLayer xyfl = new XYfeatureLayer(bpa, map, s2, s3, s4);
		XYlayer = xyfl;
		xyfl.setVisible(true);
		map = QuickStart9eHotlink.map;
		map.getLayerset().addLayer(xyfl);
		map.redraw();
		CreateXYShapeDialog xydialog = new CreateXYShapeDialog(XYlayer);
		xydialog.setVisible(true);
	}

	public void setMap(com.esri.mo2.ui.bean.Map map1) {
		map = map1;
	}
}
