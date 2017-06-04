package edu.nyu.cs.pqs.connect4.impl;

import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;

/**
 * This class defines the computer player for the Connect4 game. 
 * This is a singleton class as there can only be one computer
 * player in the game.
 * 
 * @author Chakshu Sardana
 */
class ConnectFourComputerPlayer implements ConnectFourPlayer {
  private static ConnectFourComputerPlayer instance = null;
  private ColorAvailable color;

  private ConnectFourComputerPlayer(ColorAvailable color) {
    this.color = color;
  }

  /**
   * This method provides the computer player instance.
   * 
   * @param color The color assigned to the player.
   * @return The Object of Computer player.
   * @throws IllegalArgumentException if the color that is passed is null.
   */
  public static ConnectFourComputerPlayer getInstance(ColorAvailable color) {
    if (color == null) {
      throw new IllegalArgumentException("Color can't be null");
    }
    if (instance == null) {
      instance = new ConnectFourComputerPlayer(color);
    }
    return instance;
  }

  @Override
  public boolean isHuman() {
    return false;
  }

  @Override
  public int getPlayerId() {
    return 2;
  }

  @Override
  public String getPlayerName() {
    return "Computer";
  }

  @Override
  public ColorAvailable getPlayerColor() {
    return color;
  }

}
