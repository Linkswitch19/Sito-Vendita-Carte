package ch.supsi.cardgames.controllers;

import ch.supsi.cardgames.models.Card;
import ch.supsi.cardgames.models.Condition;
import ch.supsi.cardgames.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/card/edit")
public class EditCardController {
    private final CardService cardService;

    @Autowired
    public EditCardController(CardService cardService) {
        this.cardService = cardService;
    }
    @GetMapping("/{idCarta}")
    public String editCard(@PathVariable int idCarta,Model model){
        model.addAttribute("statoCarta", Condition.values());
        model.addAttribute("carta",this.cardService.getCarta(idCarta));
        model.addAttribute("idCarta", idCarta);

        return "cardSaleFormEdit";

    }

  



}
