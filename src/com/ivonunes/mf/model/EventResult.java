package com.ivonunes.mf.model;

public class EventResult {
  private final boolean winner;

  public EventResult(boolean win){
    this.winner = win;
  }

  public boolean isWin(){
    return winner;
  }
}
