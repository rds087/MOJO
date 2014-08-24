
INBOX Compose Addresses Folders Options
Current Folder: INBOX 

Welcome: masc1661
Message List Message List  Delete Delete	Previous  Next 	Forward Forward   Forward as Attachment   Reply Reply   Reply All Reply All   
Subject:   	QuickStart7.java
From:   	"Carl Eckberg" <eckberg@rohan.sdsu.edu>
Date:   	Wed, September 19, 2012 9:21 am
To:   	eckberg@rohan.sdsu.edu
Priority:   	Normal
Options:   	View Full Header | View Printable Version  | Download this as a file | Add to Address Book

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import java.io.IOException;
import java.awt.event.*;
import java.awt.*;
import com.esri.mo2.ui.bean.*; // beans used: Map,Layer,Toc,TocAdapter,
        // TocEvent,Legend(a legend is part of a toc)
import com.esri.mo2.ui.tb.ZoomPanToolBar;
import com.esri.mo2.ui.tb.SelectionToolBar;
import com.esri.mo2.ui.ren.LayerProperties;

public class QuickStart7 extends JFrame {
  Map map = new Map();
  Legend legend;
  Layer layer = new Layer();
  Layer layer2 = new Layer();
  JMenuBar mbar = new JMenuBar();
  JMenu file = new JMenu("File");
  JMenuItem printitem = new JMenuItem("print",new ImageIcon("print.gif"));
  JMenuItem addlyritem = new JMenuItem("add layer",new ImageIcon("addtheme.gif"));
  JMenuItem remlyritem = new JMenuItem("remove layer",new ImageIcon("delete.gif"));
  JMenuItem propsitem = new JMenuItem("Legend Editor",new ImageIcon("properties.gif"));
  Toc toc = new Toc();
  String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
  String s2 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\capitals.shp";
  ZoomPanToolBar zptb = new ZoomPanToolBar();
  SelectionToolBar stb = new SelectionToolBar();
  JToolBar jtb = new JToolBar();
  JPanel myjp = new JPanel();
  JButton prtjb = new JButton(new ImageIcon("print.gif"));
  JButton addlyrjb = new JButton(new ImageIcon("addtheme.gif"));
  ActionListener lis;
  ActionListener layerlis;
  ActionListener actlis;
  TocAdapter mytocadapter;

  public QuickStart7() {
    //add a title to the window
    super("Quick Start");
    this.setSize(750, 550);
    zptb.setMap(map);
    stb.setMap(map);
    setJMenuBar(mbar);

    lis = new ActionListener() {public void actionPerformed(ActionEvent ae){
          Object source = ae.getSource();
          if (source == prtjb || source instanceof JMenuItem ) {
         //System.out.println("print button");
        com.esri.mo2.ui.bean.Print mapPrint = new com.esri.mo2.ui.bean.Print();
        mapPrint.setMap(map);
        mapPrint.doPrint();// prints the map
        }
      else {
                try {
              AddLyrDialog aldlg=  new AddLyrDialog();
              aldlg.setMap(map);
              aldlg.setVisible(true);
            } catch(IOException e){}
      }
    }};

    layerlis = new ActionListener() {public void actionPerformed(ActionEvent ae){
          Object source = ae.getSource();
          if (source instanceof JMenuItem) {
                String arg = ae.getActionCommand();
                if(arg == "add layer") {
          try {
                AddLyrDialog aldlg = new AddLyrDialog();
                aldlg.setMap(map);
                aldlg.setVisible(true);
          } catch(IOException e){}
              }
            else if(arg == "remove layer") {
              try {
                        com.esri.mo2.map.dpy.Layer dpylayer =
                          (com.esri.mo2.map.dpy.Layer) legend.getLayer();
                        map.getLayerset().removeLayer(dpylayer);
                        map.redraw();
                        remlyritem.setEnabled(false);
                        propsitem.setEnabled(false);
                        stb.setSelectedLayer(null);
              } catch(Exception e) {}
              }
            else if(arg == "Legend Editor") {
          LayerProperties lp = new LayerProperties();
          lp.setLegend(legend);
          lp.setSelectedTabIndex(0);
          lp.setVisible(true);
            }
      }
    }};
    // the following listener is not currently attached to anything
    actlis = new ActionListener (){public void actionPerformed(ActionEvent ae){
                System.out.println(map.getLayer(1).getName());//this is same
                    // as the name that appears in the table of contents
      }};
    toc.setMap(map);
    // The following is a longcut mandated by an ESRI decision to
    // garbage collect immediately any non-hard references;  it is
    // discussed in the FAQ in MOJ10;   the code may not work without
    // this hard reference to the TocAdapter object!!!!
    mytocadapter = new TocAdapter() {
                public void click(TocEvent e) {
                  System.out.println("aloha");
                  legend = e.getLegend();
                  stb.setSelectedLayer((e.getLegend()).getLayer());
                  remlyritem.setEnabled(true);
                  propsitem.setEnabled(true);
                  }
    };
    toc.addTocListener(mytocadapter);
    remlyritem.setEnabled(false); // assume no layer initially selected
    propsitem.setEnabled(false);
    printitem.addActionListener(lis);
    addlyritem.addActionListener(layerlis);
    remlyritem.addActionListener(layerlis);
    propsitem.addActionListener(layerlis);
    file.add(addlyritem);
    file.add(printitem);
    file.add(remlyritem);
    file.add(propsitem);
    mbar.add(file);
    prtjb.addActionListener(lis);
    prtjb.setToolTipText("print map");
    addlyrjb.addActionListener(lis);
    addlyrjb.setToolTipText("add layer");
    jtb.add(prtjb);
    jtb.add(addlyrjb);
    myjp.add(jtb);
    myjp.add(zptb); myjp.add(stb);
    getContentPane().add(map, BorderLayout.CENTER);
    getContentPane().add(myjp,BorderLayout.NORTH);
    //add a shape file to the map
    addShapefileToMap(layer,s1);
    addShapefileToMap(layer2,s2);
    //stb.selectedLayer = map.getLayer(1);// error: variable is protected
    getContentPane().add(toc, BorderLayout.WEST);
  }
  private void addShapefileToMap(Layer layer,String s) {
    String datapath = s; //"C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
    layer.setDataset("0;"+datapath);
    map.add(layer);
  }

  public static void main(String[] args) {
    QuickStart7 qstart = new QuickStart7();
    qstart.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            System.out.println("Thanks, Quick Start exits");
            System.exit(0);
        }
    });
    qstart.setVisible(true);
  }
}

// following is an Add Layer dialog window
class AddLyrDialog extends JDialog {
  Map map;
  ActionListener lis;
  JButton ok = new JButton("OK");
  JButton cancel = new JButton("Cancel");
  JPanel panel1 = new JPanel();
  com.esri.mo2.ui.bean.CustomDatasetEditor cus = new com.esri.mo2.ui.bean.
    CustomDatasetEditor();
  AddLyrDialog() throws IOException {
        setBounds(50,50,520,430);
        setTitle("Select a theme/layer");
        addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            setVisible(false);
          }
    });

        lis = new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();
            if (source == cancel)
              setVisible(false);
            else {
              try {
                        setVisible(false);
                        map.getLayerset().addLayer(cus.getLayer());
                        map.redraw();
                  } catch(IOException e){}
            }
          }
    };
    ok.addActionListener(lis);
    cancel.addActionListener(lis);
    getContentPane().add(cus,BorderLayout.CENTER);
    panel1.add(ok);
    panel1.add(cancel);
    getContentPane().add(panel1,BorderLayout.SOUTH);
  }
  public void setMap(com.esri.mo2.ui.bean.Map map1){
        map = map1;
  }
}

Delete & Prev |  Delete & Next
Move to:
