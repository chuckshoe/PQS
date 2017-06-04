package edu.nyu.cs.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectFourGameBoardTest {
  ConnectFourGameBoard playboard;
  int[][] testboard;

  @Before
  public void setup() {
    playboard = ConnectFourGameBoard.getConnectFourGameBoard();
    playboard.initializeBoard();
    testboard = playboard.getBoard();
  }

  @After
  public void tearDown() {
    playboard = null;
    testboard = null;
  }

  @Test
  public void testInitializeBoard() {
    playboard.initializeBoard();
    for (int[] i : testboard) {
      for (int j : i) {
        assertEquals(0, j);
      }
    }
  }

  @Test
  public void testUpdateBoard() {
    playboard.updateBoard(0, 1);
    for (int i = 0; i < ConnectFourInformation.ROWS; i++) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS; j++) {
        if ((i == ConnectFourInformation.ROWS - 1) && (j == 0)) {
          assertEquals(1, testboard[i][j]);
        } else
          assertEquals(0, testboard[i][j]);
      }
    }
  }

  @Test
  public void testGetNextAvailableRow() {
    testboard[ConnectFourInformation.ROWS - 1][0] = 1;
    assertEquals(ConnectFourInformation.ROWS - 2, playboard.getNextAvailableRow(0));
  }

  @Test (expected = ArrayIndexOutOfBoundsException.class)
  public void testGetNextAvailableRow_columnGreaterThanGrid() {
    playboard.getNextAvailableRow(7);
  }

  @Test
  public void testIsColumnFull() {
    for (int i = 0; i < ConnectFourInformation.ROWS; i++) {
      testboard[i][0] = 1;
    }
    playboard.setBoard(testboard);
    assertTrue(playboard.isColumnFull(0));
    assertFalse(playboard.isColumnFull(1));
  }

  @Test
  public void testCheckForAPlayerWin_bottomRow() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 0, 1, 0 }, { 0, 0, 0, 2, 0, 2, 0 },
        { 0, 1, 0, 1, 0, 1, 0 }, { 0, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 1, 2, 2, 0 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testCheckForAPlayerWin_topRow() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 1, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testCheckForAPlayerWin_rightMostColumn() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 2 },
        { 0, 0, 0, 0, 0, 0, 2 }, { 1, 0, 0, 0, 0, 1, 2 }, { 1, 1, 2, 1, 2, 1, 2 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(2, playboard.getWinner());
  }

  @Test
  public void testCheckForAPlayerWin_leftMostColumn() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 1, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 1, 1, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testCheckForAPlayerWin_northEastSouthWestDiagonal() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 1, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 1, 1, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 2, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(2, playboard.getWinner());
  }

  @Test
  public void testCheckForAPlayerWin_northWestSouthEastDiagonal() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0, 0, 0 },
        { 1, 1, 2, 2, 0, 0, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(2, playboard.getWinner());
  }

  @Test
  public void testCheckforWinner_noWinner() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 0, 1, 0 }, { 0, 1, 1, 2, 0, 2, 0 },
        { 1, 1, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 2, 2, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
  }

  @Test
  public void testCheckforWinner_noWinnerFullBoard() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 },
        { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
  }
  
  @Test
  public void testCheckforWinner_WinnerFullBoard() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 1, 2, 2, 2, 2, 1, 1 }, { 1, 2, 1, 1, 2, 1, 2 }, { 2, 2, 1, 1, 1, 2, 1 },
            { 1, 1, 1, 2, 2, 2, 1 }, { 2, 1, 2, 1, 2, 2, 1 }, { 2, 2, 2, 1, 1, 1, 1 } };
    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
  }  

  @Test
  public void testGetBestMoveForComputer_northWestSouthEastDiagonalPossiblity() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 1, 0, 0, 0 }, { 1, 2, 2, 1, 1, 2, 0 }, { 1, 1, 1, 2, 1, 1, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(2, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_rightMostColumnPossiblity() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 1 }, { 1, 2, 1, 1, 2, 0, 1 }, { 1, 1, 1, 2, 1, 0, 1 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(6, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_leftMostColumnPossiblity() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 2, 0, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0 },
        { 1, 1, 2, 2, 0, 0, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_bottomRowPossiblity() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 0, 1, 2, 2, 0, 0, 0 }, { 0, 2, 2, 1, 2, 2, 0 }, { 0, 1, 1, 1, 2, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_midRowWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 2, 2, 0, 2, 2, 0, 0 },
        { 2, 2, 1, 1, 1, 0, 2 }, { 1, 1, 1, 2, 1, 2, 1 }, { 1, 1, 2, 1, 1, 2, 2 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(2, playboard.getBestMoveForComputer());
  }  

  @Test
  public void testGetBestMoveForComputer_topRowPossiblity() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 1, 1, 1, 0, 0, 0, 0 }, { 1, 2, 1, 2, 0, 0, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 2, 1, 2, 1, 2, 1, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 2, 1, 2, 1, 2, 1 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_topRowWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 2, 2, 2, 0, 0, 0, 0 }, { 1, 2, 1, 2, 0, 0, 0 }, { 1, 1, 1, 2, 0, 2, 0 },
        { 2, 1, 2, 1, 2, 1, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 2, 1, 2, 1, 2, 1 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_southWestNorthEastDiagonalPossiblity() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 2, 0, 0, 0, 0 },
        { 2, 1, 1, 2, 0, 0, 0 }, { 2, 1, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 1, 1, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_northWestSouthEastDiagonalWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 2, 1, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 1, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(2, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_rightMostColumnWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 2 }, { 1, 2, 1, 1, 2, 0, 2 }, { 1, 1, 1, 2, 1, 0, 2 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(6, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_leftMostColumnWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 0 }, { 2, 2, 1, 1, 2, 2, 0 }, { 2, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_bottomRowWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 0, 1, 2, 2, 0, 0, 0 }, { 0, 2, 1, 1, 2, 2, 0 }, { 0, 2, 2, 2, 1, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(0, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetBestMoveForComputer_southWestNorthEastDiagonalWin() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0 },
        { 2, 1, 2, 2, 0, 0, 0 }, { 1, 2, 1, 1, 2, 2, 0 }, { 2, 1, 1, 2, 1, 2, 0 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(3, playboard.getBestMoveForComputer());
  }

  @Test
  public void testGetWinner() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0, 0, 0 },
        { 1, 0, 2, 2, 0, 0, 2 }, { 1, 0, 1, 1, 2, 0, 2 }, { 1, 0, 1, 2, 1, 0, 2 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.checkforWinner());
    assertEquals(1, playboard.getWinner());
  }

  @Test
  public void testGetWinner_noWinnerDuringGame() {
    assertFalse(playboard.checkforWinner());
    int tArray[][] = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 2, 0, 0, 0 }, { 1, 0, 0, 1, 0, 0, 2 }, { 1, 0, 1, 2, 1, 0, 2 } };

    playboard.setBoard(tArray);
    assertFalse(playboard.checkforWinner());
    assertEquals(-1, playboard.getWinner());
  }

  @Test
  public void testIsBoardFull() {
    assertFalse(playboard.isBoardFull());
    int tArray[][] = { { 1, 1, 2, 1, 1, 1, 2 }, { 2, 2, 1, 2, 2, 2, 1 }, { 1, 1, 2, 1, 1, 1, 2 },
        { 2, 2, 1, 2, 2, 2, 1 }, { 1, 1, 2, 1, 1, 1, 2 }, { 2, 2, 1, 2, 2, 2, 1 } };

    playboard.setBoard(tArray);
    assertTrue(playboard.isBoardFull());
  }
}
