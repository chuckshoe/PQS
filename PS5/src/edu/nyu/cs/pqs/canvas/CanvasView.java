package edu.nyu.cs.pqs.canvas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import edu.nyu.cs.pqs.canvas.CanvasModel;
import edu.nyu.cs.pqs.canvas.CanvasInformation.ColorsAllowed;
import edu.nyu.cs.pqs.canvas.CanvasInformation.Mode;

/**
 * This class provides the GUI for the Canvas Application. The GUI has the drawing area 
 * and the options (eraser, clear, choosing different line color). It also implements the
 * CanvasListener which enables it to connect to the Canvas model. 
 * 
 * @author Chakshu Sardana
 *
 */
public class CanvasView implements CanvasListener {
  private CanvasPanel canvas;
  private CanvasModel modelReference;
  private JFrame mainFrame;
  private CanvasInformation.Mode currentMode;
  // for testing
  boolean reset = false;
  
  /**
   * Creates a new object of CanvasView class.
   * @param model the Canvas Model that the listener wants to subscribe/unsubscribe to.
   *  
   */
  public CanvasView(CanvasModel model) {
    modelReference = model;
    subscribeListener();
    currentMode = Mode.DRAW;
    canvas = new CanvasPanel();
  }
  
  /**
   * Creates and displays the GUI for the user to work on.
   * The GUI has the drawing area and the options 
   * (eraser, clear, choosing different line color).
   */
  void init() {
    mainFrame = new JFrame("Canvas");
    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    mainFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        unsubscribeListener();
      }
    });
    mainFrame.setSize(500, 500);
    mainFrame.setResizable(false);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setLayout(new BorderLayout());
    mainFrame.add(getOptionsPanel(), BorderLayout.SOUTH);
    mainFrame.add(canvas, BorderLayout.CENTER);
    mainFrame.pack();
    mainFrame.setVisible(true);      
  }
  
  /**
   * A Panel which serves as the drawing area for the view.
   * 
   * @author Chakshu Sardana
   */
  class CanvasPanel extends JPanel {

    private static final long serialVersionUID = 4561239873456544601L;
    private int oldX;
    private int oldY;
    private Color currentColor;    
    private Image currentImage;
    private Graphics2D currentGraphics;

    /**
     * Creates a new object of CanvasPanel and attaches listeners to track of 
     * pressing and dragging of mouse on the drawing area  . 
     */
    public CanvasPanel() {
      currentColor = Color.BLACK;
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          modelReference.fireMouseClickedEvent(e.getX(), e.getY());
        }
      });
      addMouseMotionListener(new MouseAdapter() {
        public void mouseDragged(MouseEvent e) {
          modelReference.fireMouseDraggedEvent(e.getX(), e.getY());
        }
      });
      currentGraphics = (Graphics2D) getGraphics();
    }

    /*
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
      return new Dimension(500, 500);
    }

    /*
     * @see javax.swing.JComponent#paintComponent()
     */
    protected void paintComponent(Graphics g) {
      if (currentImage == null) {
        currentImage = createImage(getSize().width, getSize().height);
        currentGraphics = (Graphics2D) currentImage.getGraphics();
        clearCanvas();
      }
      g.drawImage(currentImage, 0, 0, null);
    }

    /**
     * Updates the current line color of the Panel graphics.
     * Any subsequent drawings will use this color. 
     * 
     * @param newColor The color to be updated as the new current color.
     * @return The current changed color.
     */
    private Color updateColor(Color newColor) {
      currentGraphics.setColor(newColor);
      return currentColor;
    }

    /**
     * Updates currentColor and the line color of the Panel graphics to RED.
     */
    private void redColor() {
      currentColor = Color.RED;
      updateColor(Color.RED);
    }

    /**
     * Updates currentColor and the line color of the Panel graphics to BLACK
     */
    private void blackColor() {
      currentColor = Color.BLACK;
      updateColor(Color.BLACK);
    }

    /**
     * Updates currentColor and the line color of the Panel graphics to BLUE
     */
    private void blueColor() {
      currentColor = Color.BLUE;
      updateColor(Color.BLUE);
    }

    /**
     * Updates currentColor and the line color of the Panel graphics to GREEN
     */
    private void greenColor() {
      currentColor = Color.GREEN;
      updateColor(Color.GREEN);
    }

    /**
     * Updates currentColor and the line color of the Panel graphics to WHITE
     */
    private void whiteColor() {
      currentColor = Color.WHITE;
      updateColor(Color.WHITE);
    }

    /**
     * Clears the canvas area.
     */
    private void clearCanvas() {
      currentGraphics.setColor(Color.WHITE);
      currentGraphics.fillRect(0, 0, getSize().width, getSize().height);
      currentGraphics.setColor(currentColor);
      repaint();
    }
  }
  
  // For testing, this method made default(not private)
  /**
   * A private method for changing colors to colorsAllowed. If the color passed 
   * as the argument is not present in the ColorsAvailable enum then by default,
   * BLACK color is returned.
   * 
   * @param color The color which needs to be mapped to ColorsAvailable enum
   * @return The mapped Color.
   * @throws IllegalArgumentException if the argument is null.
   */
  ColorsAllowed colorToColorsAllowed(Color color) {
    if (color == null) {
      throw new IllegalArgumentException("The given Color argument can't be null");
    }
    if (color == Color.RED) {
      return ColorsAllowed.RED;
    } else if (color == Color.BLUE) {
      return ColorsAllowed.BLUE;
    } else if (color == Color.GREEN) {
      return ColorsAllowed.GREEN;
    } else if (color == Color.WHITE) {
      return ColorsAllowed.WHITE;
    } else {
      return ColorsAllowed.BLACK;
    }
  }
  
  /**
   * @return Listener for color buttons to notify when the button is clicked.
   */
  private ActionListener getColorButtonsListener() {
    ActionListener colorButtonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        modelReference.fireDrawModeEvent();
        modelReference.fireColorChangeEvent(colorToColorsAllowed(clickedButton
            .getBackground()));
      }
    };
    return colorButtonListener;
  }

  /**
   * @return A new created panel which contains the eraser button.
   */
  private JPanel getEraserPanel() {
    JPanel eraserPanel = new JPanel();
    eraserPanel.setPreferredSize(new Dimension(100, 30));
    JButton eraser = new JButton("Eraser");
    eraser.setPreferredSize(new Dimension(80, 20));
    eraser.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modelReference.fireEraserClickedEvent();
      }
    });
    eraserPanel.add(eraser);   
    return eraserPanel;  
  } 
  
  /**
   * @return A new created panel which contains the clear button.
   */  
  private JPanel getClearPanel() {
    JPanel clearPanel = new JPanel();
    clearPanel.setPreferredSize(new Dimension(100, 30));
    JButton clear = new JButton("Clear");
    clear.setPreferredSize(new Dimension(80, 20));
    clear.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modelReference.fireCanvasClearEvent();
      }
    });
    clearPanel.add(clear);
    return clearPanel;
  }
  
  /**
   * @param color The color for which button has to be created.
   * @return A button of the specified color. 
   */
  private JButton getColorButton(Color color) {
      JButton button = new JButton();
      button.setBackground(color);
      button.setOpaque(true);
      button.setBorder(new LineBorder(Color.WHITE));
      button.setPreferredSize(new Dimension(50, 30));
      button.addActionListener(getColorButtonsListener());
      return button;
  }
  
  /**
   * @return A new created options panel which contains all the buttons for all
   *  the options i.e. the color buttons, the eraser and clear button.
   */
  private JPanel getOptionsPanel() {
    JPanel optionPanel = new JPanel(new FlowLayout());
    optionPanel.setPreferredSize(new Dimension(500, 50));
    optionPanel.add(getClearPanel());
    optionPanel.add(getColorButton(Color.BLACK));
    optionPanel.add(getColorButton(Color.RED));
    optionPanel.add(getColorButton(Color.BLUE));
    optionPanel.add(getColorButton(Color.GREEN));
    optionPanel.add(getEraserPanel());
    
    return optionPanel;
  }
  
  
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
    // for testing
    reset = true;
    canvas.clearCanvas();
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
    if (currentMode == Mode.ERASE) {
      canvas.currentGraphics.fillOval(x - 20, y - 20, 40, 40);
    }
    canvas.oldX = x;
    canvas.oldY = y;
  }

  @Override
  public void mouseDragged(int x, int y) {
    if (currentMode == Mode.DRAW) {
      canvas.currentGraphics.drawLine(canvas.oldX, canvas.oldY, x, y);
    } else if (currentMode == Mode.ERASE) {
      canvas.currentGraphics.fillOval(canvas.oldX - 20, 
          canvas.oldY - 20, 40, 40);
    }
    canvas.repaint();
    canvas.oldX = x;
    canvas.oldY = y;
  }

  @Override
  public void colorChange(ColorsAllowed changedColor) {
    if (changedColor == ColorsAllowed.RED) {
          canvas.redColor();
    } else if (changedColor == ColorsAllowed.BLUE) {
          canvas.blueColor();
    } else if (changedColor == ColorsAllowed.GREEN) {
          canvas.greenColor();
    } else if (changedColor == ColorsAllowed.WHITE) {
          canvas.whiteColor();
    } else {
          canvas.blackColor();
    }    
  }
  
  // for testing
  CanvasModel getModelReference() {
    return modelReference;
  }

  // for testing
  Mode getCurrentMode() {
    return currentMode;
  }

  // for testing
  void setCurrentMode(Mode m) {
    currentMode = m;
  }

  // for testing
  int getOldX() {
    return canvas.oldX;
  }

  // for testing
  int getOldY() {
    return canvas.oldY;
  }

  // for testing
  Color getCurrentColor() {
    return canvas.currentColor;
  }
  
}
