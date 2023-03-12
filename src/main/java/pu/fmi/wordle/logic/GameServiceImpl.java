package pu.fmi.wordle.logic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import pu.fmi.wordle.model.Game;
import pu.fmi.wordle.model.GameRepo;
import pu.fmi.wordle.model.Guess;
import pu.fmi.wordle.model.WordRepo;

@Component
public class GameServiceImpl implements GameService {

  final GameRepo gameRepo;

  final WordRepo wordRepo;

  public GameServiceImpl(GameRepo gameRepo, WordRepo wordRepo) {
    this.gameRepo = gameRepo;
    this.wordRepo = wordRepo;
  }

  @Override
  public Game startNewGame() {
    var game = new Game();
    game.setId(UUID.randomUUID().toString());
    game.setStartedOn(LocalDateTime.now());
    game.setWord(wordRepo.getRandom());
    game.setGuesses(new ArrayList<>(game.getMaxGuesses()));
    updateAlphabetMatches(game);
    gameRepo.save(game);
    return game;
  }

  @Override
  public Game getGame(String id) {
    var game = gameRepo.get(id);
    if (game == null) throw new GameNotFoundException(id);
    return game;
  }

  @Override
  public Game makeGuess(String id, String word) {
    var game = getGame(id);
    if (!wordRepo.exists(word)) throw new UnknownWordException(word);

    var guess = new Guess();
    guess.setWord(word);
    guess.setMadeAt(LocalDateTime.now());
    guess.setMatches(findMatches(game.getWord(), word));
    game.getGuesses().add(guess);
    updateAlphabetMatches(game);
    return game;
  }

  private String findMatches(String chosen, String given) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < given.length(); i++) {
      char cc = given.charAt(i);
      char mc = Guess.NO_MATCH;
      if (chosen.charAt(i) == cc) {
        mc = Guess.PLACE_MATCH;
      } else if (chosen.indexOf(cc) >= 0) {
        mc = Guess.LETTER_MATCH;
      }

      result.append(mc);
    }

    return result.toString();
  }

  private void updateAlphabetMatches(Game game) {
    StringBuilder result = new StringBuilder();
    game.getAlphabet()
        .chars()
        .map(letter -> getLetterMatch(game.getGuesses(), (char) letter))
        .forEach(letter -> result.append((char) letter));
    game.setAlphabetMatches(result.toString());
  }

  private char getLetterMatch(List<Guess> guesses, char letter) {
    char match = 'U'; // Not used yet
    for (var guess : guesses) {
      var word = guess.getWord();
      var matches = guess.getMatches();
      for (int i = 0; i < word.length(); i++) {
        if (word.charAt(i) == letter && match != Guess.PLACE_MATCH) {
          match = matches.charAt(i);
        }
      }
    }

    return match;
  }
}
