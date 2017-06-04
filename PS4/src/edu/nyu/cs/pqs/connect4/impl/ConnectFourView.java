package edu.nyu.cs.pqs.connect4.impl;

import edu.nyu.cs.pqs.connect4.api.ConnectFourListener;
import edu.nyu.cs.pqs.connect4.api.ConnectFourPlayer;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ColorAvailable;
import edu.nyu.cs.pqs.connect4.impl.ConnectFourInformation.ModeAvailable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Provides GUI for the user. It implements the
 * ConnectFourListener which enables it to connect to the model of Connect4.
 * 
 * @author Chakshu Sardana
 */
public class ConnectFourView implements ConnectFourListener {
  private JFrame mainFrame;
  private JFrame startFrame;
  private JPanel gridPanel;
  private JPanel mainFrameTopPanel;
  private JLabel messageLabel;
  private ConnectFourModel model;
  private JButton resetButton;
  private JButton gridButtons[][];

  public ConnectFourView(ConnectFourModel m) {
    model = m;
    model.addListener(this);
    gridButtons = new JButton[ConnectFourInformation.ROWS][ConnectFourInformation.COLUMNS];
  }

  /**
   * Provides an ActionListener to every button on the grid which makes it possible to
   * track which column user hits.
   * 
   * @return ActionListener to all the grid buttons
   */
  public ActionListener gridButtonActionListener() {
    ActionListener buttonListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent gridButtonActionEvent) {
        JButton clickedButton = (JButton) gridButtonActionEvent.getSource();
        Integer columnNumber = (Integer) clickedButton.getClientProperty("Column");
        model.fireMakeMoveEvent(columnNumber);
      }
    };
    return buttonListener;
  }

  /**
   * Provides an ActionListener to the start screen buttons making it possible to track
   * the mode of the game chosen by the user.
   * 
   * @return Action listener to both the buttons on the start screen
   */
  public ActionListener startButtonListener() {
    ActionListener startListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent startButtonActionEvent) {
        ConnectFourInformation.ModeAvailable mode = null;
        JButton clickedButton = (JButton) startButtonActionEvent.getSource();
        if (clickedButton.getText().equalsIgnoreCase("Human Vs Human")) {
          ConnectFourPlayer p1 = ConnectFourPlayerFactory
              .getPlayer(true, "Player1", ColorAvailable.BLUE);
          ConnectFourPlayer p2 = ConnectFourPlayerFactory
              .getPlayer(true, "Player2", ColorAvailable.GREEN);
          model.setPlayers(p1, p2);
          mode = ModeAvailable.MULTIPLAYER;
        } else if (clickedButton.getText().equalsIgnoreCase("Human Vs Computer")) {
          model.setPlayers(ConnectFourPlayerFactory
              .getPlayer(true, "Player", ColorAvailable.BLUE),
              ConnectFourPlayerFactory.getPlayer(false, "Computer", ColorAvailable.GREEN));
          mode = ModeAvailable.SINGLEPLAYER;
        }
        startFrame.dispose();
        launchMainFrame();
        model.fireGameStartedEvent(mode);
      }
    };
    return startListener;
  }

  /**
   * Provides an ActionListener to keep a track of when user hits the reset button in
   * the game.
   * 
   * @return ActionListener to the reset button
   */
  public ActionListener resetButtonListener() {
    ActionListener resetListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        model.fireResetGameEvent();
      }
    };
    return resetListener;
  }

  /**
   * Provides a panel consisting of all the grid buttons.
   * 
   * @return Panel of JButtons
   */
  public JPanel mainFrameBoardPanel() {
    gridPanel = new JPanel(new GridLayout(ConnectFourInformation.ROWS, 
        ConnectFourInformation.COLUMNS));
    for (int i = 0; i < ConnectFourInformation.ROWS; i++) {
      for (int j = 0; j < ConnectFourInformation.COLUMNS; j++) {
        JButton button = new JButton();
        button.putClientProperty("Column", j);
        button.setEnabled(false);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.addActionListener(gridButtonActionListener());
        gridButtons[i][j] = button;
        gridPanel.add(button);
      }
    }
    return gridPanel;
  }

  /**
   * Provides a panel consisting of a message label and reset button.
   * 
   * @return panel with a reset JButton and message Label
   */
  public JPanel mainFrameTopPanel() {
    mainFrameTopPanel = new JPanel(new BorderLayout());
    messageLabel = new JLabel("Welcome to Connect4! Please choose a mode to play.");
    resetButton = new JButton("Reset Game");
    resetButton.addActionListener(resetButtonListener());
    resetButton.setEnabled(false);
    mainFrameTopPanel.add(resetButton, BorderLayout.EAST);
    mainFrameTopPanel.add(messageLabel, BorderLayout.WEST);
    return mainFrameTopPanel;
  }

  /**
   * Launches the Main Frame where the game will actually be played.
   */
  public void launchMainFrame() {
    mainFrame = new JFrame("Connect4");
    mainFrame.setSize(700, 600);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setResizable(false);
    mainFrame.setLayout(new BorderLayout());
    mainFrame.add(mainFrameBoardPanel(), BorderLayout.CENTER);
    mainFrame.add(mainFrameTopPanel(), BorderLayout.NORTH);
    mainFrame.setVisible(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Launches the start frame to ask for the mode of the game.
   */
  public void launchStartFrame() {
    startFrame = new JFrame("Connect Four - Please Choose Mode:");
    startFrame.setSize(300, 150);
    startFrame.setResizable(false);
    startFrame.setLocationRelativeTo(null);
    startFrame.setLayout(new BorderLayout());
    JPanel startFrameBottomPanel = new JPanel(new GridLayout());
    JPanel startFrameTopPanel = new JPanel();
    JLabel startFrameImage = new JLabel(new ImageIcon("resources//connect4.jpg"));
    startFrameTopPanel.add(startFrameImage);
    JButton humanButton = new JButton("Human Vs Human");
    JButton computerButton = new JButton("Human Vs Computer");    
    computerButton.addActionListener(startButtonListener());
    humanButton.addActionListener(startButtonListener());
    startFrameBottomPanel.add(computerButton);
    startFrameBottomPanel.add(humanButton);
    startFrame.add(startFrameTopPanel, BorderLayout.NORTH);
    startFrame.add(startFrameBottomPanel, BorderLayout.SOUTH);
    startFrame.pack();
    startFrame.setVisible(true);
    startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void gameStarted(ModeAvailable mode) {
    messageLabel.setText("Game Started In " + mode + " Mode!! Lets Play ");
    for (JButton[] buttonArray : gridButtons) {
      for (JButton button : buttonArray) {
        button.setEnabled(true);
      }
    }
    resetButton.setEnabled(true);
  }

  @Override
  public void nextPlayer(ConnectFourPlayer nextPlayer) {
    messageLabel.setText(nextPlayer.getPlayerName() + "'s Turn");
  }

  @Override
  public void illegalMove(String message) {
    messageLabel.setText(message);
  }

  @Override
  public void makeMove(int row, int column, ConnectFourPlayer player) {
    if (player.getPlayerColor() == ColorAvailable.BLUE) {
      gridButtons[row][column].setBackground(Color.BLUE);
    } else {
      gridButtons[row][column].setBackground(Color.GREEN);
    }
  }

  @Override
  public void notifyWinner(ConnectFourPlayer winner) {
    JOptionPane.showMessageDialog(null, winner.getPlayerName() + " has won the game");
  }

  @Override
  public void gameEnded() {
    for (JButton[] buttonRow : gridButtons) {
      for (JButton button : buttonRow) {
        button.setEnabled(false);
      }
    }
    messageLabel.setText("Click 'Reset Game' button to play again.");
  }

  @Override
  public void resetGame() {
    for (JButton[] buttonRow : gridButtons) {
      for (JButton button : buttonRow) {
        button.setBackground(Color.WHITE);  
        button.setEnabled(true);
      }
    }
    messageLabel.setText("New Game Started");
  }

  @Override
  public void gameDraw() {
    JOptionPane.showMessageDialog(null, "Game ended in a draw. Try Again!");
  }
}
