package com.ivonunes.mf.view;

import com.ivonunes.mf.model.Board;

import javax.swing.*;

public class MainWindow extends JFrame {

  public MainWindow(){
    Board board = new Board(16,30,50);
    PanelBoard panelBoard = new PanelBoard(board);

    add(panelBoard);

    setTitle("Mine Field - Game");
    setSize(690,438);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }
  public static void main(String[] args) {

    new MainWindow();

  }
}
