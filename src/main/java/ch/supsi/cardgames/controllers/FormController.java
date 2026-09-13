package ch.supsi.cardgames.controllers;

import ch.supsi.cardgames.models.Card;
import ch.supsi.cardgames.models.Condition;
import ch.supsi.cardgames.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/card/new")
public class FormController {
    private final CardService cardService;

    @Autowired
    public FormController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public String formCard(Model model) {
        model.addAttribute("statoCarta",Condition.values());
        model.addAttribute("cartaNuova",new Card());

        return "cardSaleForm";
    }
    @PostMapping
    public String aggiungiCarta(
            @ModelAttribute Card card,
            @RequestParam("ImmagineCartaInput")MultipartFile file
    ) {
        this.cardService.salvaCarta(file,card);
        return "redirect:/";


    }






}
