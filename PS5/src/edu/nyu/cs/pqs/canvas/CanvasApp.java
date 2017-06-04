package edu.nyu.cs.pqs.canvas;

import edu.nyu.cs.pqs.canvas.CanvasView;
import edu.nyu.cs.pqs.canvas.CanvasModel;

/*
 * This is the driver class to run the Canvas Application.
 * 
 * @author Chakshu Sardana
 */
public class CanvasApp {
  void startCanvas() {
    CanvasModel m = CanvasModel.getInstance();
    new CanvasView(m).init();
    new CanvasView(m).init();
  }
    
  public static void main(String[] args) {
    CanvasApp app = new CanvasApp();
    app.startCanvas();
  }
}