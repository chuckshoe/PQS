package edu.nyu.cs.pqs.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.canvas.CanvasListener;
import edu.nyu.cs.pqs.canvas.CanvasInformation.ColorsAllowed;
import edu.nyu.cs.pqs.canvas.CanvasInformation.Mode;
import edu.nyu.cs.pqs.canvas.CanvasModel;
import edu.nyu.cs.pqs.canvas.CanvasView;

import java.awt.Color;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CanvasViewTest {
  CanvasModel testModel = null;
  Set<CanvasListener> testListeners = null;
  CanvasView testView;

  @Before
  public void setup() {
    testModel = CanvasModel.getInstance();
    testListeners = testModel.getListeners();
    testView = new CanvasView(testModel);
    testView.subscribeListener();
  }

  @After
  public void tearDown() {
    testView.unsubscribeListener();
  }

  @Test
  public void testCanvasViewConstructor() {
    assertEquals(1, testListeners.size());
    assertEquals(testModel, testView.getModelReference());
    assertEquals(Mode.DRAW, testView.getCurrentMode());
  }

  @Test
  public void testSubscribeListener() {
    testView.unsubscribeListener();
    assertTrue(testView.subscribeListener());
  }

  @Test
  public void testUnsubscribeListener() {
    assertTrue(testView.unsubscribeListener());
    // done because it is again unsubscribed in tearDown() 
    testView.subscribeListener();
  }

  @Test
  public void testColorToColorsAllowed() {
    assertEquals(ColorsAllowed.BLUE, testView.colorToColorsAllowed(Color.BLUE));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testColorToColorsAllowed_nullColor() {
    testView.colorToColorsAllowed(null);
  }

  @Test
  public void testColorToColorsAllowed_ColorNotInColorsAllowed() {
    assertEquals(ColorsAllowed.BLACK, testView.colorToColorsAllowed(Color.GRAY));
  }

  @Test
  public void testMouseClicked() {
    testModel.fireMouseClickedEvent(15, 20);
    assertEquals(15, testView.getOldX());
    assertEquals(20, testView.getOldY());
  }

  @Test
  public void testDrawMode() throws InterruptedException {
    testView.setCurrentMode(Mode.ERASE);
    testModel.fireDrawModeEvent();
    assertEquals(Mode.DRAW, testView.getCurrentMode());
  }

  // the following tests need GUI and hence the init() call.
  @Test
  public void testClearCanvas() throws InterruptedException {
    testView.init();
    Thread.sleep(200);
    assertFalse(testView.reset);
    testModel.fireCanvasClearEvent();
    assertTrue(testView.reset);
    assertEquals(Color.BLACK, testView.getCurrentColor());
  }

  @Test
  public void testEraseMode() throws InterruptedException {
    testView.init();
    Thread.sleep(200);
    assertNotEquals(Mode.ERASE, testView.getCurrentMode());
    testModel.fireEraserClickedEvent();
    assertEquals(Mode.ERASE, testView.getCurrentMode());
  }
  
  @Test
  public void testMouseDragged() throws InterruptedException {
    testView.init();
    testModel.fireMouseClickedEvent(5, 2);
    Thread.sleep(200);
    testModel.fireMouseDraggedEvent(15, 20);
    assertEquals(15, testView.getOldX());
    assertEquals(20, testView.getOldY());
  }

  @Test
  public void testColorChange() throws InterruptedException {
    testView.init();
    Thread.sleep(200);
    assertNotEquals(Color.BLUE, testView.getCurrentColor());
    testModel.fireColorChangeEvent(ColorsAllowed.BLUE);
    assertEquals(Color.BLUE, testView.getCurrentColor());
  }
}
