package VynilStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ModificaController {

    @FXML private ComboBox<Vinile> sceltaVinile;
    @FXML private GridPane formGrid;
    @FXML private TextField fId;
    @FXML private TextField fTitolo;
    @FXML private TextField fArtista;
    @FXML private TextField fAnno;
    @FXML private TextField fGenere;
    @FXML private TextField fPrezzo;
    @FXML private TextField fImmagine;
    @FXML private Label messaggioLabel;

    @FXML
    public void initialize() {
        // Popola il ComboBox con tutti i vinili
        sceltaVinile.getItems().addAll(MainController.tuttiVinili);
    }

    @FXML
    private void handleSelezione() {
        Vinile v = sceltaVinile.getValue();
        if (v == null) return;

        // Pre-popola i campi con i dati del vinile selezionato
        fId.setText(v.getId());
        fTitolo.setText(v.getTitolo());
        fArtista.setText(v.getArtista());
        fAnno.setText(String.valueOf(v.getAnno()));
        fGenere.setText(v.getGenere());
        fPrezzo.setText(String.valueOf(v.getPrezzo()));
        fImmagine.setText(v.getImmagine());
        messaggioLabel.setText("");

        // Mostra il form
        formGrid.setVisible(true);
    }

    @FXML
    private void handleSalva() {
        Vinile selezionato = sceltaVinile.getValue();
        if (selezionato == null) {
            messaggioLabel.setText("Seleziona prima un vinile.");
            return;
        }

        if (fTitolo.getText().trim().isEmpty() || fArtista.getText().trim().isEmpty()) {
            messaggioLabel.setText("Titolo e artista sono obbligatori.");
            return;
        }

        int anno;
        float prezzo;
        try {
            anno   = Integer.parseInt(fAnno.getText().trim());
            prezzo = Float.parseFloat(fPrezzo.getText().trim());
        } catch (NumberFormatException e) {
            messaggioLabel.setText("Anno e prezzo devono essere numerici.");
            return;
        }

        // Aggiorna il vinile nella lista condivisa
        int idx = MainController.tuttiVinili.indexOf(selezionato);
        Vinile aggiornato = new Vinile(
            fId.getText().trim(),
            fTitolo.getText().trim(),
            fArtista.getText().trim(),
            anno,
            fGenere.getText().trim(),
            prezzo,
            fImmagine.getText().trim()
        );
        MainController.tuttiVinili.set(idx, aggiornato);
        Utils.vinile2JSONFile(MainController.tuttiVinili);

        tornaCatalogo();
    }

    @FXML
    private void handleTorna() {
        tornaCatalogo();
    }

    private void tornaCatalogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VynilStore/main.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);
            Stage stage = (Stage) sceltaVinile.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Vynil Store - Catalogo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
