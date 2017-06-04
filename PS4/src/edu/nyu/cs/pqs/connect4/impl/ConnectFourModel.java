package edu.nyu.cs.pqs.connect4.impl;

import edu.nyu.cs.pqs.connect4.api.ConnectFourListener;
import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ModeAvailable;

import java.util.HashSet;
import java.util.Random;

/**
 * This class serves as the model of the Game. Any view to the game must be registered with this
 * model to get updates for the game. It provides the main logic for the game.
 * 
 * @author Chakshu Sardana
 */
public class ConnectFourModel {
  private HashSet<ConnectFourListener> listeners = new HashSet<>();
  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;
  private ConnectFourPlayer currentPlayer;
  private ConnectFourGameBoard currentGameBoard;
  private Integer result;

  // for Testing 
  boolean gameStartedEventFired = false;
  boolean gameEndedEventFired = false;
  boolean gameDrawEventFired = false;
  boolean illegalMoveEventFired = false;
  boolean nextPlayerEventFired = false;
  boolean resetGameEventFired = false;
  boolean notifyWinnerEventFired = false;
  boolean makeMoveEventFired = false;

  /**
   * This method fires the gameStarted event for every listener registered to the model. It also
   * sets the currentPlayer to player1 and initializes the currentBoardState.
   * 
   * @param mode
   */
  void fireGameStartedEvent(ModeAvailable mode) {
    gameStartedEventFired = true;
    for (ConnectFourListener listener : listeners) {
      listener.gameStarted(mode);
    }
    currentPlayer = player1;
    currentGameBoard = ConnectFourGameBoard.getConnectFourGameBoard();
  }

  /**
   * This method allows the listener to register with the model.
   * 
   * @param newListener new Listener to be registered
   * @throws IllegalArgumentException will be thrown if a null listener is passed.
   */
  void addListener(ConnectFourListener newListener) {
    if (newListener == null) {
      throw new IllegalArgumentException("Listener can't be null");
    }
    listeners.add(newListener);
  }

  /**
   * This method fires the gameEnded event for every listener registered to the model.
   */
  void fireGameEndedEvent() {
    gameEndedEventFired = true;
    for (ConnectFourListener listener : listeners) {
      listener.gameEnded();
    }
  }

  /**
   * This method fires the movePlayed event for every listener registered to the model. It also
   * updates the currentPlayer and checks every move to ensure whether the move is legal or if the
   * move resulted in a winner or a draw.
   * 
   * @param column The column on which the move was played. It should be within the grid size
   * @throws IllegalArgumentException thrown if invalid column(out of range of grid) is passed
   */
  void fireMakeMoveEvent(int column) {
    makeMoveEventFired = true;
    if (column < 0 || (column > ConnectFourInformation.COLUMNS - 1)) {
      throw new IllegalArgumentException("Column does not exist.");
    }
    if (currentGameBoard.updateBoard(column, currentPlayer.getPlayerId())) {
      int nextRow = currentGameBoard.getNextRow();
      for (ConnectFourListener listener : listeners) {
        listener.makeMove(nextRow, column, currentPlayer);
      }
      swapPlayers(currentPlayer);
      fireNextPlayerEvent(currentPlayer);
    } else {
      fireIllegalMoveEvent("Invalid column selected. Please select other column.");
    }
    checkForWinnerOrDraw();
    if (!currentPlayer.isHuman() && result == null) {
      int computerMove = currentGameBoard.getBestMoveForComputer();
      if (computerMove == -1) {
        computerMove = new Random().nextInt(ConnectFourInformation.COLUMNS);
        while (currentGameBoard.isColumnFull(computerMove)) {
          computerMove = new Random().nextInt(ConnectFourInformation.COLUMNS);
        }
      }
      if (currentGameBoard.updateBoard(computerMove, currentPlayer.getPlayerId())) {
        int nextRow = currentGameBoard.getNextRow();
        for (ConnectFourListener listener : listeners) {
          listener.makeMove(nextRow, computerMove, currentPlayer);
        }
        swapPlayers(currentPlayer);
        fireNextPlayerEvent(currentPlayer);
      } else {
        fireIllegalMoveEvent("Invalid column selected. Please select other column.");
      }
      checkForWinnerOrDraw();
    }
  }
  
  /**
   * This method is used to test if we have a winner or if game has resulted in a draw. 
   */
  private void checkForWinnerOrDraw() {
    if (currentGameBoard.checkforWinner()) {
      result = currentGameBoard.getWinner();
      fireNotifyWinnerEvent(result);
    } else if (currentGameBoard.isBoardFull()) {
      fireGameDrawEvent();
    }
  }
  
  /**
   * This method fires the illegalMove event for every listener registered to the model.
   * 
   * @param message
   */
  void fireIllegalMoveEvent(String message) {
    illegalMoveEventFired = true;
    for (ConnectFourListener listener : listeners) {
      listener.illegalMove(message);
    }
  }

  /**
   * This method fires the resetGame event for every listener registered to the model. It also
   * reinitializes the playboard and resets the current player.
   */
  void fireResetGameEvent() {
    resetGameEventFired = true;
    currentGameBoard.initializeBoard();
    currentPlayer = player1;
    result = null;
    for (ConnectFourListener listener : listeners) {
      listener.resetGame();
    }
  }

  /**
   * This method fires the gameDraw event for every listener registered to the model.It also
   * reinitializes the playboard and resets the current player.
   */
  void fireGameDrawEvent() {
    gameDrawEventFired = true;
    currentGameBoard.initializeBoard();
    currentPlayer = player1;
    result = null;
    for (ConnectFourListener listener : listeners) {
      listener.gameDraw();
    }
    fireGameEndedEvent();
  }

  /**
   * This method fires the notifyWinner event for every listener registered to the model. Further
   * after declaring the winner it ends the game.
   * 
   * @param winnerID
   */
  void fireNotifyWinnerEvent(int winnerID) {
    notifyWinnerEventFired = true;
    ConnectFourPlayer winner;
    if (winnerID == 1) {
      winner = player1;
    } else {
      winner = player2;
    }
    for (ConnectFourListener listener : listeners) {
      listener.notifyWinner(winner);
    }
    fireGameEndedEvent();
  }

  /**
   * Notifies each registered listener that the current player has changed.
   * 
   * @param nextPlayer The object of the next player
   */
  void fireNextPlayerEvent(ConnectFourPlayer nextPlayer) {
    nextPlayerEventFired = true;
    for (ConnectFourListener listener : listeners) {
      listener.nextPlayer(nextPlayer);
    }
  }

  /**
   * This method is used to sets player1 and player2.
   * 
   * @param player1 object of player1
   * @param player2 object of player2
   * @throws IllegalArgumentException if any of the players are null or if both the players passed
   *           have same ID's or same color.
   */
  void setPlayers(ConnectFourPlayer player1, ConnectFourPlayer player2) {
    if (player1 == null || player2 == null) {
      throw new IllegalArgumentException("Players can't be null");
    } else if (player1.getPlayerId() == player2.getPlayerId()) {
      throw new IllegalArgumentException("Players can't have same IDs");
    } else if (player1.getPlayerColor().equals(player2.getPlayerColor())) {
      throw new IllegalArgumentException("Players can't have same color");
    } else {
      this.player1 = player1;
      this.player2 = player2;
    }
  }

  /**
   * This method is used to swap player1 and player2
   * 
   * @param currentPlayer The object of current player
   */
  // For testing purposes making the method package-visible
  void swapPlayers(ConnectFourPlayer currentPlayer) {
    if (currentPlayer == null) {
      throw new IllegalArgumentException("Player can't be null");
    } else if (currentPlayer.getPlayerId() == player1.getPlayerId()) {
      this.currentPlayer = player2;
    } else {
      this.currentPlayer = player1;
    }
  }

  // For testing only
  ConnectFourPlayer getCurrentPlayer() {
    return currentPlayer;
  }

  // For testing only
  ConnectFourPlayer getPlayer1() {
    return player1;
  }

  // For testing only
  ConnectFourPlayer getPlayer2() {
    return player2;
  }

  // For testing only
  Integer getReseut() {
    return result;
  }

  // For testing only
  HashSet<ConnectFourListener> getListeners() {
    return listeners;
  }
}
