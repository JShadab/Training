package java2d;

import java.util.ArrayList;

/**
 *claSS TO Represent the all data of Well
 * 
 */

public class WellData {
	/**
	 * declaration of wellname,x,y,and property of the well
	 */
	/**
	 * declaring wellName variable of type string 
	 */

	private String wellName;
	/**
	 * declaring variable of type double which declare the Xcordinate of particular well
	 */
	private double x;
	/**
	 * declaring variable of type double which declare the Ycordinate of particular well
	 */
			
	private double y;
	/**
	 * declaring property ArrayList of type ArrayList which will contain the property of the well 
	 */
	private ArrayList<Double> propertyArrayList;
	/**
	 * getter method for getting the x coordinate of the well
	 * 
	 */

	public double getX() {
		return x;
	}

	
	/**
	 * getter method for getting the y coordinate of the well
	 * 
	 */
	public double getY() {
		return y;
	}
	/**
	 * getter method for getting the property of the well
	 * this will return the property ArrayList
	 * 
	 */
	public ArrayList<Double> getProperty() {
		return propertyArrayList;
	}

	
	/**
	 * setter method for setting the property of the well
	 * 
	 */
	public void setProperty(ArrayList<Double> property) {
		this.propertyArrayList = property;
	}

	
	/**
	 * getter method for getting the well name
	 * 
	 */
	public String getWellName() {
		return wellName;
	}

	
	/**
	 * setter method for setting the x coordinate of the well
	 * 
	 */
	public void setX(double x) {
		this.x = x;
	}

	
	/**
	 * setter method for setting the y coordinate of the well
	 * 
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * setter method for setting the well name of the well
	 * wellname is passed by the wellForm class and wellname is now available for canvas class
	 */
	public void setWellName(String wellName) {
		this.wellName = wellName;
	}
}
