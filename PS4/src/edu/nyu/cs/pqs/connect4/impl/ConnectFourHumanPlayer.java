package edu.nyu.cs.pqs.connect4.impl;

import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;

public class ConnectFourHumanPlayer implements ConnectFourPlayer {
  private final int playerID;
  private final String playerName;
  private final ColorAvailable playerColor;

  /**
   * This is the Builder class to generate ConnectFourHumanPlayer Objects. 
   * Player Name is a required parameter to builder class.
   * 
   * @author Chakshu Sardana
   */
  public static class Builder {
    private int playerID = -1;
    private String playerName = "";
    private ColorAvailable playerColor;

    /**
     * This method is used to set the Player's Name.
     * 
     * @param playerName The Name to be assigned. It can't be empty or null.
     */
    public Builder(String pName) {
      if (pName == null) {
        throw new IllegalArgumentException("Player name can't be null");
      } else if (pName.equals("")) {
        throw new IllegalArgumentException("Player name can't be empty");
      }
      playerName = pName;
    }

    /**
     * This method is used to set the Player's color.
     * 
     * @param playerColor The value of color to be assigned to player. It can't be null.
     * @return builder The object with playerColor added.
     */
    public Builder playerColor(ColorAvailable pColor) {
      if (pColor == null) {
        throw new IllegalArgumentException("Player color can't be null");
      }
      playerColor = pColor;
      return this;
    }

    /**
     * This method is used to set the Player's ID.
     * 
     * @param playerID ID to be set
     * @return builder object with playerID added
     */
    public Builder playerID(int pID) {
      if (pID < 0) {
        throw new IllegalArgumentException("Player ID can't be negative");
      }
      playerID = pID;
      return this;
    }

    /**
     * @return a new ConnectFourHumanPlayer object.
     */
    public ConnectFourHumanPlayer build() {
      return new ConnectFourHumanPlayer(this);
    }
  }

  private ConnectFourHumanPlayer(Builder builder) {
    this.playerID = builder.playerID;
    this.playerName = builder.playerName;
    this.playerColor = builder.playerColor;
  }

  @Override
  public boolean isHuman() {
    return true;
  }

  @Override
  public int getPlayerId() {
    return playerID;
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public ColorAvailable getPlayerColor() {
    return playerColor;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + playerID;
    result = prime * result + ((playerColor == null) ? 0 : playerColor.hashCode());
    result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }  
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }  
    ConnectFourHumanPlayer other = (ConnectFourHumanPlayer) obj;
    if ((playerColor != other.playerColor) || (playerID != other.playerID)) {
      return false;
    }
    if (playerName == null) {
      if (other.playerName != null) {
        return false;
      }
    } else if (!playerName.equals(other.playerName)) {
      return false;
    }
    return true;
  }

}

