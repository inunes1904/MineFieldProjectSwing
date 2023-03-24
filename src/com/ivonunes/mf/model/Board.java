package com.ivonunes.mf.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class Board {

  private int lines;
  private int columns;

  private int mines;

  private final List<Field> fields = new ArrayList<>();

  public Board(int lines, int columns, int mines) {
    this.lines = lines;
    this.columns = columns;
    this.mines = mines;

    generateFields();
    joinNeighbours();
    generateMines();
  }

  public void openField(int line, int column){
      /*  */
    try{
      fields.parallelStream()
            .filter(c -> c.getLine() == line &&
            c.getColumn() == column)
            .findFirst().ifPresent(c -> c.open());
    }catch(Exception e){
      // FIXME adjust implementation
      fields.forEach(c -> c.setOpen(true));
      throw e;
    }
  }

  public void markField(int line, int column){
    fields.parallelStream()
            .filter(c -> c.getLine() == line &&
                    c.getColumn() == column)
            .findFirst().ifPresent(c -> c.changeMarked());
  }

  private void generateFields() {
    for (int i = 0; i < lines ; i++) {
      for (int j = 0; j < columns; j++) {
        fields.add( new Field(i,j));
      }
    }
  }

  private void joinNeighbours() {
    for (Field f: fields){
      for (Field f2: fields){
          f.addNeighbour(f2);
      }
    }
  }
  private void generateMines() {
    long activatedMines = 0;
    Predicate<Field> mined = f -> f.isMined();
    do {
      int randomNumber = (int) (Math.random() * fields.size());
      fields.get(randomNumber).setMine();
      activatedMines = fields.stream().filter(mined).count();
    }while(activatedMines<mines);
  }

  public boolean objectiveDone(){
    return fields.stream().allMatch(c->c.objectiveDone());
  }

  public void reset(){
    fields.stream().forEach(c-> c.reset());
    generateMines();
  }


}
