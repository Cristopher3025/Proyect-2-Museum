package app.museo;

import app.museo.entities.Entradas;
import app.museo.entities.Entradassalas;
import app.museo.entities.Salas;
import app.museo.entities.Valoracionessalas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;
import java.util.List;

public class ValoracionesController {

    @FXML private ComboBox<Entradas> comboEntradas;
    @FXML private ComboBox<Salas> comboSalas;
    @FXML private Slider sliderEstrellas;
    @FXML private TextArea txtObservacion;
    @FXML private TableView<Valoracionessalas> tablaValoraciones;
    @FXML private TableColumn<Valoracionessalas, String> colSala;
    @FXML private TableColumn<Valoracionessalas, String> colObservacion;
    @FXML private TableColumn<Valoracionessalas, Number> colValoracion;
    @FXML private TextField txtCodigoQR;
    @FXML private Label lblSala, lblMuseo, lblTematica;
    @FXML private Label lblPromedio;
    @FXML private Button btnEnviar;

    private Entradas entradaActual;
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

        btnEnviar.setDisable(true);
        cargarValoraciones();
    }

    private void cargarValoraciones() {
        listaValoraciones = FXCollections.observableArrayList(
            em.createNamedQuery("Valoracionessalas.findAll", Valoracionessalas.class).getResultList()
        );
        tablaValoraciones.setItems(listaValoraciones);
    }

    @FXML
    private void buscarEntradaPorQR() {
        String codigo = txtCodigoQR.getText().trim();
        if (codigo.isEmpty()) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Código vacío", "Por favor ingresa un código QR.");
            return;
        }

        entradaActual = em.createQuery("SELECT e FROM Entradas e WHERE e.codigoqr = :codigo", Entradas.class)
                          .setParameter("codigo", codigo)
                          .getResultStream()
                          .findFirst()
                          .orElse(null);

        if (entradaActual == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("No encontrada", "No se encontró ninguna entrada con ese código.");
            lblSala.setText("");
            lblMuseo.setText("");
            lblTematica.setText("");
            btnEnviar.setDisable(true);
            return;
        }

        List<Entradassalas> salas = em.createQuery(
            "SELECT es FROM Entradassalas es WHERE es.entradaid = :entrada", Entradassalas.class)
            .setParameter("entrada", entradaActual)
            .getResultList();

        if (salas.isEmpty()) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Sin salas", "Esta entrada no está asociada a ninguna sala.");
            return;
        }

        Salas sala = salas.get(0).getSalaid();
        lblSala.setText("Sala: " + sala.getNombre());
        lblMuseo.setText("Museo: " + sala.getMuseoid().getNombre());
        comboEntradas.getSelectionModel().select(entradaActual);
        comboSalas.getSelectionModel().select(sala);
        btnEnviar.setDisable(false);
    }

    @FXML
    private void guardarValoracion() {
        Entradas entrada = comboEntradas.getValue();
        Salas sala = comboSalas.getValue();
        String observacion = txtObservacion.getText();
        Integer estrellas = (int) sliderEstrellas.getValue();

        if (entrada == null || sala == null || observacion.isEmpty() || estrellas == null) {
            Sonidos.reproducirSonidoError();
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
        mostrarAlerta("Registrado", "Valoración guardada correctamente.");

        cargarValoraciones();
        limpiarCampos();
    }

    private void limpiarCampos() {
        comboEntradas.getSelectionModel().clearSelection();
        comboSalas.getSelectionModel().clearSelection();
        txtObservacion.clear();
        sliderEstrellas.setValue(3);
        btnEnviar.setDisable(true);
    }

    @FXML
    private void cargarEntradaSeleccionada() {
        entradaActual = comboEntradas.getValue();
        if (entradaActual == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Entrada no válida", "Selecciona una entrada válida.");
            return;
        }

        List<Entradassalas> salas = em.createQuery(
            "SELECT es FROM Entradassalas es WHERE es.entradaid = :entrada", Entradassalas.class)
            .setParameter("entrada", entradaActual)
            .getResultList();

        if (!salas.isEmpty()) {
            Salas sala = salas.get(0).getSalaid();
            comboSalas.getSelectionModel().select(sala);
            lblSala.setText("Sala: " + sala.getNombre());
            lblMuseo.setText("Museo: " + sala.getMuseoid().getNombre());
            
            btnEnviar.setDisable(false);
        } else {
            lblSala.setText("");
            lblMuseo.setText("");
            lblTematica.setText("");
            btnEnviar.setDisable(true);
        }
    }


    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

