package app.museo;

import app.museo.entities.Epocas;
import app.museo.entities.Tematicas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TematicasController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCaracteristicas;
    @FXML private ComboBox<Epocas> comboEpoca;
    @FXML private TableView<Tematicas> tablaTematicas;
    @FXML private TableColumn<Tematicas, String> colNombre;
    @FXML private TableColumn<Tematicas, String> colCaracteristicas;
    @FXML private TableColumn<Tematicas, String> colEpoca;

    private EntityManager em;
    private ObservableList<Epocas> listaEpocas;
    private ObservableList<Tematicas> listaTematicas;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaEpocas = FXCollections.observableArrayList(
                em.createNamedQuery("Epocas.findAll", Epocas.class).getResultList()
        );
        comboEpoca.setItems(listaEpocas);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCaracteristicas.setCellValueFactory(new PropertyValueFactory<>("caracteristicas"));
        colEpoca.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getEpocaid() != null ? c.getValue().getEpocaid().getNombre() : "Sin época"
                )
        );

        cargarTematicas();
    }

    private void cargarTematicas() {
        listaTematicas = FXCollections.observableArrayList(
            em.createNamedQuery("Tematicas.findAll", Tematicas.class).getResultList()
        );
        tablaTematicas.setItems(listaTematicas);
    }

    @FXML
    private void agregarTematica() {
        String nombre = txtNombre.getText();
        String caracteristicas = txtCaracteristicas.getText();
        Epocas epoca = comboEpoca.getValue();

        if (nombre.isEmpty() || caracteristicas.isEmpty() || epoca == null) {
            mostrarAlerta("Campos requeridos", "Completa todos los campos.");
            return;
        }

        Tematicas t = new Tematicas();
        t.setNombre(nombre);
        t.setCaracteristicas(caracteristicas);
        t.setEpocaid(epoca);

        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();

        cargarTematicas();
        limpiar();
    }

    @FXML
    private void eliminarTematica() {
        Tematicas seleccionada = tablaTematicas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Seleccione una temática", "Debe seleccionar una temática para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Tematicas ref = em.merge(seleccionada);
        em.remove(ref);
        em.getTransaction().commit();

        cargarTematicas();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiar() {
        txtNombre.clear();
        txtCaracteristicas.clear();
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
