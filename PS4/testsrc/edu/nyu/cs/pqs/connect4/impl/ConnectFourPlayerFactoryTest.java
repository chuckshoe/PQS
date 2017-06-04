package edu.nyu.cs.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;

import org.junit.After;
import org.junit.Test;

public class ConnectFourPlayerFactoryTest {
  // Not Creating a setup method as only two players are allowed in the game

  @After
  public void tearDown() {
    ConnectFourPlayerFactory.setID(0);
  }

  @Test
  public void testConnectFourPlayerFactory() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.BLUE);
    ConnectFourPlayer human2 = ConnectFourPlayerFactory
        .getPlayer(true, "Tyrion", ColorAvailable.GREEN);
    assertTrue(human1.isHuman());
    assertTrue(human2.isHuman());
    assertEquals("Jon", human1.getPlayerName());
    assertEquals("Tyrion", human2.getPlayerName());
  }

  @Test
  public void testConnectFourComputerPlayer() {
    ConnectFourPlayer computer = ConnectFourPlayerFactory
        .getPlayer(false, "Computer", ColorAvailable.GREEN);
    assertEquals(2, computer.getPlayerId());
    assertEquals(ColorAvailable.GREEN, computer.getPlayerColor());
  }

  @Test
  public void testConnectFourComputerPlayer_SingletonBehavior() {
    ConnectFourPlayer computer = ConnectFourPlayerFactory
        .getPlayer(false, "Computer", ColorAvailable.GREEN);
    ConnectFourPlayer computer2 = ConnectFourPlayerFactory
        .getPlayer(false, "Computer2", ColorAvailable.BLUE);
    assertEquals(computer, computer2);
  }

  @Test (expected = IllegalStateException.class)
  public void testConnectFourPlayerFactory_MoreThanTwoHumanPlayers() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.BLUE);
    ConnectFourPlayer human2 = ConnectFourPlayerFactory
        .getPlayer(true, "Tyrion", ColorAvailable.GREEN);
    ConnectFourPlayer human3 = ConnectFourPlayerFactory
        .getPlayer(true, "Tywin", ColorAvailable.BLUE);
  }

  @Test (expected = IllegalStateException.class)
  public void testConnectFourPlayerFactory_TwoHumanPlayersAndComputerPlayer() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.BLUE);
    ConnectFourPlayer computer = ConnectFourPlayerFactory
        .getPlayer(false, "Computer", ColorAvailable.GREEN);
    ConnectFourPlayer human2 = ConnectFourPlayerFactory
        .getPlayer(true, "Sam", ColorAvailable.BLUE);
  }
}
