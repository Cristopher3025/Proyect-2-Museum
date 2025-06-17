package app.museo;

import app.museo.entities.Colecciones;
import app.museo.entities.Epocas;
import app.museo.entities.Especies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class EspeciesController {

    @FXML private TextField txtNombreCientifico;
    @FXML private TextField txtNombreComun;
    @FXML private TextField txtFechaExtincion;
    @FXML private TextField txtPeso;
    @FXML private TextField txtTamano;
    @FXML private TextField txtCaracteristicas;
    @FXML private ComboBox<Colecciones> comboColeccion;
    @FXML private ComboBox<Epocas> comboEpoca;
    @FXML private TableView<Especies> tablaEspecies;
    @FXML private TableColumn<Especies, String> colCientifico;
    @FXML private TableColumn<Especies, String> colComun;
    @FXML private TableColumn<Especies, String> colFecha;
    @FXML private TableColumn<Especies, BigDecimal> colPeso;
    @FXML private TableColumn<Especies, BigDecimal> colTamano;
    @FXML private TableColumn<Especies, String> colEpoca;
    @FXML private TableColumn<Especies, String> colColeccion;

    private EntityManager em;
    private ObservableList<Especies> listaEspecies;
    private ObservableList<Epocas> listaEpocas;
    private ObservableList<Colecciones> listaColecciones;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaEpocas = FXCollections.observableArrayList(em.createNamedQuery("Epocas.findAll", Epocas.class).getResultList());
        listaColecciones = FXCollections.observableArrayList(em.createNamedQuery("Colecciones.findAll", Colecciones.class).getResultList());

        comboEpoca.setItems(listaEpocas);
        comboColeccion.setItems(listaColecciones);

        colCientifico.setCellValueFactory(new PropertyValueFactory<>("nombrecientifico"));
        colComun.setCellValueFactory(new PropertyValueFactory<>("nombrecomun"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaextincion"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colTamano.setCellValueFactory(new PropertyValueFactory<>("tamano"));
        colEpoca.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getEpocaid() != null ? c.getValue().getEpocaid().getNombre() : "Sin época"));
        colColeccion.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getColeccionid() != null ? c.getValue().getColeccionid().getNombre() : "Sin colección"));

        cargarEspecies();
    }

    private void cargarEspecies() {
        listaEspecies = FXCollections.observableArrayList(
            em.createNamedQuery("Especies.findAll", Especies.class).getResultList()
        );
        tablaEspecies.setItems(listaEspecies);
    }

    @FXML
    private void agregarEspecie() {
        if (txtNombreCientifico.getText().isEmpty() || txtNombreComun.getText().isEmpty() ||
            txtFechaExtincion.getText().isEmpty() || txtPeso.getText().isEmpty() ||
            txtTamano.getText().isEmpty() || txtCaracteristicas.getText().isEmpty() ||
            comboEpoca.getValue() == null || comboColeccion.getValue() == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Campos incompletos", "Todos los campos son obligatorios.");
            return;
        }

        Especies e = new Especies();
        e.setNombrecientifico(txtNombreCientifico.getText());
        e.setNombrecomun(txtNombreComun.getText());
        e.setFechaextincion(txtFechaExtincion.getText());
        e.setPeso(new BigDecimal(txtPeso.getText()));
        e.setTamano(new BigDecimal(txtTamano.getText()));
        e.setCaracteristicas(txtCaracteristicas.getText());
        e.setEpocaid(comboEpoca.getValue());
        e.setColeccionid(comboColeccion.getValue());

        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();

        cargarEspecies();
        limpiar();
    }

    @FXML
    private void eliminarEspecie() {
        Especies seleccionada = tablaEspecies.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Seleccione una especie", "Debe seleccionar un elemento para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Especies ref = em.merge(seleccionada);
        em.remove(ref);
        em.getTransaction().commit();

        cargarEspecies();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiar() {
        txtNombreCientifico.clear();
        txtNombreComun.clear();
        txtFechaExtincion.clear();
        txtPeso.clear();
        txtTamano.clear();
        txtCaracteristicas.clear();
        comboColeccion.getSelectionModel().clearSelection();
        comboEpoca.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
