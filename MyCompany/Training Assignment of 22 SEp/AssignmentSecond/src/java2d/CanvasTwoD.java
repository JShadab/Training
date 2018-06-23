package java2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.color.CMMException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Main class for designing and paint component for draw well, bubble, ticks To
 * add Mouse related event
 * 
 */

public class CanvasTwoD extends JPanel implements MouseMotionListener,
		MouseListener {

	/**
	 * declaring variable for zooming process
	 */
	boolean isZoomed;

	/**
	 * declaring variable of arraylist type which will contain the well property
	 */

	private ArrayList<WellData> wellList;

	/**
	 * declaring variable of JPopMenu type which is used
	 */
	private JPopupMenu popUpMenu;
	/** declaration of select index variable */

	/**
	 * selectIndex for set well property of the well
	 */
	int selectIndex;

	/** declaration of array for value of x , y and property array */
	Double valuesOfX[], valuesOfY[], propertyArray[];

	/** declaration of major ticks variable */
	int majorTicks = 10;
	/** declaration of minor ticks variable */
	int minorTicks = 10;

	/**
	 * declaration of height , width, leftInsect, bottonInsect variable of type
	 * double
	 */
	double height, width, leftInsect, bottomInsect;

	/**
	 * show the XCordinate of mouse where mouse is pressed on the panel
	 */
	double pressX;
	/**
	 * show the YCordinate of mouse where mouse is pressed on the panel
	 */
	double pressY;
	/**
	 * show the XCordinate of mouse where mouse is released on the panel
	 */
	double releaseX;
	/**
	 * show the YCordinate of mouse where mouse is released on the panel
	 */
	double releaseY;
	/**
	 * show the XCordinate of mouse where mouse is dragged on the panel
	 */
	double draggedX;
	/**
	 * show the YCordinate of mouse where mouse is dragged on the panel
	 */
	double draggedY;

	/** declaration of g2d and clip rectangle */
	Graphics2D g2d;
	/**
	 * declaring the clipRectangle variable of type Rectangle2D.Double which is
	 * to be drawn on the panel
	 */
	Rectangle2D.Double clipRectangle;
	/** declaration of variable xMin,xMax, yMin and yMax */
	/**
	 * variable for find minimum values of X coordinate
	 */
	double xMin;

	/**
	 * variable for find maximum values of X coordinate
	 */
	double xMax;
	/**
	 * variable for find minimum values of Y coordinate
	 */
	double yMin;

	/**
	 * variable for find maximum values of Y coordinate
	 */
	double yMax;

	/** constructor of class Canvas */
	public CanvasTwoD() {
		repaint();
	}

	/**
	 * 
	 * @param wellList
	 *            which is the parameter which is to be passed declaring the
	 *            setwellList method which will set the wellList which is
	 *            obtained from wellForm class
	 */
	public void setWellList(ArrayList<WellData> wellList) {
		this.wellList = wellList;
		/**
		 * adding mouse motion listener
		 */
		addMouseMotionListener(this);
		addMouseListener(this);
		/** method call initVal */
		initVal();
	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}

	/** method for initialized value of X and Y coordinate */
	private void initVal() {
		valuesOfX = new Double[wellList.size()];
		valuesOfY = new Double[wellList.size()];

		for (int j = 0; j < wellList.size(); j++) {
			valuesOfX[j] = wellList.get(j).getX();
			valuesOfY[j] = wellList.get(j).getY();
		}

		/** calling method of getMin and getMax of x coordinate */
		xMin = getMin(valuesOfX);
		xMax = getMax(valuesOfX);

		/** calling method of getMin and getMax of y coordinate */
		yMin = getMin(valuesOfY);
		yMax = getMax(valuesOfY);
	}

	/**
	 * method for drawing all components such as bubble, ticks, well
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;

		/*********************************************/
		if (wellList != null) {
			// propertyArray = new Double[wellList.size()];

			double radius = 0, x = 0, y = 0;

			height = this.getHeight();
			width = this.getWidth();
			leftInsect = width / 10;
			bottomInsect = height / 10;

			/** use for clipping the selected area */
			clipRectangle = new Rectangle2D.Double(leftInsect, bottomInsect,
					width - leftInsect * 2, height - bottomInsect * 2);

			g2d.draw(clipRectangle);
			g2d.setClip(clipRectangle);

			for (int i = 0; i < wellList.size(); i++) {

				// radius = (propertyArray[i] * getPropertyFactor()) * 500;

				double factorX = (clipRectangle.getWidth()) / (xMax - xMin);
				double factorY = (clipRectangle.getHeight()) / (yMax - yMin);
				x = clipRectangle.getX()
						+ ((wellList.get(i).getX() - xMin) * factorX);

				y = (getHeight() - clipRectangle.getY())
						- ((wellList.get(i).getY() - yMin) * factorY);

				/** draw circle according to radius */
				Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 0, 0);

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				/** fill circle */
				g2d.fill(circle);

				g2d.setPaint(Color.BLACK);

				/** draw center of well */

				g2d.fill(new Ellipse2D.Double((x), (y), 3, 3));

				/** print name of well */
				g2d.drawString(wellList.get(i).getWellName(), (float) (x),
						(float) (y));
			}

			/** draw rectangle when we press mouse and drag it */
			if (isZoomed) {
				g2d.draw(new Rectangle2D.Double(pressX, pressY, draggedX
						- pressX, draggedY - pressY));
			}

			g2d.setClip(null);
			g2d.setStroke(new BasicStroke(1.5f));
			xTics();
			yTics();
		}
		/*************************************************************/
		if (selectIndex != 0) {
			if (wellList != null) {
				propertyArray = new Double[wellList.size()];

				double radius = 0, x = 0, y = 0;

				height = this.getHeight();
				width = this.getWidth();
				leftInsect = width / 10;
				bottomInsect = height / 10;

				/** use for clipping the selected area */
				clipRectangle = new Rectangle2D.Double(leftInsect,
						bottomInsect, width - leftInsect * 2, height
								- bottomInsect * 2);

				g2d.draw(clipRectangle);
				g2d.setClip(clipRectangle);

				for (int j = 0; j < wellList.size(); j++) {
					propertyArray[j] = wellList.get(j).getProperty()
							.get(selectIndex - 1);
				}

				for (int i = 0; i < wellList.size(); i++) {

					radius = (propertyArray[i] * getPropertyFactor()) * 500;

					double factorX = (clipRectangle.getWidth()) / (xMax - xMin);
					double factorY = (clipRectangle.getHeight())
							/ (yMax - yMin);
					x = clipRectangle.getX()
							+ ((wellList.get(i).getX() - xMin) * factorX);

					y = (getHeight() - clipRectangle.getY())
							- ((wellList.get(i).getY() - yMin) * factorY);

					/** draw circle according to radius */
					Ellipse2D.Double circle = new Ellipse2D.Double(x - radius
							/ 2, y - radius / 2, radius, radius);

					if (selectIndex == 1) {
						g2d.setPaint(Color.BLUE);
					}
					if (selectIndex == 2) {
						g2d.setPaint(Color.GREEN);
					}
					if (selectIndex == 3) {
						g2d.setPaint(Color.RED);
					}

					g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
					/** fill circle */
					g2d.fill(circle);

					g2d.setPaint(Color.BLACK);

					/** draw center of well */

					g2d.fill(new Ellipse2D.Double((x), (y), 3, 3));

					/** print name of well */
					g2d.drawString(wellList.get(i).getWellName(), (float) (x),
							(float) (y));
				}

				/** draw rectangle when we press mouse and drag it */
				if (isZoomed) {
					g2d.draw(new Rectangle2D.Double(pressX, pressY, draggedX
							- pressX, draggedY - pressY));
				}

				g2d.setClip(null);
				g2d.setStroke(new BasicStroke(1.5f));
				xTics();
				yTics();
			}
		}
	}

	/** method for draw major ticks and minor ticks of Y coordinate */
	void yTics() {
		double yTicksIncrValue = 0;
		/**
		 * for loop is declared for drawing the YTics value
		 */
		for (int t = 1; t <= majorTicks; t++) {

			/*************************************/
			/**
			 * getting the Yfactor
			 */
			double factorY = (clipRectangle.getHeight()) / (yMax - yMin);
			g2d.setPaint(Color.BLACK);
			/**
			 * getting the Y coordinate of the well 
			 */
			double Y = bottomInsect + (yTicksIncrValue * factorY);
			/**
			 * draw the YTics on the y Axis
			 */
			g2d.draw(new Line2D.Double(leftInsect - 10, Y, leftInsect + 10,
					bottomInsect + (yTicksIncrValue * factorY)));

			/**
			 * decimal formatter is declared
			 */
			DecimalFormat formatter = new DecimalFormat("#0.000");
			g2d.setPaint(Color.BLACK);
			g2d.drawString(" " + formatter.format((yMin + yTicksIncrValue)),
					(float) (leftInsect - 75),

					(float) (clipRectangle.getMaxY() - yTicksIncrValue
							* factorY));
/**
 * draw the subMinor tics between two major tics
 */
			if (t < majorTicks) {
				double yMTicsIncrValue = yTicksIncrValue;
				for (int j = 0; j < minorTicks; j++) {
					double Ym = bottomInsect + (yMTicsIncrValue * factorY);

					g2d.draw(new Line2D.Double(leftInsect, Ym, leftInsect + 5,
							bottomInsect + (yMTicsIncrValue * factorY)));
					yMTicsIncrValue = yMTicsIncrValue
							+ ((yMax - yMin) / (majorTicks - 1))
							/ (minorTicks - 1);
				}
			}

			yTicksIncrValue = yTicksIncrValue + (yMax - yMin)
					/ (majorTicks - 1);
			/**************************************/
		}
	}

	/** 
	 * method for drawing major ticks and minor ticks of X coordinate 
	 * */
	void xTics() {
		double xTicksIncrValue = 0;
		/**
		 * for loop is declared for drawing the XTics
		 */
		for (int t = 1; t <= majorTicks; t++) {
			/*********************/
			/**
			 * factorX is declared 
			 */
			double factorX = (clipRectangle.getWidth()) / (xMax - xMin);
			g2d.setPaint(Color.BLACK);
			/**
			 * XCoordinate is set
			 */
			double X = leftInsect + xTicksIncrValue * factorX;
			BasicStroke bs = new BasicStroke();
			g2d.setStroke(bs);
/**
 * drawing the ticsLine on the X Axis
 */
			g2d.draw(new Line2D.Double(X, height - bottomInsect - 10,
					leftInsect + xTicksIncrValue * (factorX), height
							- bottomInsect + 10));
/**
 * formatter is declared for showing the only three digit after decimal
 */
			DecimalFormat formatter = new DecimalFormat("#0.000");
			g2d.setPaint(Color.GRAY);
			
			g2d.drawString(" " + formatter.format((xMin + xTicksIncrValue)),
					(float) (leftInsect - 10 + xTicksIncrValue * factorX),
					(float) (clipRectangle.getMaxY() + 40));
			// FOr draw minor ticks

			if (t < majorTicks) {
				double xMTicsIncrValue = xTicksIncrValue;
				for (int j = 0; j < minorTicks; j++) {
					double Xm = leftInsect + xMTicsIncrValue * factorX;

					g2d.draw(new Line2D.Double(Xm, height - bottomInsect - 5,
							leftInsect + xMTicsIncrValue * (factorX), height
									- bottomInsect));
					xMTicsIncrValue = xMTicsIncrValue
							+ ((xMax - xMin) / (majorTicks - 1))
							/ (minorTicks - 1);
				}
			}
			xTicksIncrValue = xTicksIncrValue + (xMax - xMin)
					/ (majorTicks - 1);

		}
	}

	/**
	 * 
	 * @param arr
	 *            of x coordinate
	 * @return max value of array
	 */
	Double getMax(Double arr[]) {
		Double max, arrayX[] = arr;
		max = arrayX[0];
		/*
		 * for loop is declared for getting the max value
		 */
		for (int i = 1; i < arrayX.length; i++) {
			/**
			 * if array value is greater then max value the if condition will be executed
			 */
			if (arrayX[i] >= max) {
				/**
				 * max value is asssigned
				 */
				max = arrayX[i];
			}
		}
		/**
		 * return the maximum value
		 */
		return max;
	}

	/**
	 * 
	 * @param arr
	 *            of x coordinate
	 * @return minimum value of array
	 */
	Double getMin(Double arr[]) {
		Double min, arrayX[] = arr;
		min = arrayX[0];
		for (int i = 1; i < arrayX.length; i++) {
			if (arrayX[i] <= min) {
				min = arrayX[i];
			}
		}
		return min;
	}

	/** method for determining factor of X coordinate to set screen resolution */
	Double getFactorX() {

		Double maxX, minX;
		Double factorX = null;
		maxX = getMax(valuesOfX);
		minX = getMin(valuesOfX);
		factorX = (clipRectangle.getWidth()) / (maxX - minX);
		return factorX;
	}

	/**
	 * method for determining factor of Y coordinate to set screen resolution
	 * 
	 */
	Double getFactorY() {
		Double maxY, minY, factorY = null;
		/**
		 * determining the maximum value of YCordinate
		 */
		maxY = getMax(valuesOfY);
		/**
		 * determining the minimum value of YCoordinate
		 */
		minY = getMin(valuesOfY);
		/**
		 * determining the YFactor
		 */
		factorY = (clipRectangle.getHeight()) / (maxY - minY);
		return factorY;
	}

	/**
	 * method for getting property factor
	 */
	Double getPropertyFactor() {
		Double max, min, factor = null;
		/**
		 * determining the maximum value of property Array
		 */
		max = getMax(propertyArray);
		/**
		 * determining the maximum value of property Array
		 */
		min = getMin(propertyArray);
		factor = (clipRectangle.getWidth()) / (max - min);
		return factor / 10000;
	}

	/**
	 * listener for mouse click
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("mouseClicked");

	}

	/**
	 * listener for mouse Pressed
	 */

	@Override
	public void mousePressed(MouseEvent e) {

		isZoomed = true;
		/**
		 * x coordinate of pressed point
		 * 
		 */
		pressX = e.getX();
		/**
		 * getting the Y Coordinate of pressed point
		 */
		pressY = e.getY();

		

	}

	/**
	 * listener for mouse released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

		isZoomed = false;
		releaseX = e.getX();
		releaseY = e.getY();
/**
 * if pressX is less then the releaseX then if condition will be executed
 */
		if (pressX < releaseX) {
			/**
			 * getting the xmin value
			 */
			xMin = (((pressX - clipRectangle.getX())) / getFactorX()) + xMin;
			/**
			 * getting the ymax value
			 */
			yMax = (((getHeight() - pressY - clipRectangle.getY()) / getFactorY()))
					+ yMin;
/**
 * getting the XMax value
 */
			xMax = (((releaseX - clipRectangle.getX())) / getFactorX()) + xMin;
			/**
			 * getting the YMin value
			 */
			yMin = (((getHeight() - releaseY - clipRectangle.getY()) / getFactorY()))
					+ yMin;
		} else {
			/**
			 * getting the xmin value
			 */
			xMin = getMin(valuesOfX);
			/**
			 * getting the xmax value
			 */
			xMax = getMax(valuesOfX);
			/**
			 * getting the YMin value
			 */

			yMin = getMin(valuesOfY);
			/**
			 * getting the YMax value
			 */
			yMax = getMax(valuesOfY);
		}
		repaint();
	}

	/**
	 * listener for mouse dragged
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		/**
		 * getting the xCoordinate of dragged point on the panel
		 */
		draggedX = e.getX();
		/**
		 * getting the YCoordinate of dragged point on the jpanel
		 */
		draggedY = e.getY();
		/**
		 * repaint method is called
		 */
		repaint();
	}

	/**
	 * listener for mouse move
	 */

	@Override
	public void mouseMoved(MouseEvent e) {
		if (popUpMenu != null) {
			popUpMenu.setVisible(false);
		}
		/**
		 * if selectIndex is not equal to zero then if condition will be
		 * executed
		 */
		if (selectIndex != 0)
			/**
			 * if wellList is not null then if condition will be executed
			 */
			if (wellList != null) {
				propertyArray = new Double[wellList.size()];
				/**
				 * initialization of x,y and radius variable
				 */
				double radius = 0, x = 0, y = 0;
				/**
				 * getting the height
				 */
				height = this.getHeight();
				/**
				 * getting the width panel
				 */
				width = this.getWidth();

				leftInsect = width / 20;
				bottomInsect = height / 10;
				/**
				 * rectangle will be drawn when required parameter is passed in
				 * the Rectangle2D.Double
				 */
				clipRectangle = new Rectangle2D.Double(leftInsect,
						bottomInsect, width - leftInsect * 2, height
								- bottomInsect * 2);

				/**
				 * for loop is declared for getting the wellList property
				 */

				for (int j = 0; j < wellList.size(); j++) {
					propertyArray[j] = wellList.get(j).getProperty()
							.get(selectIndex - 1);
				}
				/**
				 * for loop is declared for getting the radius of each well
				 */
				for (int i = 0; i < wellList.size(); i++) {

					radius = (propertyArray[i] * getPropertyFactor()) * 500;
					/**
					 * getting the factorX which is used for drawing the well
					 */
					double factorX = (clipRectangle.getWidth()) / (xMax - xMin);
					/**
					 * getting the factorY which is used for drawing the well
					 */
					double factorY = (clipRectangle.getHeight())
							/ (yMax - yMin);
					/**
					 * getting the XCoordinate of the well which is passed in
					 * Ellipse2D.Double
					 */
					x = clipRectangle.getX()
							+ ((wellList.get(i).getX() - xMin) * factorX);

					/**
					 * getting the YCoordinate of the well which is passed in
					 * Ellipse2D.Double
					 */
					y = (getHeight() - clipRectangle.getY())
							- ((wellList.get(i).getY() - yMin) * factorY);

					Ellipse2D.Double shape = new Ellipse2D.Double((x), (y),
							radius, radius);

					/**
					 * if shape will contain the point then if condition will be
					 * executed
					 */
					if (shape.contains(e.getPoint())) {
						/**
						 * popupmenu is created which will be displayed on
						 * mouseMoved Event
						 */
						popUpMenu = new JPopupMenu(wellList.get(i)
								.getWellName());
						JMenuItem menuItem = new JMenuItem(wellList.get(i)
								.getWellName());
						/**
						 * getting the XCoordinate of wellList
						 */
						Double xcordinate = wellList.get(i).getX();
						/**
						 * getting the YCoordinate of wellList
						 */
						Double ycordinate = wellList.get(i).getY();
						/**
						 * MenuItem will be added on popMenu
						 */
						popUpMenu.add(menuItem);
						/**
						 * XCoordinate will be added on popMenu
						 */
						popUpMenu.add(xcordinate.toString());
						/**
						 * ycoordinate will be added on popupmenu
						 */
						popUpMenu.add(ycordinate.toString());
						/**
						 * popupmenu will be shown when mousemoved event will be
						 * fire
						 */
						popUpMenu.show(e.getComponent(), e.getX(), e.getY());

					}

				}

			}

	}

	/**
	 * listener for mouse entered
	 */

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * listener for mouse exited
	 */

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}