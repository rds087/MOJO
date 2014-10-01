import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import com.esri.mo2.ui.bean.AcetateLayer;
import com.esri.mo2.ui.tool.MeasureTool;

class DistanceTool extends MeasureTool {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int startx,starty,endx,endy,currx,curry;
com.esri.mo2.cs.geom.Point initPoint, endPoint, currPoint;
double distance;
public void mousePressed(MouseEvent me) {
startx = me.getX(); starty = me.getY();
initPoint = QuickStart9eHotlink.map.transformPixelToWorld(me.getX(),me.getY());
}
public void mouseReleased(MouseEvent me) {

endx = me.getX(); endy = me.getY();
endPoint = QuickStart9eHotlink.map.transformPixelToWorld(me.getX(),me.getY());
distance = (69.44 / (2*Math.PI)) * 360 * Math.acos(
Math.sin(initPoint.y * 2 * Math.PI / 360)
* Math.sin(endPoint.y * 2 * Math.PI / 360)
+ Math.cos(initPoint.y * 2 * Math.PI / 360)
* Math.cos(endPoint.y * 2 * Math.PI / 360)
* (Math.abs(initPoint.x - endPoint.x) < 180 ?
Math.cos((initPoint.x - endPoint.x)*2*Math.PI/360):
Math.cos((360 - Math.abs(initPoint.x -endPoint.x))*2*Math.PI/360)));
System.out.println( distance );
QuickStart9eHotlink.milesLabel.setText("DIST: " + new Float((float)distance).toString() + " miles ");
QuickStart9eHotlink.kmLabel.setText("DIST: " +new Float((float)(distance*1.6093)).toString() + " km ");
if (QuickStart9eHotlink.acetLayer != null)
	QuickStart9eHotlink.map.remove(QuickStart9eHotlink.acetLayer);
QuickStart9eHotlink.acetLayer = new AcetateLayer() {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void paintComponent(java.awt.Graphics g) {
java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
Line2D.Double line = new Line2D.Double(startx,starty,endx,endy);
g2d.setColor(new Color(0,0,250));
g2d.draw(line);
}
};
QuickStart9eHotlink.map.add(QuickStart9eHotlink.acetLayer);
QuickStart9eHotlink.map.redraw();
}
public void cancel() {};
}