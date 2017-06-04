package edu.nyu.cs.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.connect4.api.ConnectFourListener;
import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ModeAvailable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectFourModelTest {
  ConnectFourModel testModel;
  ConnectFourPlayer player1;
  ConnectFourPlayer player2;
  ConnectFourView testView;
  ConnectFourGameBoard playboard;

  @Before
  public void setup() {
    testModel = new ConnectFourModel();
    player1 = ConnectFourPlayerFactory.getPlayer(true, "Player1", ColorAvailable.BLUE);
    player2 = ConnectFourPlayerFactory.getPlayer(true, "Player2", ColorAvailable.GREEN);
    testModel.setPlayers(player1, player2);
    testModel.fireGameStartedEvent(ModeAvailable.SINGLEPLAYER);
    playboard = ConnectFourGameBoard.getConnectFourGameBoard();
    playboard.initializeBoard();
  }

  @After
  public void tearDown() {
    ConnectFourPlayerFactory.setID(0);
  }

  @Test
  public void testFireGameStartedEvent() {
    testModel.fireGameStartedEvent(ModeAvailable.SINGLEPLAYER);
    assertTrue(testModel.gameStartedEventFired);
    ConnectFourPlayer currentTestPlayer = testModel.getCurrentPlayer();
    assertTrue(currentTestPlayer.isHuman());
    assertEquals(currentTestPlayer, testModel.getPlayer1());
  }

  @Test
  public void testAddListener() {
    ConnectFourListener testListener = new ConnectFourView(testModel);
    testModel.addListener(testListener);
    assertEquals(1, testModel.getListeners().size());
  }

  @Test
  public void testFireMakeMoveEvent() {
    assertFalse(testModel.makeMoveEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.makeMoveEventFired);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFireMakeMoveEvent_negativeColumn() {
    testModel.fireMakeMoveEvent(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testFireMakeMoveEvent_outOfBoundColumn() {
    testModel.fireMakeMoveEvent(ConnectFourInformation.COLUMNS);
  }

  @Test
  public void testFireIllegalMove() {
    int tArray[][] = { { 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 2, 0, 1, 0 }, { 1, 0, 0, 2, 0, 2, 0 },
        { 1, 1, 0, 1, 0, 1, 0 }, { 1, 2, 2, 1, 2, 2, 0 }, { 1, 1, 1, 1, 2, 2, 0 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.illegalMoveEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.illegalMoveEventFired);
  }

  @Test
  public void testFireNextPlayerEvent() {
    assertFalse(testModel.nextPlayerEventFired);
    ConnectFourPlayer beforeMoveCurrentPlayer = testModel.getCurrentPlayer();
    testModel.fireMakeMoveEvent(1);
    ConnectFourPlayer afterMoveCurrentPlayer = testModel.getCurrentPlayer();
    assertTrue(testModel.nextPlayerEventFired);
    assertNotEquals(beforeMoveCurrentPlayer, afterMoveCurrentPlayer);
  }

  @Test
  public void testFireGameDrawEvent() {
    int tArray[][] = { { 0, 2, 1, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 },
        { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.gameDrawEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.gameDrawEventFired);
  }

  @Test
  public void testfireEndGameEvent_gameEndsAfterGameDrawEvent() {
    int tArray[][] = { { 0, 2, 1, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 },
        { 2, 1, 1, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 1, 1 }, { 2, 1, 1, 1, 2, 2, 2 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.gameDrawEventFired);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.gameDrawEventFired);
    assertTrue(testModel.gameEndedEventFired);
  }

  @Test
  public void testfireEndGameEvent_boardFull() {
    int tArray[][] = { { 1, 2, 2, 1, 2, 2, 1 }, { 1, 2, 1, 1, 2, 1, 2 }, { 2, 2, 1, 1, 1, 2, 1 },
        { 1, 1, 1, 2, 2, 2, 1 }, { 2, 1, 2, 1, 2, 2, 1 }, { 2, 2, 2, 1, 1, 1, 1 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMakeMoveEvent(4);
    assertTrue(testModel.gameEndedEventFired);
  }
  
  @Test
  public void testfireNotifyWinnerEvent() {
    int tArray[][] = { { 0, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.notifyWinnerEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.notifyWinnerEventFired);
  }

  @Test
  public void testfireNotifyWinnerEvent_boardFullAndPlayerWinsInLastMove() {
    int tArray[][] = { { 1, 2, 2, 2, 0, 1, 1 }, { 1, 2, 1, 1, 2, 1, 2 }, { 2, 2, 1, 1, 1, 2, 1 },
        { 1, 1, 1, 2, 2, 2, 1 }, { 2, 1, 2, 1, 2, 2, 1 }, { 2, 2, 2, 1, 1, 1, 1 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.notifyWinnerEventFired);
    testModel.fireMakeMoveEvent(4);
    assertFalse(testModel.gameDrawEventFired);
    assertTrue(testModel.notifyWinnerEventFired);
  }  
  
  @Test
  public void testfireEndGameEvent_gameEndAfterNotifyWinnerEvent() {
    int tArray[][] = { { 0, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.notifyWinnerEventFired);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.notifyWinnerEventFired);
    assertTrue(testModel.gameEndedEventFired);
  }

  @Test
  public void testfireEndGameEvent() {
    int tArray[][] = { { 0, 1, 1, 1, 0, 0, 0 }, { 1, 2, 1, 2, 0, 1, 0 }, { 2, 1, 1, 2, 0, 2, 0 },
        { 2, 2, 2, 1, 0, 1, 0 }, { 1, 2, 2, 1, 0, 2, 0 }, { 1, 0, 1, 1, 0, 0, 0 } };
    playboard.setBoard(tArray);
    assertFalse(testModel.gameEndedEventFired);
    testModel.fireMakeMoveEvent(0);
    assertTrue(testModel.gameEndedEventFired);
  }

  @Test
  public void testSetPlayers() {
    testModel.setPlayers(player2, player1);
    assertEquals(player1, testModel.getPlayer2());
    assertEquals(player2, testModel.getPlayer1());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetPlayers_nullPlayers() {
    testModel.setPlayers(null, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetPlayers_samePlayers() {
    testModel.setPlayers(player1, player1);
  }

  @Test
  public void testSwapPlayers() {
    ConnectFourPlayer initialPlayer = testModel.getCurrentPlayer();
    testModel.swapPlayers(initialPlayer);
    ConnectFourPlayer afterSwapPlayer = testModel.getCurrentPlayer();
    assertNotEquals(initialPlayer, afterSwapPlayer);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSwapPlayers_NullPlayers() {
    testModel.swapPlayers(null);
  }

  @Test
  public void testAddListener_addDuplicateListener() {
    ConnectFourListener testListener = new ConnectFourView(testModel);
    ConnectFourListener testListener2 = new ConnectFourView(testModel);
    testModel.addListener(testListener);
    testModel.addListener(testListener2);
    testModel.addListener(testListener);
    testModel.addListener(testListener2);
    assertEquals(2, testModel.getListeners().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddListener_addNullListener() {
    testModel.addListener(null);
  }

}
