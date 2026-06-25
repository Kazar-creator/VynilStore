package VynilStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;

public class MainController {

    @FXML private FlowPane viniliGrid;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> genereFilter;
    @FXML private ComboBox<String> sortCombo;

    static ArrayList<Vinile> tuttiVinili = new ArrayList<>();

    @FXML
    public void initialize() {
        sortCombo.getItems().addAll(
                "Titolo (A→Z)",
                "Artista (A→Z)",
                "Anno (più recente)",
                "Prezzo (crescente)",
                "Prezzo (decrescente)"
        );

        tuttiVinili = Utils.fromJSONFile2ArrayListOfVinili("vinili.json");

        if (tuttiVinili == null) {
            tuttiVinili = new ArrayList<>();
        }

        popolaGeneri();
        mostraVinili(tuttiVinili);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> cercaEFiltra());
    }

    private void popolaGeneri() {
        genereFilter.getItems().clear();
        genereFilter.getItems().add("Tutti");
        for (Vinile v : tuttiVinili) {
            if (!genereFilter.getItems().contains(v.getGenere())) {
                genereFilter.getItems().add(v.getGenere());
            }
        }
        genereFilter.setValue("Tutti");
    }

    @FXML
    void cercaEFiltra() {
        String testo = searchField.getText().toLowerCase();
        String genere = genereFilter.getValue();

        ArrayList<Vinile> filtrati = new ArrayList<>();
        for (Vinile v : tuttiVinili) {
            boolean matchTesto = testo.isEmpty()
                    || v.getTitolo().toLowerCase().contains(testo)
                    || v.getArtista().toLowerCase().contains(testo);
            boolean matchGenere = genere == null || genere.equals("Tutti")
                    || v.getGenere().equalsIgnoreCase(genere);

            if (matchTesto && matchGenere) {
                filtrati.add(v);
            }
        }

        ordinaLista(filtrati);
        mostraVinili(filtrati);
    }

    private void ordinaLista(ArrayList<Vinile> lista) {
        String sort = sortCombo.getValue();
        if (sort == null) return;

        // Ordinamento semplice con if/else
        for (int i = 0; i < lista.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < lista.size(); j++) {
                Vinile a = lista.get(j);
                Vinile b = lista.get(minIdx);
                boolean minore = false;

                if (sort.equals("Titolo (A→Z)"))
                    minore = a.getTitolo().compareToIgnoreCase(b.getTitolo()) < 0;
                else if (sort.equals("Artista (A→Z)"))
                    minore = a.getArtista().compareToIgnoreCase(b.getArtista()) < 0;
                else if (sort.equals("Anno (più recente)"))
                    minore = a.getAnno() > b.getAnno();
                else if (sort.equals("Prezzo (crescente)"))
                    minore = a.getPrezzo() < b.getPrezzo();
                else if (sort.equals("Prezzo (decrescente)"))
                    minore = a.getPrezzo() > b.getPrezzo();

                if (minore) minIdx = j;
            }
            Vinile temp = lista.get(i);
            lista.set(i, lista.get(minIdx));
            lista.set(minIdx, temp);
        }
    }

    private void mostraVinili(ArrayList<Vinile> vinili) {
        viniliGrid.getChildren().clear();
        for (Vinile v : vinili) {
            viniliGrid.getChildren().add(creaCard(v));
        }
    }

    private VBox creaCard(Vinile v) {
        VBox card = new VBox(8);
        card.setPrefWidth(160);
        card.setPrefHeight(240);
        card.setStyle(
                "-fx-background-color: #141414;" +
                        "-fx-border-color: #2a2a2a;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 16;"
        );

        ImageView cover = new ImageView();
        cover.setFitWidth(128);
        cover.setFitHeight(90);
        cover.setPreserveRatio(true);

        InputStream imgStream = getClass().getResourceAsStream("/VynilStore/copertine/" + v.getImmagine());
        if (imgStream == null) {
            imgStream = getClass().getResourceAsStream("/VynilStore/logoVero.png");
        }
        if (imgStream != null) {
            cover.setImage(new Image(imgStream));
        }
        card.getChildren().add(cover);

        Label titoloLabel = new Label(v.getTitolo());
        titoloLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 12px; -fx-font-weight: bold;");
        titoloLabel.setWrapText(true);

        Label artistaLabel = new Label(v.getArtista());
        artistaLabel.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");

        Label dettagli = new Label(v.getAnno() + " · " + v.getGenere());
        dettagli.setStyle("-fx-text-fill: #555555; -fx-font-size: 10px;");

        Label prezzoLabel = new Label("€ " + v.getPrezzo());
        prezzoLabel.setStyle("-fx-text-fill: #c9a84c; -fx-font-size: 12px; -fx-font-weight: bold;");

        card.getChildren().addAll(titoloLabel, artistaLabel, dettagli, prezzoLabel);

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #1e1e1e;" +
                        "-fx-border-color: #c9a84c;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 16;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #141414;" +
                        "-fx-border-color: #2a2a2a;" +
                        "-fx-border-width: 1;" +
                        "-fx-padding: 16;"
        ));

        return card;
    }

    @FXML
    void handleAggiungi() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VynilStore/aggiungi.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Vynil Store - Aggiungi Vinile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleModifica() {
        if (tuttiVinili.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Catalogo vuoto", "Non ci sono vinili da modificare.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VynilStore/modifica.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Vynil Store - Modifica Vinile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleElimina() {
        if (tuttiVinili.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Catalogo vuoto", "Non ci sono vinili da eliminare.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VynilStore/elimina.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Vynil Store - Elimina Vinile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void handleHome()     {}
    @FXML void handleCatalogo() {}

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}