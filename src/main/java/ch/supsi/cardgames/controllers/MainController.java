package ch.supsi.cardgames.controllers;



import ch.supsi.cardgames.models.Card;
import ch.supsi.cardgames.models.CardType;
import ch.supsi.cardgames.models.Condition;
import ch.supsi.cardgames.models.User;
import ch.supsi.cardgames.service.CardService;
import ch.supsi.cardgames.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Controller
public class MainController {
    private final CardService cardService;
    private final UserService userService;

    @Autowired
    public MainController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("carte",cardService.getCarte());
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        //Tramite name posso creare l'oggetto senza usare th:object e field
        //non devo passare i model
        return "registrazione";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,Model model) throws BadRequestException {
        //qui uguale
        this.userService.createUser(user);

        return "registrazione";
    }

    @GetMapping("/login")
    public String login(){
        return "registrazione";
    }






}
