package app.museo;

import app.museo.entities.Entradas;
import app.museo.entities.Salas;
import app.museo.entities.Valoracionessalas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ValoracionesController {

    @FXML private ComboBox<Entradas> comboEntradas;
    @FXML private ComboBox<Salas> comboSalas;
    @FXML private Spinner<Integer> spinnerEstrellas;
    @FXML private TextArea txtObservacion;
    @FXML private TableView<Valoracionessalas> tablaValoraciones;
    @FXML private TableColumn<Valoracionessalas, String> colSala;
    @FXML private TableColumn<Valoracionessalas, String> colObservacion;
    @FXML private TableColumn<Valoracionessalas, Number> colValoracion;

    private EntityManager em;
    private ObservableList<Entradas> listaEntradas;
    private ObservableList<Salas> listaSalas;
    private ObservableList<Valoracionessalas> listaValoraciones;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaEntradas = FXCollections.observableArrayList(
            em.createNamedQuery("Entradas.findAll", Entradas.class).getResultList()
        );
        comboEntradas.setItems(listaEntradas);

        listaSalas = FXCollections.observableArrayList(
            em.createNamedQuery("Salas.findAll", Salas.class).getResultList()
        );
        comboSalas.setItems(listaSalas);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3);
        spinnerEstrellas.setValueFactory(valueFactory);

        colSala.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getSalaid() != null ? cellData.getValue().getSalaid().getNombre() : "Sin sala"
            )
        );
        colObservacion.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getObservacion()
            )
        );
        colValoracion.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getValoracion()
            )
        );

        cargarValoraciones();
    }

    private void cargarValoraciones() {
        listaValoraciones = FXCollections.observableArrayList(
            em.createNamedQuery("Valoracionessalas.findAll", Valoracionessalas.class).getResultList()
        );
        tablaValoraciones.setItems(listaValoraciones);
    }

    @FXML
    private void guardarValoracion() {
        Entradas entrada = comboEntradas.getValue();
        Salas sala = comboSalas.getValue();
        String observacion = txtObservacion.getText();
        Integer estrellas = spinnerEstrellas.getValue();

        if (entrada == null || sala == null || observacion.isEmpty() || estrellas == null) {
            mostrarAlerta("Campos obligatorios", "Completa todos los campos para registrar la valoración.");
            return;
        }

        Valoracionessalas v = new Valoracionessalas();
        v.setEntradaid(entrada);
        v.setSalaid(sala);
        v.setObservacion(observacion);
        v.setValoracion(estrellas.shortValue());
        v.setFecha(new Date());

        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();

        cargarValoraciones();
        limpiarCampos();
    }

    private void limpiarCampos() {
        comboEntradas.getSelectionModel().clearSelection();
        comboSalas.getSelectionModel().clearSelection();
        txtObservacion.clear();
        spinnerEstrellas.getValueFactory().setValue(3);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }
}


