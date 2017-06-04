package edu.nyu.cs.pqs.canvas;

import edu.nyu.cs.pqs.canvas.CanvasListener;
import edu.nyu.cs.pqs.canvas.CanvasInformation.ColorsAllowed;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the Model for the Canvas Application. It is a singleton class.
 * It fires different events to let all the subscribed listeners know of the event.
 * The different events are: canvasClear, colorChange, drawMode, eraserClicked, 
 * mouseClicked and mouseDragged events.
 * It also has methods to add and remove listeners from its set of subscribed listeners.      
 * 
 * @author Chakshu Sardana
 *
 */
public class CanvasModel {
  private static CanvasModel instance = null;
  private Set<CanvasListener> listeners;

  /**
   * Private constructor for CanvasModel. Creates a new set of listeners for the model.
   */
  private CanvasModel() {
    listeners = new HashSet<>();
  }
  /**
   * 
   * @return The instance of CanvasModel.
   */
  public static CanvasModel getInstance() {
    if (instance == null) {
      instance = new CanvasModel();
    }
    return instance;    
  }
  
  /**
   * Adds a listener to it's set of listeners.
   * 
   * @param newListener The Listener to be added to the set of listeners.
   * @return true if the listener is successfully added, else false.
   * @throws IllegalArgumentException if the listener argument is null.
   */
  public boolean addListener(CanvasListener newListener) {
    if (newListener == null) {
      throw new IllegalArgumentException("Listener can't be null");
    }
    return listeners.add(newListener);
  }
  
  /**
   * Removes a listener from it's set of listeners.
   * 
   * @param removedListener The Listener to be removed from the set of listeners.
   * @return true if the listener is successfully removed, else false.
   * @throws IllegalArgumentException if the listener argument is null 
   *         or listener is not present in it's set of listeners.
   */
  public boolean removeListener(CanvasListener removedListener) {
    if (removedListener == null) {
      throw new IllegalArgumentException("Listener can't be null");
    }
    if (!listeners.contains(removedListener)) {
      throw new IllegalArgumentException("Listener is not registered.");
    }
    return listeners.remove(removedListener);
  }
  
  /**
   * For every listener subscribed to this model, events to clear canvas, change 
   * mode to Draw and change color to black are fired. 
   */
  public void fireCanvasClearEvent() {
    for (CanvasListener listener : listeners) {
      listener.clearCanvas();
      listener.drawMode();
      listener.colorChange(ColorsAllowed.BLACK);
    }
  }

  /**
   * For every listener subscribed to this model, events to change 
   * mode to Erase and change color to white are fired. 
   */  
  public void fireEraserClickedEvent() {
    for (CanvasListener listener : listeners) {
      listener.colorChange(ColorsAllowed.WHITE);
      listener.eraseMode();
    }
  }

  /**
   * For every listener subscribed to this model, event to change 
   * mode to Draw is fired. 
   */  
  public void fireDrawModeEvent() {
    for (CanvasListener listener : listeners) {
      listener.drawMode();
    }
  } 

  /**
   * For every listener subscribed to this model, the mouse clicked event is fired.
   * @param  x The x coordinate for the location where mouse is clicked.
   * @param  y The y coordinate for the location where mouse is clicked. 
   */  
  public void fireMouseClickedEvent(int x, int y) {
    for (CanvasListener listener : listeners) {
      listener.mouseClicked(x, y);
    }
  } 

  /**
   * For every listener subscribed to this model, the mouse dragged event is fired.
   * @param  x The x coordinate for the location where the mouse is dragged to.
   * @param  y The y coordinate for the location where the mouse is dragged to.
   */   
  public void fireMouseDraggedEvent(int newX, int newY) {
    for (CanvasListener listener : listeners) {
      listener.mouseDragged(newX, newY);
    }
  }

  /**
   * For every listener subscribed to this model, the color change event is fired.
   * @param  newColor The new color so that the view can update it's current color.
   */  
  public void fireColorChangeEvent(ColorsAllowed newColor) {
    for (CanvasListener listener : listeners) {
      listener.colorChange(newColor);
    }
  }
  
  // for testing
  public Set<CanvasListener> getListeners() {
    return listeners;
  }  
}
