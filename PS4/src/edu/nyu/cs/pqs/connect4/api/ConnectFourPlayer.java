package edu.nyu.cs.pqs.connect4.api;

import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;

/**
 * This interface represents players of the Connect4 game. 
 * It provides basic information of a player like its ID, name and type.
 * 
 * @author Chakshu Sardana
 */
public interface ConnectFourPlayer {
  /**
   * @return ID of the player.
   */
  int getPlayerId();

  /**
   * This method is used to identify the type of player.
   * 
   * @return true if the player is human else false.
   */
  boolean isHuman();

  /**
   * @return name of the player.
   */
  String getPlayerName();

  /**
   * @return color assigned to the player.
   */
  ColorAvailable getPlayerColor();

}