package com.ivonunes.mf.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class Board implements FieldObserver {

  private int lines;
  private int columns;

  private int mines;

  private final List<Field> fields = new ArrayList<>();
  private final List<Consumer<EventResult>> observers = new ArrayList<>();

  public void registerObserver(Consumer<EventResult> observer){
    observers.add(observer);
  }

  public void notifyObservers(boolean res){
    observers.stream()
      .forEach( o -> o.accept(new EventResult(res)));
  }

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

  private void showMines(){
    fields.stream().filter(c -> c.isMined())
            .filter( c -> !c.isMarked())
      .forEach(c -> c.setOpen(true));
  }

  public void markField(int line, int column){
    fields.parallelStream()
            .filter(c -> c.getLine() == line &&
                    c.getColumn() == column)
            .findFirst().ifPresent(c -> c.changeMarked());
  }

  private void generateFields() {
    // l = line c = column
    for (int l = 0; l < lines ; l++) {
      for (int c = 0; c < columns; c++) {
        Field newField = new Field(l, c);
        newField.addObserver(this);
        fields.add( newField);
      }
    }
  }

  public int getLines() {
    return lines;
  }

  public int getColumns() {
    return columns;
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


  @Override
  public void eventOccured(Field c, FieldEvent fE) {
    if (fE == FieldEvent.EXPLODE){
      System.out.println("You Lose!");
      showMines();
      notifyObservers(false);
    }
    else if (objectiveDone()){
      System.out.println("You Win!");
      System.out.println("Congratulations!");
      notifyObservers(true);
    }
  }

  public void forEachOne(Consumer<Field> func){
    fields.forEach(func);
  }
}
