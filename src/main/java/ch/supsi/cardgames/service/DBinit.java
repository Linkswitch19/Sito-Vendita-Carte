package ch.supsi.cardgames.service;

import ch.supsi.cardgames.models.*;
import ch.supsi.cardgames.repository.CardRepository;

import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DBinit {

    private final CardRepository cardRepository;
    private final UserService userService;

    @Autowired
    public DBinit(CardRepository cardRepository ,UserService userService) {
        this.cardRepository = cardRepository;
        this.userService=userService;
    }

    @PostConstruct  // per assicurarsi che tutto sia creato bene
    private  void init(){
        if (cardRepository.count()!=0) return;
        creaUtenti();
        creaCarte();



    }

    private void creaUtenti(){
        try {
            this.userService.createUser(new User("peiti","andre", "linkswitch", UserRole.ADMIN,"linky"));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

    }

    private void creaCarte() {

        if (this.cardRepository.count() == 0) {// se non ci sono carte
            try {
                this.cardRepository.save(new Card(
                        "Rin",
                        "©TYPE-MOON, ufotable, FSNPC",
                        new SimpleDateFormat("yyyy-MM-dd").parse("2023-03-19"),
                        this.userService.getAllUSer().getFirst().getUsername(),
                        Condition.NUOVA,
                        CardType.ANIME,
                        "Rin Tohsaka (遠坂 凛, Hepburn: Tōsaka Rin) è un personaggio immaginario introdotto nella visual novel Fate/stay night del 2004, sviluppata da Type-Moon.",2
                ));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
