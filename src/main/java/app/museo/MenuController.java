package app.museo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import app.museo.App;
import java.io.IOException;

public class MenuController {

    @FXML
    private void goToColecciones(ActionEvent event) throws Exception {
        App.setRoot("colecciones");
    }

    @FXML
    private void goToComisiones(ActionEvent event) throws Exception {
        App.setRoot("comisiones");
    }

    @FXML
    private void goToEntradas(ActionEvent event) throws Exception {
        App.setRoot("entradas");
    }

    @FXML
    private void goToEspecies(ActionEvent event) throws Exception {
        App.setRoot("especies");
    }

    @FXML
    private void goToMuseos(ActionEvent event) throws Exception {
        App.setRoot("museos");
    }

    @FXML
    private void goToPrecios(ActionEvent event) throws Exception {
        App.setRoot("precios");
    }

    @FXML
    private void goToReportes(ActionEvent event) throws Exception {
        App.setRoot("reportes");
    }

    @FXML
    private void goToSalas(ActionEvent event) throws Exception {
        App.setRoot("salas");
    }

    @FXML
    private void goToTematicas(ActionEvent event) throws Exception {
        App.setRoot("tematicas");
    }

    @FXML
    private void goToValoraciones(ActionEvent event) throws Exception {
        App.setRoot("valoraciones");
    }

    @FXML
    private void salir() {
        System.exit(0);
    }
}
