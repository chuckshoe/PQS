package edu.nyu.cs.pqs.connect4.impl;

import edu.nyu.cs.pqs.connect4.impl.ConnectFourApp;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourModel;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourView;

/**
 * This is the driver class that starts the Connect4 game.
 * 
 * @author Chakshu Sardana
 */
public class ConnectFourApp {

  /**
   * This method initializes the view object with a model and launches the game.
   */
  private void start() {
    ConnectFourModel m = new ConnectFourModel();
    ConnectFourView  v = new ConnectFourView(m);
    v.launchStartFrame();
  }

  public static void main(String[] args) {
    ConnectFourApp app = new ConnectFourApp();
    app.start();
  }
}
