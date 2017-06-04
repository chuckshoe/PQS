package edu.nyu.cs.pqs.connect4.impl;

/**
 * This class keeps tracks of the constants of the game. 
 * 
 * @author Chakshu Sardana
 */
public class ConnectFourInformation {
  public static final Integer ROWS = 6;
  public static final Integer COLUMNS = 7;

  public static enum ColorAvailable {
    BLUE, GREEN
  };

  public static enum ModeAvailable {
    SINGLEPLAYER, MULTIPLAYER
  };
}
