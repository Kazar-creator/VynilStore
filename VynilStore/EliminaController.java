package VynilStore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EliminaController {

    @FXML private ComboBox<Vinile> sceltaVinile;
    @FXML private VBox anteprimaBox;
    @FXML private Label titoloLabel;
    @FXML private Label artistaLabel;
    @FXML private Label dettagliLabel;
    @FXML private Label prezzoLabel;

    @FXML
    public void initialize() {
        sceltaVinile.getItems().addAll(MainController.tuttiVinili);
    }

    @FXML
    private void handleSelezione() {
        Vinile v = sceltaVinile.getValue();
        if (v == null) return;

        // Mostra anteprima del vinile da eliminare
        titoloLabel.setText(v.getTitolo());
        artistaLabel.setText(v.getArtista());
        dettagliLabel.setText(v.getAnno() + " · " + v.getGenere());
        prezzoLabel.setText("€ " + v.getPrezzo());
        anteprimaBox.setVisible(true);
    }

    @FXML
    private void handleElimina() {
        Vinile selezionato = sceltaVinile.getValue();
        if (selezionato == null) {
            showAlert(Alert.AlertType.INFORMATION, "Nessuna selezione", "Seleziona prima un vinile.");
            return;
        }

        MainController.tuttiVinili.remove(selezionato);
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
