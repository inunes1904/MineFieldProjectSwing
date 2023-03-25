package com.ivonunes.mf.view;

import com.ivonunes.mf.model.Field;
import com.ivonunes.mf.model.FieldEvent;
import com.ivonunes.mf.model.FieldObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonField extends JButton implements FieldObserver, MouseListener {

  private Field field;
  private final Color BG_STD = new Color(184,184,184);
  private final Color BG_EXPLOSION = new Color(189,66,68);
  private final Color TEXT_GREEN = new Color(0,100,0);

  public ButtonField(Field field){
    this.field = field;
    setBackground(BG_STD);
    setOpaque(true);
    setBorder(BorderFactory.createBevelBorder(0));
    addMouseListener(this);
    field.addObserver(this);
  }

  @Override
  public void eventOccured(Field c, FieldEvent fE) {
    switch (fE){
      case OPEN:
        applyStyleOpen();
        break;
      case MARK:
        applyStyleMark();
        break;
      case UNMARK:
        applyStyleUnMark();
        break;
      case EXPLODE:
        applyStyleExplode();
        break;
      default:
        applyStyleDefault();
    }
  }
  private void applyStyleDefault() {
    setBackground(BG_STD);
    setBorder(BorderFactory.createBevelBorder(0));
    setText("");
  }
  private void applyStyleExplode() {
    setBackground(BG_EXPLOSION);
    setText("X");
    setForeground(Color.BLACK);
  }
  private void applyStyleUnMark() {
    setBackground(BG_STD);
    setText("");
  }
  private void applyStyleMark() {
    setBackground(BG_STD);
    setText("\uD83D\uDEA9");
  }
  private void applyStyleOpen() {

    if (field.isMined()){
      setBackground(BG_EXPLOSION);
      setText("x");
      setForeground(Color.WHITE);
      return;
    }

    setBackground(BG_STD);
    setBorder(BorderFactory.createLineBorder(Color.GRAY));
    switch (field.minesInTheNeighbourhood()){
      case 1:
        setForeground(TEXT_GREEN);
        break;
      case 2:
        setForeground(Color.BLUE);
        break;
      case 3:
        setForeground(Color.YELLOW);
        break;
      case 4:
      case 5:
      case 6:
        setForeground(Color.RED);
        break;
      default:
        setForeground(Color.PINK);
        break;
    }
    String value = !field.safeNeighbour() ? field.minesInTheNeighbourhood()
                  + "":"";
    setText(value);
  }
  @Override
  public void mousePressed(MouseEvent e) {
      if(e.getButton()==1){
       //System.out.println("Left Button");
        field.open();
      }else{
        //System.out.println("Right Button");
        field.changeMarked();
      }
  }
  public void mouseClicked(MouseEvent e) { }
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }

}
