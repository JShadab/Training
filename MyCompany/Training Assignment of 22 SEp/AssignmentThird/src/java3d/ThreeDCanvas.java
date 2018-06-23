package java3d;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.OrientedShape3D;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class ThreeDCanvas {

	private Canvas3D canvas;
	private BranchGroup bg;
	private Switch switchShape;
	private Switch switchLayer;
	private Switch switchAxis;
	private Switch switchAxisLbl;
    private Switch switchTitle;
    
    public static final int FILLED_GEOMETRY = 1;
	public static final int LINE_GEOMETRY = 2;
	public static final int AXIX_GEOMETRY = 3;
	public static final int X_DIRECTION = 4;
	public static final int Y_DIRECTION = 5;
	public static final int Z_DIRECTION = 6;
	public static int CURRENT_DIRECTION = X_DIRECTION;
	public static int SELECTED_LAYER = -1;

	private Color[] colors= { (Color.blue), (Color.orange), (Color.green),(Color.white),(Color.red), (Color.cyan),Color.yellow,Color.magenta };
	
	private GridData grid;
	private OrientedShape3D[] orientShpAxisLbl;
	private double xMin, yMin, zMin;
	private double xMax, yMax, zMax;
	private double xFactor, yFactor, zFactor;
	private double avgX, avgY, avgZ;

	private boolean isLayer = false;
    
 /**
  *Default constructor of class ThreeDCanvas 
  */
	public ThreeDCanvas() {
		canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	}

	/**
	 * this method creates the canvas that contains the branch graph.
	 * 
	 * @return an instance of Canvas3D
	 */
	public Canvas3D creatingCanvas() {

		// grid data is not initialized
		if (grid == null) {
			return canvas;
		}

		SimpleUniverse simpleU = new SimpleUniverse(canvas);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		
		bg = createSceneGraph();
		bg.compile();
		simpleU.addBranchGraph(bg);

		return canvas;
	}

	/**
	 * this method creates scene graph.
	 * 
	 * @return an instance of BranchGroup.
	 */
	private BranchGroup createSceneGraph() {
		BranchGroup bg = new BranchGroup();

		Transform3D t3d = new Transform3D();
		t3d.setScale(0.1f);

	
		Background bgColor= new Background(new Color3f(Color.LIGHT_GRAY));
		
		
		BoundingBox bkBound = new BoundingBox();
		bgColor.setApplicationBounds(bkBound);

		TransformGroup tg = new TransformGroup();
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		TransformGroup tgAxis = new TransformGroup(t3d);
		tgAxis.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tgAxis.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		BoundingBox bound = new BoundingBox();

		// rotate behavior for geometry
		MouseRotate rotateBehavior = new MouseRotate(tg);
		rotateBehavior.setSchedulingBounds(bound);
		rotateBehavior.setFactor(0.2f);
		tg.addChild(rotateBehavior);

		// rotate behavior for axis label
		MouseRotate rotateBehaviorAxis = new MouseRotate(tgAxis);
		rotateBehaviorAxis.setSchedulingBounds(bound);
		rotateBehaviorAxis.setFactor(0.2f);
		tgAxis.addChild(rotateBehaviorAxis);

		// zoom behavior for geometry
		MouseZoom zoomBehavior = new MouseZoom(tg);
		zoomBehavior.setSchedulingBounds(bound);
		zoomBehavior.setFactor(0.1f);
		tg.addChild(zoomBehavior);

		// zoom behavior for axis label
		MouseZoom zoomBehaviorAxis = new MouseZoom(tgAxis);
		zoomBehaviorAxis.setSchedulingBounds(bound);
		zoomBehaviorAxis.setFactor(0.1f);
		tgAxis.addChild(zoomBehaviorAxis);

		// translate behavior for geometry
		MouseTranslate translateBehavior = new MouseTranslate(tg);
		translateBehavior.setSchedulingBounds(bound);
		translateBehavior.setFactor(0.01f);
		tg.addChild(translateBehavior);

		// translate behavior for axis label
		MouseTranslate translateBehaviorAxis = new MouseTranslate(tgAxis);
		translateBehaviorAxis.setSchedulingBounds(bound);
		translateBehaviorAxis.setFactor(0.01f);
		tgAxis.addChild(translateBehaviorAxis); 

		// oriented shape for axis labels
		orientShpAxisLbl = new OrientedShape3D[3];

		switchAxisLbl = new Switch(0);
		switchAxisLbl.setCapability(Switch.ALLOW_SWITCH_WRITE);

		for (int i = 0; i < orientShpAxisLbl.length; i++) {
		  orientShpAxisLbl[i] = new OrientedShape3D();
			orientShpAxisLbl[i]
					.setCapability(OrientedShape3D.ALLOW_POINT_WRITE);
			orientShpAxisLbl[i].setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
			orientShpAxisLbl[i]
					.setAlignmentMode(OrientedShape3D.ROTATE_ABOUT_POINT);
			tgAxis.addChild(orientShpAxisLbl[i]);
		}

		creatingAxisLbl();
		switchAxisLbl.addChild(tgAxis);

		Shape3D shpNoLbl = new Shape3D();
		shpNoLbl.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);

		switchAxisLbl.addChild(shpNoLbl);
		
		TransformGroup titleGroup=new TransformGroup(t3d); 
		titleGroup.addChild(buildTitle());
		
		tg.addChild(buildShape());
		tg.addChild(buildLayer());
		tg.addChild(buildAxis());
		bg.addChild(bgColor);
		bg.addChild(tg);
		bg.addChild(switchAxisLbl);
		
	    bg.addChild(titleGroup);
	  
		return bg;
	}
/**
 * Method to show Title 
 */
	 private Node buildTitle()
	 {
		 
	 switchTitle=new Switch(0);
	 switchTitle.setCapability(Switch.ALLOW_SWITCH_WRITE);
	
	 Font font=new Font("Ariel", Font.PLAIN, 1);
	 Font3D font3d=new Font3D(font, null);
	
	 String text=grid.getGridName();
	 Point3f pos=new Point3f(0,6,0);
	
	 Text3D txtTitle=new Text3D(font3d, text, pos);
	 txtTitle.setAlignment(Text3D.ALIGN_CENTER); 
	
     Shape3D shpTitle=new Shape3D();
	 shpTitle.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
	 shpTitle.addGeometry(txtTitle);
	
	 switchTitle.addChild(shpTitle);
	 switchTitle.addChild(new Shape3D());
	 	
	 return switchTitle;
	 }
/**
 * 
 * @return switchAxis 
 * method for create axis
 */
	private Node buildAxis() {
		switchAxis = new Switch(0);
		switchAxis.setCapability(Switch.ALLOW_SWITCH_WRITE);

		// axis lines
		Shape3D shpAxis = new Shape3D();
		shpAxis.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
		shpAxis.addGeometry(createAxis());
		shpAxis.setAppearance(createAppearance(AXIX_GEOMETRY));

		// No Axis lines
		Shape3D shpNoAxis = new Shape3D();
		shpNoAxis.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);

		switchAxis.addChild(shpAxis);
		switchAxis.addChild(shpNoAxis);

		return switchAxis;
	}

	/**
	 * creates node for filled geometry and line geometry. By default renderer
	 * geometry is filled geometry.
	 * 
	 * @return an instance of Node.
	 */
	private Node buildShape() {
		switchShape = new Switch(0);
		switchShape.setCapability(Switch.ALLOW_SWITCH_WRITE);

		Shape3D shpFilledGrid = new Shape3D();
		shpFilledGrid.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
		shpFilledGrid.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
		shpFilledGrid.addGeometry(createGeometry());
		shpFilledGrid.setAppearance(createAppearance(FILLED_GEOMETRY));

		Shape3D shpLineGrid = new Shape3D();
		shpLineGrid.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
		shpLineGrid.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		shpLineGrid.addGeometry(createGeometry());
		shpLineGrid.setAppearance(createAppearance(LINE_GEOMETRY));

		switchShape.addChild(shpFilledGrid);
		switchShape.addChild(shpLineGrid);

		return switchShape;
	}

	/**
	 * creates all possible layers in the geometry. By default none is shown.
	 * 
	 * @return an instance of Node.
	 */
	private Node buildLayer() {
		switchLayer = new Switch(0);
		switchLayer.setCapability(Switch.ALLOW_SWITCH_WRITE);

		// Shape3D with no geometry.
		Shape3D shpNoLayer = new Shape3D();
		shpNoLayer.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
		switchLayer.addChild(shpNoLayer);

		int NX = grid.getNX();
		int NY = grid.getNY();
		int NZ = grid.getNZ();

		isLayer = true;

		// Layers possible in X-direction.
		CURRENT_DIRECTION = X_DIRECTION;
		for (int i = 1; i <= NX; i++) {
			SELECTED_LAYER = i;
			Shape3D shpLayer = new Shape3D();
			shpLayer.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
			shpLayer.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
			shpLayer.addGeometry(createGeometry());
			shpLayer.setAppearance(createAppearance(FILLED_GEOMETRY));
			switchLayer.addChild(shpLayer);
		}

		// Layers possible in Y-direction.
		CURRENT_DIRECTION = Y_DIRECTION;
		for (int i = 1; i <= NY; i++) {
			SELECTED_LAYER = i;
			Shape3D shpLayer = new Shape3D();
			shpLayer.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
			shpLayer.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
			shpLayer.addGeometry(createGeometry());
			shpLayer.setAppearance(createAppearance(FILLED_GEOMETRY));
			switchLayer.addChild(shpLayer);
		}

		// Layers possible in Z-direction.
		CURRENT_DIRECTION = Z_DIRECTION;
		for (int i = 1; i <= NZ; i++) {
			SELECTED_LAYER = i;
			Shape3D shpLayer = new Shape3D();
			shpLayer.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
			shpLayer.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
			shpLayer.addGeometry(createGeometry());
			shpLayer.setAppearance(createAppearance(FILLED_GEOMETRY));
			switchLayer.addChild(shpLayer);
		}

		isLayer = false;

		return switchLayer;
	}

	/**
	 * this method creates Labels of Axis.
	 */
	private void creatingAxisLbl() {

		Font font = new Font("Arial", Font.BOLD, 1);
		Font3D font3d = new Font3D(font, null);

		Text3D txt3dX = new Text3D(font3d, "X", new Point3f(
				(float) (((xMax - avgX) / 2) * xFactor) + 4.2f,
				(float) (((yMin - avgY) / 2) * yFactor) - 2.6f,
				(float) (((avgZ - zMin) / 2) * zFactor) + 3.0f));
		txt3dX.setAlignment(Text3D.ALIGN_CENTER);
		orientShpAxisLbl[0].addGeometry(txt3dX);
		orientShpAxisLbl[0].setRotationPoint(new Point3f(
				(float) (((xMax - avgX) / 2) * xFactor) + 4.2f,
				(float) (((yMin - avgY) / 2) * yFactor) - 2.6f,
				(float) (((avgZ - zMin) / 2) * zFactor) + 3.0f));

		Text3D txt3dY = new Text3D(font3d, "Y", new Point3f(
				(float) (((xMin - avgX) / 2) * xFactor) - 2.0f,
				(float) (((yMax - avgY) / 2) * yFactor) + 4.2f,
				(float) (((avgZ - zMin) / 2) * zFactor) + 3.0f));
		txt3dY.setAlignment(Text3D.ALIGN_CENTER);
		orientShpAxisLbl[1].addGeometry(txt3dY);
		orientShpAxisLbl[1].setRotationPoint(new Point3f(
				(float) (((xMin - avgX) / 2) * xFactor) - 2.0f,
				(float) (((yMax - avgY) / 2) * yFactor) + 4.2f,
				(float) (((avgZ - zMin) / 2) * zFactor) + 3.0f));

		Text3D txt3dZ = new Text3D(font3d, "Z", new Point3f(
				(float) (((xMin - avgX) / 2) * xFactor) - 2.0f,
				(float) (((yMin - avgY) / 2) * yFactor) - 2.0f,
				(float) (((avgZ - zMax) / 2) * zFactor) - 4.2f));
		txt3dZ.setAlignment(Text3D.ALIGN_CENTER);
		orientShpAxisLbl[2].addGeometry(txt3dZ);
		orientShpAxisLbl[2].setRotationPoint(new Point3f(
				(float) (((xMin - avgX) / 2) * xFactor) - 2.0f,
				(float) (((yMin - avgY) / 2) * yFactor) - 2.0f,
				(float) (((avgZ - zMax) / 2) * zFactor) - 4.2f));
	}

	/**
	 * this method is used to create the axis of geometry.
	 * 
	 * @return an instance of LineArray.
	 */
	private LineArray createAxis() {

		Point3d xAxis = new Point3d((((xMax - avgX) / 2) * xFactor) + 0.2,
				((yMin - avgY) / 2) * yFactor, ((avgZ - zMin) / 2) * zFactor);
		Point3d yAxis = new Point3d(((xMin - avgX) / 2) * xFactor,
				(((yMax - avgY) / 2) * yFactor) + 0.2, ((avgZ - zMin) / 2)
						* zFactor);
		Point3d zAxis = new Point3d(((xMin - avgX) / 2) * xFactor,
				((yMin - avgY) / 2) * yFactor,
				(((avgZ - zMax) / 2) * zFactor) - 0.2);
		Point3d origin = new Point3d(((xMin - avgX) / 2) * xFactor,
				((yMin - avgY) / 2) * yFactor, ((avgZ - zMin) / 2) * zFactor);

		LineArray lineArray = new LineArray(6, LineArray.COORDINATES);
		lineArray.setCoordinate(0, origin);
		lineArray.setCoordinate(1, xAxis);
		lineArray.setCoordinate(2, origin);
		lineArray.setCoordinate(3, yAxis);
		lineArray.setCoordinate(4, origin);
		lineArray.setCoordinate(5, zAxis);

		return lineArray;
	}

	/**
	 * this method is used to create Appearance of all type of geometry required.
	 * 
	 * @param geomType
	 *            : which type of appearance is required for shape.
	 * @return an instance of Appearance.
	 */
	private Appearance createAppearance(int geomType) {
		Appearance appearance = new Appearance();
		appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);

		LineAttributes la = new LineAttributes();
		la.setLineAntialiasingEnable(true);
		la.setCapability(LineAttributes.ALLOW_PATTERN_WRITE);
		la.setCapability(LineAttributes.PATTERN_SOLID);
		la.setLineWidth(0.2f);

		ColoringAttributes ca = new ColoringAttributes();
		ca.setCapability(ColoringAttributes.ALLOW_COLOR_WRITE);

		PolygonAttributes pa = new PolygonAttributes();
		pa.setCullFace(PolygonAttributes.CULL_NONE);

		switch (geomType) {
		case FILLED_GEOMETRY:
			pa.setPolygonMode(PolygonAttributes.POLYGON_FILL);
			break;

		case LINE_GEOMETRY:
			pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
			break;

		case AXIX_GEOMETRY:
			la.setLineWidth(2.0f);
			break;
		}

		appearance.setLineAttributes(la);
		appearance.setColoringAttributes(ca);
		appearance.setPolygonAttributes(pa);

		return appearance;
	}

	/**
	 * this method creates geometry and all possible layers of that geometry.
	 * 
	 * @return an instance of GeometryArray.
	 */
	private GeometryArray createGeometry() {

		int NX = grid.getNX();
		int NY = grid.getNY();
		int NZ = grid.getNZ();

		int count = 2 * (NX * NY + NY * NZ + NZ * NX);
		double[] coordinates = new double[count * 4 * 3];
		byte[] cellColors = new byte[count * 4 * 3];
		double[][] cellCoordinates = null;

		int ctr = 0;
		int colorCtr = 0;
		int colorIndex = 0;

		for (int z = 1; z <= NZ; z++) {
			for (int y = 1; y <= NY; y++) {
				for (int x = 1; x <= NX; x++) {
					if (x == 1 || x == NX || y == 1 || y == NY || z == 1
							|| z == NZ) {
						if (isLayer) {
							switch (CURRENT_DIRECTION) {
							case X_DIRECTION:
								cellCoordinates = getCellCoordinates(
										SELECTED_LAYER, y, z);
								break;

							case Y_DIRECTION:
								cellCoordinates = getCellCoordinates(x,
										SELECTED_LAYER, z);
								break;

							case Z_DIRECTION:
								cellCoordinates = getCellCoordinates(x, y,
										SELECTED_LAYER);
								break;
							}
						} else {
							cellCoordinates = getCellCoordinates(x, y, z);
						}

						// Left face
						if (x == 1) {
							coordinates[ctr++] = ((cellCoordinates[0][0] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][0] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][0]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][3] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][3] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][3]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][7] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][7] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][7]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][4] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][4] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][4]) / 2)
									* zFactor;

							for (int i = 0; i < 4; i++) {
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getRed();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getGreen();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getBlue();
							}
						}

						// Back face
						if (y == 1) {
							coordinates[ctr++] = ((cellCoordinates[0][0] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][0] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][0]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][1] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][1] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][1]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][5] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][5] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][5]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][4] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][4] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][4]) / 2)
									* zFactor;

							for (int i = 0; i < 4; i++) {
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getRed();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getGreen();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getBlue();
							}
						}

						// Top face
						if (z == 1) {
							coordinates[ctr++] = ((cellCoordinates[0][0] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][0] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][0]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][1] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][1] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][1]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][2] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][2] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][2]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][3] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][3] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][3]) / 2)
									* zFactor;

							for (int i = 0; i < 4; i++) {
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getRed();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getGreen();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getBlue();
							}
						}

						// Right face
						if (x == NX) {
							coordinates[ctr++] = ((cellCoordinates[0][1] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][1] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][1]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][2] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][2] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][2]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][6] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][6] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][6]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][5] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][5] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][5]) / 2)
									* zFactor;

							for (int i = 0; i < 4; i++) {
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getRed();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getGreen();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getBlue();
							}
						}

						// Front face
						if (y == NY) {
							coordinates[ctr++] = ((cellCoordinates[0][2] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][2] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][2]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][3] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][3] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][3]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][7] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][7] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][7]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][6] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][6] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][6]) / 2)
									* zFactor;

							for (int i = 0; i < 4; i++) {
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getRed();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getGreen();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getBlue();
							}
						}

						// Bottom face
						if (z == NZ) {
							coordinates[ctr++] = ((cellCoordinates[0][4] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][4] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][4]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][5] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][5] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][5]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][6] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][6] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][6]) / 2)
									* zFactor;

							coordinates[ctr++] = ((cellCoordinates[0][7] - avgX) / 2)
									* xFactor;
							coordinates[ctr++] = ((cellCoordinates[1][7] - avgY) / 2)
									* yFactor;
							coordinates[ctr++] = ((avgZ - cellCoordinates[2][7]) / 2)
									* zFactor;

							for (int i = 0; i < 4; i++) {
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getRed();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getGreen();
								cellColors[colorCtr++] = (byte) colors[colorIndex]
										.getBlue();
							}
						}
						colorIndex = (colorIndex == 7) ? 0 : colorIndex + 1;
					}
				}
			}
		}

		QuadArray qa = new QuadArray(count * 4, QuadArray.COORDINATES
				| QuadArray.COLOR_3);
		qa.setCoordinates(0, coordinates);
		qa.setColors(0, cellColors);

		return qa;
	}

	private double[][] getCellCoordinates(int x, int y, int z) {
		int NX = grid.getNX();
		int NY = grid.getNY();

		ArrayList xVal = grid.getxValues();
		ArrayList yVal = grid.getyValues();
		ArrayList zVal = grid.getzValues();

		int cellIndex = NX * (y - 1) + (x - 1) + (NX * NY) * (z - 1);

		int k = 0;

		double[][] cellCoordinates = new double[3][8];

		while (k < 8) {
			cellCoordinates[0][k] = (Double) xVal.get(8 * cellIndex + k);
			cellCoordinates[1][k] = (Double) yVal.get(8 * cellIndex + k);
			cellCoordinates[2][k] = (Double) zVal.get(8 * cellIndex + k);
			++k;
		}

		return cellCoordinates;
	}
  /**
   * 
   * @param gd to set grid
   */
	public void setGrid(GridData gd) {
		this.grid = gd;
		setMinMax();
		setScaleFactor();
	}
  /**
   * 
   * @param onOff to show and Hide Grid
   */
	public void showHideGid(boolean onOff) {
		if (onOff) {
			switchShape.setWhichChild(1);
		} else {
			switchShape.setWhichChild(0);
		}
	}
/**
 * 
 * @param onOff to show and Hide Axis
 */
	public void showHideAxis(boolean onOff) {
		if (onOff) {
			switchAxis.setWhichChild(0);
			switchAxisLbl.setWhichChild(0);
		} else {
			switchAxis.setWhichChild(1);
			switchAxisLbl.setWhichChild(1);
		}
	}
/**
 * 
 * @param onOff to show and Hide Title 
 */
	public void showHideTitle(Boolean onOff){
		if(onOff){
			switchTitle.setWhichChild(0);
		}
		else {
			switchTitle.setWhichChild(1);
		}
	}
	/**
	 * 
	 * @param layer for layering
	 */
	public void showLayer(int layer) {
		if (layer < switchLayer.numChildren()) {
			switchLayer.setWhichChild(layer);
		}
	}
/**
 * method to set scale factor
 */
	private void setScaleFactor() {
		xFactor = 1.0 / (xMax - xMin);
		yFactor = 1.0 / (yMax - yMin);
		zFactor = 1.0 / (zMax - zMin);

		avgX = (xMin + xMax) / 2;
		avgY = (yMin + yMax) / 2;
		avgZ = (zMin + zMax) / 2;
	}
/**
 * Method to set Maximum and Minimum values of X,Y&Z coordinate
 */
	private void setMinMax() {
		if (grid != null) {
			xMin = Collections.min(grid.getxValues());
			yMin = Collections.min(grid.getyValues());
			zMin = Collections.min(grid.getzValues());
			xMax = Collections.max(grid.getxValues());
			yMax = Collections.max(grid.getyValues());
			zMax = Collections.max(grid.getzValues());
		}
	}

	

}