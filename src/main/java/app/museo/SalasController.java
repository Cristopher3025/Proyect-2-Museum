package app.museo;

import app.museo.entities.Museos;
import app.museo.entities.Salas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SalasController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private ComboBox<Museos> comboMuseos;
    @FXML private TableView<Salas> tablaSalas;
    @FXML private TableColumn<Salas, String> colNombre;
    @FXML private TableColumn<Salas, String> colDescripcion;
    @FXML private TableColumn<Salas, String> colMuseo;

    private EntityManager em;

    private ObservableList<Salas> listaSalas;
    private ObservableList<Museos> listaMuseos;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaMuseos = FXCollections.observableArrayList(em.createNamedQuery("Museos.findAll", Museos.class).getResultList());
        comboMuseos.setItems(listaMuseos);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colMuseo.setCellValueFactory(cellData -> {
            Museos m = cellData.getValue().getMuseoid();
            return new javafx.beans.property.SimpleStringProperty(m != null ? m.getNombre() : "Sin museo");
            
        });

        cargarSalas();
    }

    private void cargarSalas() {
        List<Salas> salas = em.createNamedQuery("Salas.findAll", Salas.class).getResultList();
        listaSalas = FXCollections.observableArrayList(salas);
        tablaSalas.setItems(listaSalas);
    }

    @FXML
    private void agregarSala() {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        Museos museo = comboMuseos.getValue();

        if (nombre.isEmpty() || descripcion.isEmpty() || museo == null) {
            Sonidos.reproducirSonidoError();
            showAlert("Campos obligatorios", "Completa todos los campos.");
            return;
        }

        Salas nueva = new Salas();
        nueva.setNombre(nombre);
        nueva.setDescripcion(descripcion);
        nueva.setMuseoid(museo);

        em.getTransaction().begin();
        em.persist(nueva);
        em.getTransaction().commit();

        cargarSalas();
        limpiarCampos();
    }

    @FXML
    private void eliminarSala() {
        Salas seleccionada = tablaSalas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Sonidos.reproducirSonidoError();
            showAlert("Selección requerida", "Selecciona una sala para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Salas ref = em.merge(seleccionada);
        em.remove(ref);
        em.getTransaction().commit();

        cargarSalas();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        comboMuseos.getSelectionModel().clearSelection();
    }

    private void showAlert(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

