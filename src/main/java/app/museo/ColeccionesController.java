package app.museo;

import app.museo.entities.Colecciones;
import app.museo.entities.Salas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ColeccionesController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtSiglo;
    @FXML private TextField txtObservacion;
    @FXML private ComboBox<Salas> comboSala;
    @FXML private TableView<Colecciones> tablaColecciones;
    @FXML private TableColumn<Colecciones, String> colNombre;
    @FXML private TableColumn<Colecciones, String> colSiglo;
    @FXML private TableColumn<Colecciones, String> colObservacion;
    @FXML private TableColumn<Colecciones, String> colSala;

    private EntityManager em;
    private ObservableList<Colecciones> listaColecciones;
    private ObservableList<Salas> listaSalas;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaSalas = FXCollections.observableArrayList(
                em.createNamedQuery("Salas.findAll", Salas.class).getResultList()
        );
        comboSala.setItems(listaSalas);

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSiglo.setCellValueFactory(new PropertyValueFactory<>("siglo"));
        colObservacion.setCellValueFactory(new PropertyValueFactory<>("observacion"));
        colSala.setCellValueFactory(cellData -> {
            Salas sala = cellData.getValue().getSalaid();
            return new javafx.beans.property.SimpleStringProperty(
                    sala != null ? sala.getNombre() : "Sin sala"
            );
        });

        cargarColecciones();
    }

    private void cargarColecciones() {
        List<Colecciones> colecciones = em.createNamedQuery("Colecciones.findAll", Colecciones.class).getResultList();
        listaColecciones = FXCollections.observableArrayList(colecciones);
        tablaColecciones.setItems(listaColecciones);
    }

    @FXML
    private void agregarColeccion() {
        String nombre = txtNombre.getText();
        String siglo = txtSiglo.getText();
        String observacion = txtObservacion.getText();
        Salas sala = comboSala.getValue();

        if (nombre.isEmpty() || siglo.isEmpty() || observacion.isEmpty() || sala == null) {
            showAlert("Campos requeridos", "Completa todos los campos.");
            Sonidos.reproducirSonidoError();
            return;
        }

        Colecciones c = new Colecciones();
        c.setNombre(nombre);
        c.setSiglo(siglo);
        c.setObservacion(observacion);
        c.setSalaid(sala);

        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();

        cargarColecciones();
        limpiarCampos();
    }

    @FXML
    private void eliminarColeccion() {
        Colecciones seleccionada = tablaColecciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            showAlert("Selección requerida", "Selecciona una colección para eliminar.");
            Sonidos.reproducirSonidoError();
            return;
        }

        em.getTransaction().begin();
        Colecciones ref = em.merge(seleccionada);
        em.remove(ref);
        em.getTransaction().commit();

        cargarColecciones();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtSiglo.clear();
        txtObservacion.clear();
        comboSala.getSelectionModel().clearSelection();
    }

    private void showAlert(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

