package pu.fmi.wordle.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pu.fmi.wordle.logic.GameService;

@Controller
public class HomeController {

  final GameService gameService;

  public HomeController(GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/")
  public String welcome(Model model) {
    var last10 = gameService.listLast10();
    model.addAttribute("last10", last10);
    return "index";
  }
}