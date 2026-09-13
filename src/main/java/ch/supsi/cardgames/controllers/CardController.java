package ch.supsi.cardgames.controllers;

import ch.supsi.cardgames.models.Card;
import ch.supsi.cardgames.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService=cardService;
    }
    @GetMapping("/{idCarta}")
    public String carta(@PathVariable int idCarta, Model model){
        model.addAttribute("carta",cardService.getCarta(idCarta));
        return "cartaSingola";
    }
    @GetMapping("/search")
    //@ResponseBody // per fare solo un js
    public String ricercaCarte(@RequestParam(defaultValue = "") final String query,Model model){
        List<Card> risultati = this.cardService.getCarteRicerca(query,query,query);// search?query=rin @RequestedParam
        model.addAttribute("carte", risultati);
        return "fragments/carte :: cardList"; //sostituisce solo il frammento


    }

    @GetMapping
    public String redirect() {
        return "redirect:/"; //ritorna home
    }

    @RequestMapping("/delete/{idCard}")
    public String cancellaCarta(@PathVariable int idCard){
        cardService.eliminaCarta(idCard);
        return redirect();
    }



}
