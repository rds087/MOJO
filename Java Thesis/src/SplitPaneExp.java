 import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.esri.mo2.ui.bean.Layer;
import com.esri.mo2.ui.bean.Map;

public class SplitPaneExp extends JFrame {
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
static Map map = new Map();
static boolean fullMap = true; // Map not zoomed
Layer layer = new Layer();

public void addShapefileToMap(Layer layer, String s1) {
	
	String datapath = s1; // "C:\\ESRI\\MOJ10\\Samples\\Data\\USA\\States.shp";
	layer.setDataset("0;" + datapath);
	map.add(layer);
	addShapefileToMap(layer, s1);
}
    
    public SplitPaneExp() {
    	
        
        setTitle("Example of Split Pane");
        setSize(150, 150);
        
        JPanel jsp1 = new JPanel();
        JPanel jsp2 = new JPanel();
        JLabel j1 = new JLabel("USA Map");
        JLabel j2 = new JLabel("India Map");
        
        jsp1.add(j1);
        jsp2.add(j2);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                true, jsp1, jsp2);
        
        splitPane.setOneTouchExpandable(true);
        getContentPane().add(splitPane);
        
    }
    public static void main(String[] args) {
        
        SplitPaneExp sp = new SplitPaneExp();
        sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sp.setVisible(true);
        
    }
    
    
}