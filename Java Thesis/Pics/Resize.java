import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
import com.esri.mo.ui.bean.Map;
import com.esri.mo.ui.tb.ZoomPanToolBar;
import com.esri.mo.ui.tb.SelectionToolBar;
import com.esri.mo.ui.bean.*;
import com.esri.mo.cs.geom.InvalidValuesException;
public class Resize extends JFrame {
  Map map = new Map();
  Layer layer = new Layer();
  Layer layer2 = new Layer();
  Toc toc = new Toc();
  String s1 = "C:\\ESRI\\MOJ10\\Samples\\Data\\USA\\States.shp";
  String s2 = "C:\\ESRI\\MOJ10\\Samples\\Data\\USA\\capitals.shp";
  ZoomPanToolBar zptb = new ZoomPanToolBar();
  SelectionToolBar stb = new SelectionToolBar();
  JPanel myjp = new JPanel();
  TocAdapter mytocadapter;
  ComponentListener complistener;
  public Resize() throws ParseException {
    super("Quick Start");
    //set the size
    this.setSize(550, 340);
    zptb.setMap(map);
    stb.setMap(map);
    complistener = new ComponentAdapter () {
          public void componentResized(ComponentEvent ce) {
            Dimension d = ce.getComponent().getSize();
            System.out.println(" map extent is " + map.getMapExtent());
            System.out.println("frame size is " + d.width + "  " + d.height);
            //System.out.println("frame loc is " + getX() + " " + getY());
            System.out.println("map size in pixels " + map.getWidth() + " " +
                                   map.getHeight());
            System.out.println("map loc is " + map.getX() + " " + map.getY());
            map.redraw();

          }
    };
    addComponentListener(complistener);
    toc.setMap(map);
    mytocadapter = new TocAdapter() {
          public void click(TocEvent e) {
            System.out.println("aloha");
            stb.setSelectedLayer((e.getLegend()).getLayer());
            stb.setSelectedLayer(map.getLayer(2));
      }
    };
    toc.addTocListener(mytocadapter);
    myjp.add(zptb); myjp.add(stb);
    getContentPane().add(map, BorderLayout.CENTER);
    getContentPane().add(myjp,BorderLayout.NORTH);
    addShapefileToMap(layer,s1);
    addShapefileToMap(layer2,s2);
    stb.setSelectedLayer(map.getLayer(0));
    getContentPane().add(toc, BorderLayout.WEST);
  }
    private void addShapefileToMap(Layer layer,String s) {
    String datapath = s; //"C:\\ESRI\\MOJ10\\Samples\\Data\\USA\\States.shp";
    layer.setDataset("0;"+datapath);
    map.add(layer);
  }
  public static void main(String[] args) throws ParseException {
    Resize qstart = new Resize();
    qstart.setIconImage(com.esri.mo.util.Resource.getIcon("print.gif"));
    qstart.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.out.println("Thanks, Quick Start exits");
        System.exit(0);
      }
    });
    qstart.setVisible(true);
  }
}