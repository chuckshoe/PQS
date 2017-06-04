package edu.nyu.cs.pqs.connect4.api;

import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ModeAvailable;

/**
 * This is an interface for the Connect4 Game listener. It contains methods that provide
 * information regarding the events happening in the game.
 * 
 * @author Chakshu Sardana
 */
public interface ConnectFourListener {
  /**
   * This method is used to start the game.
   * 
   * @param Mode The mode in which the game is started.
   */
  void gameStarted(ModeAvailable mode);

  /**
   * This method is used to provide information to the user about the next player.
   * 
   * @param nextPlayer
   */
  void nextPlayer(ConnectFourPlayer nextPlayer);

  /**
   * This method is used to notify the user of an illegal (not allowed) move.
   * 
   * @param message The message to be shown to the user
   */
  void illegalMove(String message);

  /**
   * The method used to make the move(highlight the cell) on the game 
   * board during the game.
   * 
   * @param row The row number of the move
   * @param column The column number of the move
   * @param player The player who makes the move.
   */
  void makeMove(int row, int column, ConnectFourPlayer player);

  /**
   * The method is used to declare the winner of the game.
   * 
   * @param winner The player who has won.
   */
  void notifyWinner(ConnectFourPlayer winner);

  /**
   * This method is used to declare that game has resulted in a draw.
   */
  void gameDraw();

  /**
   * This method is used to notify that the game has been reset.
   */
  void resetGame();

  /**
   * This method is used to notify that game has ended.
   */
  void gameEnded();
}
