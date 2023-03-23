package pu.fmi.wordle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Guess {
  public static final char PLACE_MATCH = 'P';
  public static final char LETTER_MATCH = 'L';
  public static final char NO_MATCH = 'N';

  @Id
  @Column(name = "guess_id")
  @GeneratedValue
  Long id;

  String word;
  LocalDateTime madeOn;
  String matches;

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public LocalDateTime getMadeOn() {
    return madeOn;
  }

  public void setMadeAt(LocalDateTime madeOn) {
    this.madeOn = madeOn;
  }

  public String getMatches() {
    return matches;
  }

  public void setMatches(String matches) {
    this.matches = matches;
  }
}