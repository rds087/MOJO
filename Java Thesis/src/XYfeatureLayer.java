import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import com.esri.mo2.cs.geom.BasePointsArray;
import com.esri.mo2.data.feat.BaseDataID;
import com.esri.mo2.data.feat.BaseFeature;
import com.esri.mo2.data.feat.BaseFeatureClass;
import com.esri.mo2.data.feat.BaseField;
import com.esri.mo2.data.feat.BaseFields;
import com.esri.mo2.data.feat.Field;
import com.esri.mo2.data.feat.MapDataset;
import com.esri.mo2.map.dpy.BaseFeatureLayer;
import com.esri.mo2.map.dpy.LayerCapabilities;
import com.esri.mo2.map.draw.BaseSimpleRenderer;
import com.esri.mo2.map.draw.TrueTypeMarkerSymbol;
import com.esri.mo2.ui.bean.Map;

class XYfeatureLayer extends BaseFeatureLayer {
	BaseFields fields;
	private java.util.Vector<BaseFeature> featureVector;

	public XYfeatureLayer(BasePointsArray bpa, Map map, Vector s2, Vector s3,
			Vector<?> s4) {
		createFeaturesAndFields(bpa, map, s2, s3, s4);

		BaseFeatureClass bfc = getFeatureClass("Gurudwara", bpa);
		setFeatureClass(bfc);
		BaseSimpleRenderer srd = new BaseSimpleRenderer();
		
		TrueTypeMarkerSymbol ttm = new TrueTypeMarkerSymbol();

		ttm.setFont(new Font("ESRI North", Font.PLAIN, 20));
		// sms.setType(SimpleMarkerSymbol.SQUARE_MARKER);
		ttm.setColor(new Color(255, 0, 0));
		// sms.setSymbolColor(new Color(255,255,0));
		// sms.setWidth(7);
		ttm.setCharacter("97");
		srd.setSymbol(ttm);

		setRenderer(srd);
		XYLayerCapabilities lc = new XYLayerCapabilities();
		setCapabilities(lc);
	}

	private void createFeaturesAndFields(BasePointsArray bpa, Map map,
			Vector s2, Vector<?> s3, Vector<?> s4) {
		featureVector = new java.util.Vector<BaseFeature>();
		fields = new BaseFields();
		createDbfFields();
		for (int i = 0; i < bpa.size(); i++) {
			BaseFeature feature = new BaseFeature(); // feature is a row
			feature.setFields(fields);
			com.esri.mo2.cs.geom.Point p = new com.esri.mo2.cs.geom.Point(
					bpa.getPoint(i));
			feature.setValue(0, p);
			feature.setValue(1, new Integer(0)); // point data
			feature.setValue(2, (String) s2.elementAt(i));
			feature.setValue(3, (String) s3.elementAt(i));
			feature.setValue(4, (String) s4.elementAt(i));
			feature.setDataID(new BaseDataID("List of Gurudwaras", i));
			featureVector.addElement(feature);
		}
	}

	private void createDbfFields() {
		fields.addField(new BaseField("#SHAPE#", Field.ESRI_SHAPE, 0, 0));
		fields.addField(new BaseField("Gurudwara ID", java.sql.Types.INTEGER,
				9, 0));
		fields.addField(new BaseField("Gurudwara Location",
				java.sql.Types.VARCHAR, 16, 0));
		fields.addField(new BaseField("Gurudwara Name", java.sql.Types.VARCHAR,
				40, 0));
		fields.addField(new BaseField("Year founded", java.sql.Types.VARCHAR,
				20, 0));
	}

	public BaseFeatureClass getFeatureClass(String name, BasePointsArray bpa) {
		com.esri.mo2.map.mem.MemoryFeatureClass featClass = null;
		try {
			featClass = new com.esri.mo2.map.mem.MemoryFeatureClass(
					MapDataset.POINT, fields);
		} catch (IllegalArgumentException iae) {
		}
		featClass.setName(name);
		for (int i = 0; i < bpa.size(); i++) {
			featClass.addFeature(featureVector.elementAt(i));
		}
		return featClass;
	}

	private final class XYLayerCapabilities extends
	com.esri.mo2.map.dpy.LayerCapabilities {
		XYLayerCapabilities() {
			for (int i = 0; i < this.size(); i++) {
				setAvailable(LayerCapabilities.getCapabilityName(i), true);
				setEnablingAllowed(LayerCapabilities.getCapabilityName(i), true);
				getCapability(i).setEnabled(true);
			}
		}
	}
}
