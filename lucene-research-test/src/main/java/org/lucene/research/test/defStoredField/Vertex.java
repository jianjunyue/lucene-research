package org.lucene.research.test.defStoredField;


import java.io.Serializable;

public class Vertex implements Serializable {
  private static final long serialVersionUID = -6870639303181736116L;
  
  private float polyX;
  
  private float polyY;
  
  public Vertex(float polyX, float polyY) {
    this.polyX = polyX;
    this.polyY = polyY;
  }
  
  public float getPolyX() {
    return this.polyX;
  }
  
  public void setPolyX(float polyX) {
    this.polyX = polyX;
  }
  
  public float getPolyY() {
    return this.polyY;
  }
  
  public void setPolyY(float polyY) {
    this.polyY = polyY;
  }
}