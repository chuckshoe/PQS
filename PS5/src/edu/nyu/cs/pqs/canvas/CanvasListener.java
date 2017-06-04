package edu.nyu.cs.pqs.canvas;

import edu.nyu.cs.pqs.canvas.CanvasInformation.ColorsAllowed;

/**
 * This is the listener interface that has methods needed in order to communicate 
 * with the Canvas application model.
 * 
 * @author Chakshu Sardana
 */
public interface CanvasListener {

  /**
   * Subscribes the CanvasListener to the model.
   * 
   * @return true if registration is successful, else false.
   */
  public boolean subscribeListener();

  /**
   * Unsubscribes the CanvasListener from the model.
   * 
   * @return true if successfully unsubscribed, else false.
   */
  public boolean unsubscribeListener();
  
  /**
   * Clears the canvas (sets it so that it appears blank).
   */
  public void clearCanvas();  

  /**
   * Sets the current mode of the listener to erase mode.
   */
  public void eraseMode();

  /**
   * Sets the current mode of the listener to draw mode.
   */
  public void drawMode();  
  
  /**
   * Sets the initial values of oldX and oldY when
   * mouse is clicked on the drawing area of the canvas.
   * 
   * @param x The X coordinate of the click.
   * @param y The Y coordinate of click.
   */
  public void mouseClicked(int x, int y);

  /**
   * Updates the canvas view by drawing a line from
   * (oldX, oldY) to (newX,newY). 
   * Also replaces the old coordinates with the new ones.
   * 
   * @param newX The current X coordinate of the drag.
   * @param newY The current Y coordinate of the drag.
   */
  public void mouseDragged(int newX, int newY);
  
  /**
   * Changes the current line color of the canvas view.
   * 
   * @param newColor The color after the update
   */
  public void colorChange(ColorsAllowed newColor);  

}
