package ch.supsi.cardgames.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Getter //fa le funzioni automaticamente
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {

    private String nome;
    private String copyright;
    @DateTimeFormat(pattern = "yyyy-MM-dd")// in automatico sceglie
    private Date date;
    private String author;
    @Enumerated(EnumType.STRING)
    private Condition condizione;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int nImmagine;

    public Card(String nome, String copyright, Date date, String author, Condition condizione, CardType cardType, String description, int nImmagine) {
        this.nome = nome;
        this.copyright = copyright;
        this.date = date;
        this.author = author;
        this.condizione = condizione;
        this.cardType = cardType;
        this.description = description;
        this.nImmagine = nImmagine;
    }

    public String[] getImmagini() {
        String[] immagine = new String[nImmagine];


        if (nImmagine<=0) return new String[]{"ChikaCard.png"};

        for (int i = 0; i < nImmagine; i++) {
            immagine[i] = this.nome+(i+1)+".png";
        }
        return immagine;

    }

    public void addImmagine(){
        this.nImmagine++;
        System.out.println(this.nImmagine);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setNome(String nome) {
        rinominaImmagini(this.nome,nome);
        this.nome = nome;

    }

    public String getBase64Image(String imagePath) {
        String uploadPath ="src/main/resources/static/immagini";


        if (imagePath == null || imagePath.isEmpty()) {
            return "";
        }

        try {
            // Path dell'imagine
            Path path = Paths.get(uploadPath, imagePath.substring(imagePath.lastIndexOf('/') + 1));

            byte[] fileContent = Files.readAllBytes(path);
            String base64 = Base64.getEncoder().encodeToString(fileContent);

            // Tipo
            return "data:image/png;base64," + base64;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Mon pesa sul db
     * @param vecchioNome
     * @param nuovoNome
     */
    public void rinominaImmagini(String vecchioNome, String nuovoNome) {
        String pathCartella = "src/main/resources/static/immagini/";
        for (int i = 0; i < nImmagine; i++) {
            Path vecchioPath = Paths.get(pathCartella + vecchioNome + (i + 1) + ".png");
            Path nuovoPath = Paths.get(pathCartella + nuovoNome + (i + 1) + ".png");
            if (Files.exists(vecchioPath)) {
                try {
                    Files.move(vecchioPath, nuovoPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }





}
