package ch.supsi.cardgames.service;

import ch.supsi.cardgames.models.Card;
import ch.supsi.cardgames.repository.CardRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service //posso usare atowired
public class CardService {
    private final CardRepository cardRepository;

    @Getter
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void add(Card carta){
        this.cardRepository.save(carta);

    }

    public List<Card> getCarte() {
        return this.cardRepository.findAll();
    }

    public Card getCarta(int id){

        return this.cardRepository.findById(id).orElse(null);
    }

    public List<Card> getCarteRicerca(String nome, String description, String author){
        return this.cardRepository.findCardsByNomeContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrAuthorContainingIgnoreCase(nome,description,author );
    }


    private String saveImage(MultipartFile file, Card card) {

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filename = card.getNome() + (card.getNImmagine() + 1) + ".png";

        Path path = Paths.get(this.getUploadPath(), filename);

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            card.addImmagine();
            this.cardRepository.save(card); //ricorda di aggiornare

        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio immagine", e);
        }

        return "/immagini/" + filename;
    }

    public String salvaCarta(MultipartFile file, Card card){

        if (getCarta(card.getId())!=null)  return aggiornaCarta(file,card);
        add(card);
        return saveImage(file,card);


    }



    private String aggiornaCarta (MultipartFile file,Card card){
        System.out.println("aggiornata");
        Card esistente= getCarta(card.getId());
        esistente.setAuthor(card.getAuthor());
        esistente.setDescription(card.getDescription());
        esistente.setDate(card.getDate());
        esistente.setNome(card.getNome());
        esistente.setCondizione(card.getCondizione());
        cardRepository.save(esistente); //aggiorna
        return "";
    }

    public void eliminaCarta(int id){
        this.cardRepository.deleteById(id);
    }
}
