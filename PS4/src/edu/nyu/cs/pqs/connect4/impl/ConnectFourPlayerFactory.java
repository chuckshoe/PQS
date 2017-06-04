package edu.nyu.cs.pqs.connect4.impl;

import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;

/**
 * ConnectFourPlayerFactory is a Factory Class to get different kind of players. 
 * It is also used to set the properties of these players.
 * 
 * @author Chakshu Sardana
 */
public class ConnectFourPlayerFactory {
  static private Integer id = 0;

  /**
   * Creates and returns a new Player object. Maximum two Human players can be created using this
   * class. IllegalStateException will be thrown in case ore than two player are demanded.
   * 
   * @param isHuman used to decide the type of player to be returned
   * @param playerName The name of new player object.
   * @return a new player
   */
  public static ConnectFourPlayer getPlayer(boolean isHuman, 
      String playerName, ColorAvailable playerColor) {
    if (id < 2) {
      if (isHuman) {
        id++;
        return new ConnectFourHumanPlayer.Builder(playerName)
            .playerID(id).playerColor(playerColor).build();
      } else {
        id++;
        return ConnectFourComputerPlayer.getInstance(playerColor);
      }
    } else {
      throw new IllegalStateException("Maximum two players are allowed in the game");
    }
  }

  // For testing
  static void setID(int id) {
    ConnectFourPlayerFactory.id = id;
  }
}
