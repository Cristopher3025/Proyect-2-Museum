package app.museo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import app.museo.App;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class MenuController {
    @FXML
    private ComboBox<String> comboTemas;
    @FXML
    private Label labelFraseTematica;

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
    private void goToUsuarios(ActionEvent event) throws Exception {
        App.setRoot("usuarios");
    }

    @FXML
    private void goToRoles(ActionEvent event) throws Exception {
        App.setRoot("roles");
    }

    @FXML
    private void goToValidaciones(ActionEvent event) throws Exception {
        App.setRoot("validaciones");
    }

    @FXML
    private void cambiarTema() {
        String temaSeleccionado = comboTemas.getValue();
        String archivoTema = "claro.css";
        String frase = "";

        if ("Oscuro".equals(temaSeleccionado)) {
            archivoTema = "oscuro.css";
            frase = "";
        } else if ("Liguista".equals(temaSeleccionado)) {
            archivoTema = "liguista.css";
            frase = "🦁 Ser liguista no se explica, se siente.";
        } else if ("Saprissista".equals(temaSeleccionado)) {
            archivoTema = "saprissista.css";
            frase = "💜 Morado no se hace, se hereda.";
        }

        Scene scene = comboTemas.getScene();
        if (scene != null) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/" + archivoTema).toExternalForm());
        }

        if (labelFraseTematica != null) {
            labelFraseTematica.setText(frase);
        }
    }


    @FXML
    private void salir() {
        System.exit(0);
    }
}

