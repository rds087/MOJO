import com.esri.mo2.ui.bean.Tool;
class Arrow extends Tool {

private static final long serialVersionUID = 1L;

Arrow() { // undo measure tool residue
QuickStart9eHotlink.milesLabel.setText("DISTANCE 0 Miles");
QuickStart9eHotlink.kmLabel.setText("DISTANCE 0 KM");
QuickStart9eHotlink.map.remove(QuickStart9eHotlink.acetLayer);
QuickStart9eHotlink.acetLayer = null;
QuickStart9eHotlink.map.repaint();
}

}