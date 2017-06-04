package edu.nyu.cs.pqs.connect4.impl;

/**
 * This class represents the Connect4 Game Board. The class has methods 
 * to get/update/set cells in the board.
 * 
 * @author Chakshu Sardana
 */
public class ConnectFourGameBoard {
  private static ConnectFourGameBoard instance = null;
  private int nextRow;
  private int winnerID;
  private int board[][] = new int[ConnectFourInformation.ROWS][ConnectFourInformation.COLUMNS];
  
  private ConnectFourGameBoard() {
  }
  
  /**
   * Returns the Singleton object of the class ConnectFourGameBoard.
   */
  public static ConnectFourGameBoard getConnectFourGameBoard() {
    if (instance == null) {
      instance = new ConnectFourGameBoard();
    }
    return instance;
  }  

  /**
   * Initializes the board. All the cells are initially set to zero.
   */
  void initializeBoard() {
    for (int i = 0; i < ConnectFourInformation.ROWS; i++) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS; j++) {
        board[i][j] = 0;
      }
    }
  }

  /**
   * Updates the game board as the user makes a move.
   * 
   * @param column The column on which the move is made.
   * @param playerID The ID of the player making the move.
   * @return true if it is a valid move. Otherwise returns false.
   */
  boolean updateBoard(int column, int playerID) {
    for (int i = ConnectFourInformation.ROWS - 1; i >= 0; i--) {
      if (board[i][column] == 0) {
        nextRow = i;
        board[i][column] = playerID;
        return true;
      }
    }
    return false;
  }

  /**
   * Given the column, finds the next available row for the players move.
   * 
   * @param column The column where the player chooses to make the move.
   * @return The last available row for the move.
   */
  int getNextAvailableRow(int column) {
    for (int i = ConnectFourInformation.ROWS - 1; i >= 0; i--) {
      if (board[i][column] == 0) {
        return i;
      }  
    }
    return -1;
  }

  /**
   * Checks if a column is completely filled or not.
   * 
   * @param column The column to be checked.
   * @return true if the column is full, else false.
   */
  boolean isColumnFull(int column) {
    for (int i = 0; i < ConnectFourInformation.ROWS; i++) {
      if (board[i][column] == 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return The value of next free row.
   */
  int getNextRow() {
    return nextRow;
  }

  /**
   * Checks if a player has won the game by forming 
   * continuous sequence of length 4 in horizontal direction.
   * 
   * @param id The identifier of player to be checked if it's a winner.
   * @return True, if it wins, else false.
   */
  private boolean checkHorizontallyForWinner(int id) {
    int count = 0;
    for (int i = 0; i < ConnectFourInformation.ROWS; i++) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS; j++) {
        if (board[i][j] == id) {
          count++;
          if (count > 3) {
            winnerID = id;
            return true;
           }
        } else {
          count = 0;
        }
      }
      count = 0;
    }
    return false;
  }

  /**
   * Check if a player has won the game by forming 
   * continuous sequence of length 4 in vertical direction.
   * 
   * @param id The identifier of player to be checked if it's a winner.
   * @return True, if it wins, else false.
   */  
  private boolean checkVerticallyForWinner(int id) {
    int count = 0;
    for (int i = 0; i < ConnectFourInformation.COLUMNS; i++) {
      for (int j = 0; j < ConnectFourInformation.ROWS; j++) {
        if (board[j][i] == id) {
          count++;
          if (count > 3) {
            winnerID = id;
            return true;
          }
        } else {
          count = 0;
        }
      }
      count = 0;
    }
    return false;
  }
  
  /**
   * Checks if a player has won the game by forming 
   * continuous sequence of length 4 in diagonal direction.
   * 
   * @param id The identifier of player to be checked if it's a winner.
   * @return True, if it wins, else false.
   */
  private boolean checkDiagonallyForWinner(int id) {
    for (int i = 0; i < ConnectFourInformation.ROWS - 3; i++) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS - 3; j++) {
        if (board[i][j] == id && board[i + 1][j + 1] == id && board[i + 2][j + 2] == id
            && board[i + 3][j + 3] == id) {
          winnerID = board[i][j];
          return true;
        }
      }
    }

    for (int i = ConnectFourInformation.ROWS - 1; i > ConnectFourInformation.ROWS - 4; i--) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS - 3; j++) {
        if (board[i][j] == id && board[i - 1][j + 1] == id && board[i - 2][j + 2] == id
            && board[i - 3][j + 3] == id) {
          winnerID = board[i][j];
          return true;
        }
      }
    }    
    return false;
  }
  
  /**
   * Checks if a player has won the game. This check is done
   * by checking for continuous sequences of length 4 in horizontal, 
   * vertical and diagonal directions.
   * 
   * @param id The identifier of player to be checked if it's a winner.
   * @return True, if it wins, else false.
   */
  private boolean checkForAPlayerWin(int id) {
    boolean result = false;
    result = result || checkHorizontallyForWinner(id);
    result = result || checkVerticallyForWinner(id);
    result = result || checkDiagonallyForWinner(id);
    return result;
  }

  /**
   * Checks if a player has won the game or not.
   * 
   * @return true if there is a winner, else false.
   */
  boolean checkforWinner() {
    if (checkForAPlayerWin(1) || checkForAPlayerWin(2)) {
      return true;
    }
    winnerID = -1;
    return false;
  }

  /**
   * Checks if the game board is completely filled up or not.
   * 
   * @return true if the board is full, else false.
   */
  boolean isBoardFull() {
    for (int i = 0; i < ConnectFourInformation.COLUMNS; i++) {
      if (!isColumnFull(i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return ID of the winner.
   */
  int getWinner() {
    return winnerID;
  }

  /**
   * Calculates the best move for computer. It checks if a player is winning and
   * blocks the move. Also checks if the computer can win in the next move.
   * 
   * @return column for computers next move
   */
  int getBestMoveForComputer() {
    int result = -1;
    for (int j = 0; j < ConnectFourInformation.COLUMNS; j++) {
      int i = getNextAvailableRow(j);
      if (i == -1) {
        continue;
      }
      if (board[i][j] == 0) {
        board[i][j] = 2;
        if (checkforWinner()) {
          board[i][j] = 0;
          result = j;
        }
      }
      board[i][j] = 0;
    }
    if (result == -1) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS; j++) {
        int i = getNextAvailableRow(j);
        if (i == -1) {
          continue;
        }
        if (board[i][j] == 0) {
          board[i][j] = 1;
          if (checkforWinner()) {
            board[i][j] = 0;
            result = j;
          }
        }
        board[i][j] = 0;
      }
    }
    return result;
  }

  // For tests only
  void setBoard(int[][] testBoard) {
    board = testBoard;
  }

  // For tests only
  int[][] getBoard() {
    return board;
  }
}
