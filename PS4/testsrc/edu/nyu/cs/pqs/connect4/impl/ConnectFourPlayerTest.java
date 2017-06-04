package edu.nyu.cs.pqs.connect4.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;

import org.junit.After;
import org.junit.Test;

public class ConnectFourPlayerTest {

  @After
  public void tearUp() {
    ConnectFourPlayerFactory.setID(0);
  }

  @Test
  public void testConnectFourHumanPlayerObject() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.BLUE);
    ConnectFourPlayer human2 = ConnectFourPlayerFactory
        .getPlayer(true, "Tyrion", ColorAvailable.GREEN);
    assertTrue(human1.isHuman());
    assertTrue(human2.isHuman());
    assertEquals("Jon", human1.getPlayerName());
    assertEquals("Tyrion", human2.getPlayerName());
    assertEquals(1, human1.getPlayerId());
    assertEquals(2, human2.getPlayerId());
    assertTrue(human1.getPlayerColor() == ColorAvailable.BLUE);
    assertTrue(human2.getPlayerColor() == ColorAvailable.GREEN);
  }

  @Test
  public void testConnectFourHumanPlayerEquals() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.BLUE);
    ConnectFourPlayer human2 = ConnectFourPlayerFactory
        .getPlayer(true, "Tyrion", ColorAvailable.GREEN);
    assertFalse(human1.equals(human2));
  }

  @Test
  public void testConnectFourHumanPlayerEquals_sameName() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.BLUE);
    ConnectFourPlayer human2 = ConnectFourPlayerFactory
        .getPlayer(true, "Jon", ColorAvailable.GREEN);
    assertFalse(human1.equals(human2));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConnectFourHumanPlayerBuilder_emptyName() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory.getPlayer(true, "", ColorAvailable.BLUE);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConnectFourHumanPlayerBuilder_nullName() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory.getPlayer(true, null, ColorAvailable.BLUE);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConnectFourHumanPlayerBuilder_nullColor() {
    ConnectFourPlayer human1 = ConnectFourPlayerFactory.getPlayer(true, "John", null);
  }

  @Test
  public void testConnectFourComputerPlayerObject() {
    ConnectFourPlayer computer = ConnectFourPlayerFactory
        .getPlayer(false, "Computer", ColorAvailable.GREEN);
    assertTrue(computer.getPlayerColor() == ColorAvailable.GREEN);
    assertFalse(computer.isHuman());
    assertEquals(2, computer.getPlayerId());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConnectFourComputerPlayerBuilder_nullColor() {
    ConnectFourPlayer Computer = ConnectFourPlayerFactory.getPlayer(false, "Computer", null);
  }
}
