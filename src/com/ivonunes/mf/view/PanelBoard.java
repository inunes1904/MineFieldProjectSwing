package com.ivonunes.mf.view;

import com.ivonunes.mf.model.Board;

import javax.swing.*;
import java.awt.*;

public class PanelBoard extends JPanel {

  public PanelBoard(Board board){

    setLayout(new GridLayout(board.getLines(), board.getColumns()));

    board.forEachOne(f -> add(new ButtonField(f)));
    board.registerObserver(e -> {
      SwingUtilities.invokeLater( ()->{
        if (e.isWin()){
          JOptionPane.showMessageDialog(this, "YOU WIN!\n\nCONGRATULATIONS!");
        }else {
          JOptionPane.showMessageDialog(this, "YOU LOSE!");
        }
        board.reset();
      });
    });
  }
}
