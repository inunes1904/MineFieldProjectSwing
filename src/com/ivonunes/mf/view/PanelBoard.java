package com.ivonunes.mf.view;

import com.ivonunes.mf.model.Board;

import javax.swing.*;
import java.awt.*;

public class PanelBoard extends JPanel {

  public PanelBoard(Board board){

    setLayout(new GridLayout(board.getLines(), board.getColumns()));

    board.forEachOne(f -> add(new ButtonField(f)));
    board.registerObserver(e -> {
      // TODO show the result to the user and reset if needed
    });
  }
}
