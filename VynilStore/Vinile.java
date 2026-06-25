package VynilStore;

import java.text.MessageFormat;

public class Vinile {

    private String id;
    private String titolo;
    private String artista;
    private int anno;
    private String genere;
    private float prezzo;
    private String immagine;

    public Vinile(String id, String titolo, String artista, int anno, String genere, float prezzo, String immagine) {
        this.id = id;
        this.titolo = titolo;
        this.artista = artista;
        this.anno = anno;
        this.genere = genere;
        this.prezzo = prezzo;
        this.immagine = immagine;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public int getAnno() { return anno; }
    public void setAnno(int anno) { this.anno = anno; }

    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }

    public float getPrezzo() { return prezzo; }
    public void setPrezzo(float prezzo) { this.prezzo = prezzo; }

    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Vinile'{'id={0}, titolo={1}, artista={2}, anno={3}, genere={4}, prezzo={5}, immagine={6}'}'",
                id, titolo, artista, anno, genere, prezzo, immagine
        );
    }
}