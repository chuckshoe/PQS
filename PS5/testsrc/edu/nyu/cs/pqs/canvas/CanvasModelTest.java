package edu.nyu.cs.pqs.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.canvas.CanvasListener;
import edu.nyu.cs.pqs.canvas.CanvasInformation.ColorsAllowed;
import edu.nyu.cs.pqs.canvas.CanvasInformation.Mode;

import java.awt.Color;
import java.util.Set;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class CanvasModelTest {

  CanvasModel testModel = null;
  Set<CanvasListener> testListeners = null;

  // TestListener class created for testing purposes as GUI conflicts while testing.
  TestListener testListener;

  class TestListener implements CanvasListener {
    ColorsAllowed currentColor;
    Mode currentMode;
    CanvasModel modelReference = CanvasModel.getInstance();
    int oldX;
    int oldY;
    int newX;
    int newY;
    boolean resetFlag = false;
    boolean mouseclicked = false;
    boolean mousedragged = false;

    @Override
    public boolean subscribeListener() {
      return modelReference.addListener(this);
    }

    @Override
    public boolean unsubscribeListener() {
      return modelReference.removeListener(this);
    }

    @Override
    public void clearCanvas() {
      resetFlag = true;
    }

    @Override
    public void eraseMode() {
      currentMode = Mode.ERASE;
    }

    @Override
    public void drawMode() {
      currentMode = Mode.DRAW;
    }

    @Override
    public void mouseClicked(int x, int y) {
      mouseclicked = true;
      oldX = x;
      oldY = y;
    }

    @Override
    public void mouseDragged(int newX, int newY) {
      mousedragged = true;
      this.newX = newX;
      this.newY = newY;
    }
    
    @Override
    public void colorChange(ColorsAllowed newColor) {
      currentColor = newColor;
    }
  }

  @Before
  public void setup() {
    testModel = CanvasModel.getInstance();
    testListeners = testModel.getListeners();
    testListener = new TestListener();
  }

  @Test
  public void testGetInstance() {
    CanvasModel single = CanvasModel.getInstance();
    assertEquals(testModel, single);
  }

  @Test
  public void testAddListener() {
    assertEquals(0, testListeners.size());
    testModel.addListener(testListener);
    assertEquals(1, testListeners.size());
    Iterator<CanvasListener> canvasListenerIterator = testListeners.iterator();
    assertEquals(testListener, canvasListenerIterator.next());
    testModel.removeListener(testListener);
  }  

  @Test (expected = IllegalArgumentException.class)
  public void testAddListener_nullListener() {
    testModel.addListener(null);
  }

  @Test
  public void testRemoveListener() {
    testModel.addListener(testListener);  
    assertEquals(1, testListeners.size());
    Iterator<CanvasListener> canvasListenerIterator = testListeners.iterator();
    assertTrue(testModel.removeListener(canvasListenerIterator.next()));
    assertTrue(testListeners.isEmpty());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveListener_nullListener() {
    testModel.removeListener(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testRemoveListener_unsubscribedListener() {
    testModel.removeListener(testListener);
  }

  @Test
  public void testFireCanvasClearEvent() {
    testListener.subscribeListener();
    assertFalse(testListener.resetFlag);
    testModel.fireCanvasClearEvent();
    assertTrue(testListener.resetFlag);
    testListener.unsubscribeListener();
  }

  @Test
  public void testFireEraserClickedEvent() {
    testListener.subscribeListener();
    assertNull(testListener.currentMode);
    testModel.fireEraserClickedEvent();
    assertEquals(Mode.ERASE, testListener.currentMode);
    testListener.unsubscribeListener();
  }

  @Test
  public void testFireDrawModeEvent() {
    testListener.subscribeListener();
    assertNull(testListener.currentMode);
    testModel.fireDrawModeEvent();
    assertEquals(Mode.DRAW, testListener.currentMode);
    testListener.unsubscribeListener();
  }
  
  @Test
  public void testFireMouseClickedEvent() {
    testListener.subscribeListener();
    assertFalse(testListener.mouseclicked);
    testModel.fireMouseClickedEvent(25, 10);
    assertTrue(testListener.mouseclicked);
    assertEquals(25, testListener.oldX);
    assertEquals(10, testListener.oldY);
    testListener.unsubscribeListener();
  }

  @Test
  public void testFireMouseDraggedEvent() {
    testListener.subscribeListener();
    assertFalse(testListener.mousedragged);
    testModel.fireMouseDraggedEvent(50, 25);
    assertTrue(testListener.mousedragged);
    assertEquals(50, testListener.newX);
    assertEquals(25, testListener.newY);
    testListener.unsubscribeListener();
  }

  @Test
  public void testFireColorChangeEvent() {
    Color testColor = null;
    testListener.subscribeListener();
    testModel.fireColorChangeEvent(ColorsAllowed.RED);
    if (testListener.currentColor == ColorsAllowed.RED) {
      testColor = Color.RED;
    }
    assertEquals(Color.RED, testColor);
    testListener.unsubscribeListener();
  }
}
