package pu.fmi.wordle.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Game {

  public enum GameState {
    ONGOING, WIN, LOSS
  }

  @Id
  @Column(name = "game_id")
  String id;

  String word;
  LocalDateTime startedOn;

  @Transient
  String alphabet = "абвгдежзийклмнопрстуфхцчшщъьюя";

  @Column(name = "matches")
  String alphabetMatches;

  int maxGuesses = 6;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "game_id")
  List<Guess> guesses;

  @Enumerated(EnumType.STRING)
  GameState state = GameState.ONGOING;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public LocalDateTime getStartedOn() {
    return startedOn;
  }

  public void setStartedOn(LocalDateTime startedOn) {
    this.startedOn = startedOn;
  }

  public int getMaxGuesses() {
    return maxGuesses;
  }

  public void setMaxGuesses(int maxGuesses) {
    this.maxGuesses = maxGuesses;
  }

  public List<Guess> getGuesses() {
    return guesses;
  }

  public void setGuesses(List<Guess> guesses) {
    this.guesses = guesses;
  }

  public String getAlphabet() {
    return alphabet;
  }

  public void setAlphabet(String alphabet) {
    this.alphabet = alphabet;
  }

  public String getAlphabetMatches() {
    return alphabetMatches;
  }

  public void setAlphabetMatches(String alphabetMatches) {
    this.alphabetMatches = alphabetMatches;
  }

  public GameState getState() {
    return state;
  }

  public void setState(GameState state) {
    this.state = state;
  }
}