// This version explores hotlink using Identify class, as well as
// a bolt cursor icon, and clicking on a state pops up a window
// showing the state bird.

import javax.swing.*;

import java.io.IOException;
import java.net.URI;
import java.awt.event.*;
import java.awt.*;

import BrowserLaunch.BrowserLauncher;

import com.esri.mo2.ui.bean.*; // beans used: Map,Layer,Toc,TocAdapter,
// TocEvent,Legend(a legend is part of a toc)
import com.esri.mo2.ui.tb.ZoomPanToolBar;
import com.esri.mo2.ui.tb.SelectionToolBar;
import com.esri.mo2.ui.ren.LayerProperties;
import com.esri.mo2.cs.geom.Envelope;
import com.esri.mo2.data.feat.*; //ShapefileFolder, ShapefileWriter
import com.esri.mo2.map.dpy.FeatureLayer;
import com.esri.mo2.map.dpy.Layerset;
import com.esri.mo2.map.draw.*;
import com.esri.mo2.ui.dlg.AboutBox;
import java.util.ArrayList;
import java.util.HashMap;

public class QuickStart9eHotlink extends JFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  static Map map = new Map();
  static boolean fullMap = true; // Map not zoomed
  static boolean helpToolOn;
  // Default English
  public static String TranslatorURL ="http://www.google.com/translate?hl=&sl=pa&tl=en&u=";
  // Default Punjabi
  //public static String TranslatorURL ="http://www.google.com/translate?hl=&sl=en&tl=pa&u=";

  public static String InfohelpUrl = "file:///C:/esri/MOJ20/help/help.html";
  public static String AllhelpUrl = "file:///C:/esri/MOJ20/help/help.html";

  public static String hyperLinkUrl = "https://www.youtube.com/watch?v=v0ei_S2S2H0";
  public static String youTubeUrl = "https://www.youtube.com/watch?v=v0ei_S2S2H0";
  Legend legend;
  Legend legend2;
  Layer layer = new Layer();
  Layer layer2 = new Layer();
  Layer layer3 = null;
  Layer layer5 = new Layer();
  static AcetateLayer acetLayer;
  double distance;
  static com.esri.mo2.map.dpy.Layer layer4;
  com.esri.mo2.map.dpy.Layer activeLayer;
  int activeLayerIndex;
  JMenuBar mbar = new JMenuBar();
  JMenu file = new JMenu("File");
  JMenu theme = new JMenu("Theme");
  JMenu layercontrol = new JMenu("LayerControl");
  JMenu help = new JMenu("Help");
  JMenuItem attribitem = new JMenuItem("open attribute table", new ImageIcon(
      "Pics/tableview.gif"));
  JMenuItem createlayeritem = new JMenuItem("create layer from selection",
      new ImageIcon("Pics/Icon0915b.jpg"));
  static JMenuItem promoteitem = new JMenuItem("promote selected layer",
      new ImageIcon("Pics/promote1.gif"));
  JMenuItem demoteitem = new JMenuItem("demote selected layer",
      new ImageIcon("Pics/demote1.gif"));
  JMenuItem printitem = new JMenuItem("print",
      new ImageIcon("Pics/print.gif"));
  JMenuItem addlyritem = new JMenuItem("add layer", new ImageIcon(
      "Pics/addtheme.gif"));
  JMenuItem remlyritem = new JMenuItem("remove layer", new ImageIcon(
      "Pics/delete.gif"));
  JMenuItem propsitem = new JMenuItem("Legend Editor", new ImageIcon(
      "Pics/properties.gif"));
  JMenu helptopics = new JMenu("Help Topics");
  JMenuItem tocitem = new JMenuItem("Table of Contents", new ImageIcon(
      "Pics/helptopic.gif"));
  JMenuItem legenditem = new JMenuItem("Legend Editor", new ImageIcon(
      "Pics/helptopic.gif"));
  JMenuItem layercontrolitem = new JMenuItem("Layer Control", new ImageIcon(
      "Pics/helptopic.gif"));
  JMenuItem helptoolitem = new JMenuItem("Help Tool", new ImageIcon(
      "Pics/help2.gif"));
  JMenuItem contactitem = new JMenuItem("Contact us");
  JMenuItem aboutitem = new JMenuItem("About MOJO...");
  JMenuItem moreAboutitem = new JMenuItem("Information of Sikhism");
  JMenuItem allAboutitem = new JMenuItem("All other info");

  Toc toc = new Toc();
  String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
  String datapathname = "";
  String legendname = "";
  ZoomPanToolBar zptb = new ZoomPanToolBar();
  static JLabel milesLabel = new JLabel("DISTANCE : 0 Miles");
  static JLabel kmLabel = new JLabel("DISTANCE 0 km");
  static SelectionToolBar stb = new SelectionToolBar();
  JToolBar jtb = new JToolBar();
  ComponentListener complistener;
  JLabel statusLabel = new JLabel("status bar LOC");
  java.text.DecimalFormat df = new java.text.DecimalFormat("0.000");
  JPanel myjp = new JPanel();
  JPanel myjp2 = new JPanel();
  JButton prtjb = new JButton(new ImageIcon("Pics/print.gif"));
  JButton addlyrjb = new JButton(new ImageIcon("Pics/addtheme.gif"));
  JButton ptrjb = new JButton(new ImageIcon("Pics/pointer.gif"));
  JButton XYjb = new JButton("XY");
  JButton hotjb = new JButton(new ImageIcon("Pics/hotlink.gif"));
  JButton helpjb = new JButton(new ImageIcon("Pics/help.gif"));
  JButton distjb = new JButton(new ImageIcon("Pics/measure_1.gif"));
  JButton hyperlink_button = new JButton(new ImageIcon("Pics/hyperlink_icon.gif"));
  JButton youtube_button = new JButton(new ImageIcon("Pics/hyperlink_icon.gif"));


  static HelpTool helpTool = new HelpTool();
  ActionListener lis;
  ActionListener helplis;
  ActionListener layerlis;
  ActionListener layercontrollis;
  TocAdapter mytocadapter;
  Toolkit tk = Toolkit.getDefaultToolkit();
  Image bolt = tk.getImage("Pics/hotlink.gif"); // 16x16 gif file
  Image bolt1=tk.getImage("Pics/hyperlink_icon.gif");
  java.awt.Cursor boltCursor = tk.createCustomCursor(bolt,new java.awt.Point(6, 30), "bolt");
  //java.awt.Cursor boltCursor = tk.createCustomCursor(bolt,new java.awt.Point(6, 30), "bolt");
  MyPickAdapter picklis = new MyPickAdapter();
  PicAdaptorForHyperLink pickHyperLink = new PicAdaptorForHyperLink();
  Identify hotlink = new Identify();
  Identify hyperLink = new Identify();
  static Envelope env;
  static String mystate = null;
  HashMap<String, String> hyperLinkMap = new HashMap();

  class MyPickAdapter implements PickListener { // implements hotlink
    public void beginPick(PickEvent pe) {
    };

    // this fires even when you click outside the states layer
    public void endPick(PickEvent pe) {
    }

    public void foundData(PickEvent pe) {
      com.esri.mo2.data.feat.Cursor c = pe.getCursor();
      Feature f = null;
      if (c != null)
        f = (Feature) c.next();

      mystate = (String) f.getValue(2);
      try {
        HotPick hotpick = new HotPick();// opens dialog window with Duke
        // in it
        hotpick.setVisible(true);
      } catch (Exception e) {
      }
    }
  };

  class PicAdaptorForHyperLink implements PickListener { // implements hotlink
    public void beginPick(PickEvent pe) {
    };

    // this fires even when you click outside the states layer
    public void endPick(PickEvent pe) {
    }

    public void foundData(PickEvent pe) {
      com.esri.mo2.data.feat.Cursor c = pe.getCursor();
      Feature f = null;
      if (c != null)
        f = (Feature) c.next();

      mystate = (String) f.getValue(2);
      try {
        /*HotPick hotpick = new HotPick();// opens dialog window with Duke
        // in it
        hotpick.setVisible(true);*/
        if(Desktop.isDesktopSupported())
        {
          Desktop.getDesktop().browse(new URI(hyperLinkMap.get(mystate)));
        }
      } catch (Exception e) {
      }
    }
  };

  public void PopulateHyperLinkMap()
  {
    hyperLinkMap.put("Alaska", TranslatorURL + "http://www.worldgurudwara.com/V4/556.asp");
    hyperLinkMap.put("San Jose", TranslatorURL + "http://sikhgurudwarasj.org/");
    hyperLinkMap.put("Florida", TranslatorURL + "http://www.gurdwarananaksarflorida.com/");
    hyperLinkMap.put("Chicago", TranslatorURL + "http://www.srsofchicago.com/");
    hyperLinkMap.put("Indianapolis",
        TranslatorURL + "http://indianapolisgurdwara.org/sikh/");
    hyperLinkMap.put("Massachusetts", TranslatorURL + "http://www.gurunanakdarbar.net/");
    hyperLinkMap.put("Michigan", TranslatorURL + "http://www.michigangurudwara.com/");
    hyperLinkMap.put("Louis", TranslatorURL + "http://www.gurdwarastlouis.org/");
    hyperLinkMap.put("Charlotte", TranslatorURL + "http://charlottegurdwara.org/");
    hyperLinkMap.put("Nevada", TranslatorURL + "http://www.worldgurudwara.com/V4/483.asp");
    hyperLinkMap.put("New Jersey", TranslatorURL + "http://www.nnjgurudwara.com/");
    hyperLinkMap.put("New York",
        TranslatorURL + "http://plainview-gurudwara.appspot.com/html/index.html");
    hyperLinkMap.put("Ohio", TranslatorURL + "http://clevelandgurudwara.org/");
    hyperLinkMap.put("Houston", TranslatorURL + "http://www.gurudwarasahibhouston.com/");
    hyperLinkMap.put("Vancouver", TranslatorURL + "http://www.gururamdassgurudwara.org/");

    hyperLinkMap.put("Renton", TranslatorURL + "http://www.singhsabhaseattle.com/");
    hyperLinkMap.put("Bothell", TranslatorURL + "http://www.sikhcentreofseattle.org/");
    hyperLinkMap.put("San Antonio",
        TranslatorURL + "http://www.sikhcenter.us/gur/index.php");
    hyperLinkMap.put("Los Angeles", TranslatorURL + "http://www.lankershimgurdwara.com/");
    hyperLinkMap.put("San Diego", TranslatorURL + "http://www.sdsikhs.org/");
    hyperLinkMap.put("Albuquerque",
        TranslatorURL + "http://www.worldgurudwara.com/V4/301.asp");
    hyperLinkMap
        .put("Espanola",
            TranslatorURL + "http://www.sikhiwiki.org/index.php/Sikh_Dharma_International_Espanola");
    hyperLinkMap.put("Commerce City", TranslatorURL + "http://www.coloradosinghsabha.org/");
    hyperLinkMap.put("Louisville", TranslatorURL + "http://www.louisvillegurdwara.org/");
    hyperLinkMap.put("Minneapolis", TranslatorURL + "http://www.mnsikhs.com/");
    hyperLinkMap.put("Oklahoma City", TranslatorURL + "http://www.okcgurdwara.com/");
    hyperLinkMap.put("Millbourne",
        TranslatorURL + "http://www.worldgurudwara.com/V4/521.asp");
    hyperLinkMap.put("Richmond",
        TranslatorURL + "http://pingakshotechnologies.com/richmond/");
    hyperLinkMap.put("Taylorsville", TranslatorURL + "http://www.sikhtempleofutah.com/");
    hyperLinkMap.put("Salem", TranslatorURL + "http://www.salemgurdwara.com/");
    hyperLinkMap.put("Wisconsin", TranslatorURL + "http://srsofwi.org/");
    hyperLinkMap.put("West Des Moines",
        TranslatorURL + "https://www.sikhinc.org/wordpress/");
    hyperLinkMap.put("Olathe", TranslatorURL + "http://www.kcsikh.org/");
    hyperLinkMap.put("Durham", TranslatorURL + "http://www.sgncweb.org/");
    hyperLinkMap.put("Roswell", TranslatorURL + "http://www.sewageorgia.org/");

  }
  public QuickStart9eHotlink() {
    super("Quick Start");
    PopulateHyperLinkMap();
    helpToolOn = false;
    this.setBounds(150, 150, 900, 650);
    zptb.setMap(map);
    stb.setMap(map);
    setJMenuBar(mbar);
    ActionListener lisZoom = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fullMap = false;
      }
    }; // can change a boolean here
    ActionListener lisFullExt = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        fullMap = true;
      }
    };

    // next line gets a hold of a reference to the zoomin button
    JButton zoomInButton = (JButton) zptb.getActionComponent("ZoomIn");
    JButton zoomFullExtentButton = (JButton) zptb
        .getActionComponent("ZoomToFullExtent");
    JButton zoomToSelectedLayerButton = (JButton) zptb
        .getActionComponent("ZoomToSelectedLayer");
    zoomInButton.addActionListener(lisZoom);
    zoomFullExtentButton.addActionListener(lisFullExt);
    zoomToSelectedLayerButton.addActionListener(lisZoom);
    complistener = new ComponentAdapter() {
      public void componentResized(ComponentEvent ce) {
        if (fullMap) {
          map.setExtent(env);
          map.zoom(1.0); // scale is scale factor in pixels
          map.redraw();
        }
      }
    };
    addComponentListener(complistener);
    lis = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == prtjb || source instanceof JMenuItem) {
          com.esri.mo2.ui.bean.Print mapPrint = new com.esri.mo2.ui.bean.Print();
          mapPrint.setMap(map);
          mapPrint.doPrint();// prints the map
        } else if (source == ptrjb) {
          Arrow arrow = new Arrow();
          map.setSelectedTool(arrow);
        }

        else if (source == distjb) {
          DistanceTool distanceTool = new DistanceTool();
          map.setSelectedTool(distanceTool);
        }

        else if (source == hotjb) {
          hotlink.setCursor(boltCursor);
          map.setSelectedTool(hotlink);
        }

        else if (source == XYjb) {
          try {
            AddXYtheme addXYtheme = new AddXYtheme();
            addXYtheme.setMap(map);
            addXYtheme.setVisible(false);// the file chooser needs a
            // parent
            // but the parent can stay behind the scenes
            map.redraw();
          } catch (IOException e) {
          }
        } else if (source == helpjb) {
          helpToolOn = true;
          map.setSelectedTool(helpTool);
        }
        else if (source == hyperlink_button) {
		          //hyperLink.setCursor(boltCursor);
		          //map.setSelectedTool(hyperLink);
		          BrowserLauncher.openURL(hyperLinkUrl);
        }
        else if (source == youtube_button) {
          BrowserLauncher.openURL(youTubeUrl);
        }
        else {
          try {
            AddLyrDialog aldlg = new AddLyrDialog();
            aldlg.setMap(map);
            aldlg.setVisible(true);
          } catch (IOException e) {
          }
        }
      }
    };

    helplis = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source instanceof JMenuItem) {
          String arg = ae.getActionCommand();

			if(arg == "All other info"){
				BrowserLauncher.openURL(AllhelpUrl);
         	}
			else if(arg == "Information of Sikhism"){
             BrowserLauncher.openURL(InfohelpUrl);
          }

          else if (arg == "About MOJO...") {
            AboutBox aboutbox = new AboutBox();
            aboutbox.setProductName("MOJO");
            aboutbox.setProductVersion("1.0");
            aboutbox.setVisible(true);
          } else if (arg == "Contact us") {
            try {
              String s = "\n\n\n\n    Any enquiries should be addressed to "
                  + "\n\n\n  saumya_sharma511@yahoo.com";
              HelpDialog helpdialog = new HelpDialog(s);
              helpdialog.setVisible(true);
            } catch (IOException e) {
            }
          } else if (arg == "Table of Contents") {
            try {
              HelpDialog helpdialog = new HelpDialog(
                  (String) helpText.get(0));
              helpdialog.setVisible(true);
            } catch (IOException e) {
            }
          } else if (arg == "Legend Editor") {
            try {
              HelpDialog helpdialog = new HelpDialog(
                  (String) helpText.get(1));
              helpdialog.setVisible(true);
            } catch (IOException e) {
            }
          } else if (arg == "Layer Control") {
            try {
              HelpDialog helpdialog = new HelpDialog(
                  (String) helpText.get(2));
              helpdialog.setVisible(true);
            } catch (IOException e) {
            }
          } else if (arg == "Help Tool") {
            try {
              HelpDialog helpdialog = new HelpDialog(
                  (String) helpText.get(3));
              helpdialog.setVisible(true);
            } catch (IOException e) {
            }
          }
        }
      }
    };
    layercontrollis = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        String source = ae.getActionCommand();
        System.out.println(activeLayerIndex + " active index");
        if (source == "promote selected layer")
          map.getLayerset().moveLayer(activeLayerIndex,
              ++activeLayerIndex);
        else
          map.getLayerset().moveLayer(activeLayerIndex,
              --activeLayerIndex);
        enableDisableButtons();
        map.redraw();
      }
    };
    layerlis = new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source instanceof JMenuItem) {
          String arg = ae.getActionCommand();
          if (arg == "add layer") {
            try {
              AddLyrDialog aldlg = new AddLyrDialog();
              aldlg.setMap(map);
              aldlg.setVisible(true);
            } catch (IOException e) {
            }
          } else if (arg == "remove layer") {
            try {
              com.esri.mo2.map.dpy.Layer dpylayer = legend
                  .getLayer();
              map.getLayerset().removeLayer(dpylayer);
              map.redraw();
              remlyritem.setEnabled(false);
              propsitem.setEnabled(false);
              attribitem.setEnabled(false);
              promoteitem.setEnabled(false);
              demoteitem.setEnabled(false);
              stb.setSelectedLayer(null);
              zptb.setSelectedLayer(null);
              stb.setSelectedLayers(null);
            } catch (Exception e) {
            }
          } else if (arg == "Legend Editor") {
            LayerProperties lp = new LayerProperties();
            lp.setLegend(legend);
            lp.setSelectedTabIndex(0);
            lp.setVisible(true);
          } else if (arg == "open attribute table") {
            try {
              layer4 = legend.getLayer();
              AttrTab attrtab = new AttrTab();
              attrtab.setVisible(true);
            } catch (IOException ioe) {
            }
          } else if (arg == "create layer from selection") {
            BaseSimpleRenderer sbr = new BaseSimpleRenderer();
            SimplePolygonSymbol sps = new SimplePolygonSymbol();
            sps.setPaint(AoFillStyle.getPaint(
                AoFillStyle.SOLID_FILL, new java.awt.Color(255,
                    255, 0)));
            sps.setBoundary(true);
            layer4 = legend.getLayer();
            FeatureLayer flayer2 = (FeatureLayer) layer4;
            // select, e.g., Montana and then click the
            // create layer menuitem; next line verifies a selection
            // was made
            System.out.println("has selected"
                + flayer2.hasSelection());
            // next line creates the 'set' of selections
            if (flayer2.hasSelection()) {
              SelectionSet selectset = flayer2.getSelectionSet();
              // next line makes a new feature layer of the
              // selections
              FeatureLayer selectedlayer = flayer2
                  .createSelectionLayer(selectset);
              sbr.setLayer(selectedlayer);
              sbr.setSymbol(sps);
              selectedlayer.setRenderer(sbr);
              Layerset layerset = map.getLayerset();
              // next line places a new visible layer, e.g.
              // Montana, on the map
              layerset.addLayer(selectedlayer);
              // selectedlayer.setVisible(true);
              if (stb.getSelectedLayers() != null)
                promoteitem.setEnabled(true);
              try {
                legend2 = toc.findLegend(selectedlayer);
              } catch (Exception e) {
              }



            }
          }
        }
      }
    };
    toc.setMap(map);
    mytocadapter = new TocAdapter() {
      public void click(TocEvent e) {
        legend = e.getLegend();
        activeLayer = legend.getLayer();
        stb.setSelectedLayer(activeLayer);
        zptb.setSelectedLayer(activeLayer);
        // get active layer index for promote and demote
        activeLayerIndex = map.getLayerset().indexOf(activeLayer);
        // layer indices are in order added, not toc order.
        com.esri.mo2.map.dpy.Layer[] layers = { activeLayer };
        hotlink.setSelectedLayers(layers);// replaces setToc from MOJ10
        remlyritem.setEnabled(true);
        propsitem.setEnabled(true);
        attribitem.setEnabled(true);
        enableDisableButtons();
      }
    };
    map.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent me) {
        com.esri.mo2.cs.geom.Point worldPoint = null;
        if (map.getLayerCount() > 0) {
          worldPoint = map.transformPixelToWorld(me.getX(), me.getY());
          String s = "X:" + df.format(worldPoint.getX()) + " " + "Y:"
              + df.format(worldPoint.getY());
          statusLabel.setText(s);
        } else
          statusLabel.setText("X:0.000 Y:0.000");
      }
    });
    toc.addTocListener(mytocadapter);
    remlyritem.setEnabled(false); // assume no layer initially selected
    propsitem.setEnabled(false);
    attribitem.setEnabled(false);
    promoteitem.setEnabled(false);
    demoteitem.setEnabled(false);
    printitem.addActionListener(lis);
    distjb.addActionListener(lis);
    addlyritem.addActionListener(layerlis);
    remlyritem.addActionListener(layerlis);
    propsitem.addActionListener(layerlis);
    attribitem.addActionListener(layerlis);
    createlayeritem.addActionListener(layerlis);
    promoteitem.addActionListener(layercontrollis);
    demoteitem.addActionListener(layercontrollis);
    distjb.setToolTipText("press-drag-release to measure a distance");

    tocitem.addActionListener(helplis);
    legenditem.addActionListener(helplis);
    layercontrolitem.addActionListener(helplis);
    helptoolitem.addActionListener(helplis);
    contactitem.addActionListener(helplis);
    aboutitem.addActionListener(helplis);
    moreAboutitem.addActionListener(helplis);
    allAboutitem.addActionListener(helplis);

    file.add(addlyritem);
    file.add(printitem);
    file.add(remlyritem);
    file.add(propsitem);
    theme.add(attribitem);
    theme.add(createlayeritem);
    layercontrol.add(promoteitem);
    layercontrol.add(demoteitem);

    help.add(helptopics);
    helptopics.add(tocitem);
    helptopics.add(legenditem);
    helptopics.add(layercontrolitem);
    help.add(helptoolitem);
    help.add(contactitem);
    help.add(aboutitem);
    help.add(moreAboutitem);
    help.add(allAboutitem);

    mbar.add(file);
    mbar.add(theme);
    mbar.add(layercontrol);
    mbar.add(help);
    prtjb.addActionListener(lis);
    prtjb.setToolTipText("print map");
    addlyrjb.addActionListener(lis);
    addlyrjb.setToolTipText("add layer");
    hotlink.addPickListener(picklis);
    hotlink.setPickWidth(5);// sets tolerance for hotlink clicks
    hyperLink.addPickListener(pickHyperLink);
    hyperLink.setPickWidth(5);
    hotjb.addActionListener(lis);
    hotjb.setToolTipText("hotlink tool--click somthing to maybe see a picture");
    hyperlink_button.addActionListener(lis);
    youtube_button.addActionListener(lis);
    hyperlink_button.setToolTipText("Learn More from internet");
    youtube_button.setToolTipText("Watch a video on Youtube");
    ptrjb.addActionListener(lis);
    prtjb.setToolTipText("printer");
    jtb.add(prtjb);
    jtb.add(addlyrjb);
    jtb.add(ptrjb);
    jtb.add(hotjb);
    jtb.add(helpjb);
    jtb.add(distjb);
    jtb.add(hyperlink_button);
    jtb.add(youtube_button);
    helpjb.addActionListener(lis);
    XYjb.addActionListener(lis);
    XYjb.setToolTipText("add a layer of points from a file");
    helpjb.setToolTipText("left click here, then right click on a tool to learn about that tool");
    jtb.add(XYjb);
    jtb.add(helpjb);

    myjp.add(jtb);
    myjp.add(zptb);
    myjp.add(stb);
    myjp2.add(statusLabel);
    myjp2.add(milesLabel);
    myjp2.add(kmLabel);
    getContentPane().add(map, BorderLayout.CENTER);
    getContentPane().add(myjp, BorderLayout.NORTH);
    getContentPane().add(myjp2, BorderLayout.SOUTH);
    addShapefileToMap(layer, s1);
    // addShapefileToMap(layer2, s2);
    // addShapefileToMap(layer5, s5);
    getContentPane().add(toc, BorderLayout.WEST);
  }

  private void addShapefileToMap(Layer layer, String s) {
    String datapath = s; // "C:\\ESRI\\MOJ10\\Samples\\Data\\USA\\States.shp";
    layer.setDataset("0;" + datapath);
    map.add(layer);
  }


  public static void main(String[] args) {

    if(Admin_Page.createAndShowUI())
    {
    QuickStart9eHotlink qstart = new QuickStart9eHotlink();
    qstart.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.out.println("Thanks, Quick Start exits");
        System.exit(0);
      }
    });
    qstart.setVisible(true);
    env = map.getExtent();
    }
  }

  private void enableDisableButtons() {
    @SuppressWarnings("deprecation")
    int layerCount = map.getLayerset().getSize();
    if (layerCount < 2) {
      promoteitem.setEnabled(false);
      demoteitem.setEnabled(false);
    } else if (activeLayerIndex == 0) {
      demoteitem.setEnabled(false);
      promoteitem.setEnabled(true);
    } else if (activeLayerIndex == layerCount - 1) {
      promoteitem.setEnabled(false);
      demoteitem.setEnabled(true);
    } else {
      promoteitem.setEnabled(true);
      demoteitem.setEnabled(true);
    }
  }

  private ArrayList<?> helpText = new ArrayList(3);
}