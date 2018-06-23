package java3d;

import java.util.ArrayList;

public class GridData {
private String gridName;
private int NX;
private int NY;
private int NZ;
private ArrayList xValues;
private ArrayList yValues;
private ArrayList zValues;

/**
 * 
 * @return gridName
 */
public String getGridName() {
return gridName;
}
/**
 * 
 * @param gridName to set grid name
 */
public void setGridName(String gridName) {
this.gridName = gridName;
}
/**
 * 
 * @return NX coordinate of X
 */
public int getNX() {
return NX;
}
/**
 * 
 * @param nX coordinate of X set
 */
public void setNX(int nX) {
NX = nX;
}
/**
 * 
 * @return NY coordinate of Y
 */
public int getNY() {
return NY;
}
/**
 * 
 * @param nY coordinate Y set
 */
public void setNY(int nY) {
NY = nY;
}
/**
 * 
 * @return NZ coordinate Z
 */
public int getNZ() {
return NZ;
}
/**
 * 
 * @param nZ coordinate Z set
 */
public void setNZ(int nZ) {
NZ = nZ;
}
/**
 * 
 * @return xValue of ArrayList
 */
public ArrayList getxValues() {
xValues.trimToSize();
return xValues;
}
/**
 * 
 * @param xValues of ArrayList set
 */
public void setxValues(ArrayList xValues) {
this.xValues = xValues;
}
/**
 * 
 * @return yValues of ArrayList
 */
public ArrayList getyValues() {
yValues.trimToSize();
return yValues;
}
/**
 * 
 * @param yValues of ArrayList set
 */
public void setyValues(ArrayList yValues) {
this.yValues = yValues;
}
/**
 * 
 * @return zValues of ArrayList
 */
public ArrayList getzValues() {
zValues.trimToSize();
return zValues;
}
/**
 * 
 * @param zValues of ArrayList set
 */
public void setzValues(ArrayList zValues) {
this.zValues = zValues;
}
}