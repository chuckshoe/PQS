package edu.nyu.cs.pqs.canvas;

/**
 * This class has the information about the mode and
 * allowed colors in the Canvas Application.
 * 
 * @author Chakshu Sardana
 */
public class CanvasInformation {
  /**
   * A static enum for the various colors allowed by the application.
   */
  public static enum ColorsAllowed {
    BLACK, BLUE, GREEN, RED, WHITE
  }
  
  /**
   * A static enum for the modes that the canvas can be in.
   */
  public static enum Mode {
    DRAW, ERASE
  }
}
