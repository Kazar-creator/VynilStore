package VynilStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AggiungiController {

    @FXML private TextField fId;
    @FXML private TextField fTitolo;
    @FXML private TextField fArtista;
    @FXML private TextField fAnno;
    @FXML private TextField fGenere;
    @FXML private TextField fPrezzo;
    @FXML private TextField fImmagine;
    @FXML private Label messaggioLabel;

    @FXML
    private void handleSalva() {
        // Validazione campi
        if (fId.getText().trim().isEmpty() || fTitolo.getText().trim().isEmpty() || fArtista.getText().trim().isEmpty()) {
            messaggioLabel.setText("ID, titolo e artista sono obbligatori.");
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

        Vinile nuovoVinile = new Vinile(
            fId.getText().trim(),
            fTitolo.getText().trim(),
            fArtista.getText().trim(),
            anno,
            fGenere.getText().trim(),
            prezzo,
            fImmagine.getText().trim()
        );

        // Aggiunge alla lista condivisa e salva
        MainController.tuttiVinili.add(nuovoVinile);
        Utils.vinile2JSONFile(MainController.tuttiVinili);

        // Torna al catalogo
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
            Stage stage = (Stage) fTitolo.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Vynil Store - Catalogo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
