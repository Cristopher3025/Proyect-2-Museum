package app.museo;

import app.museo.entities.Museos;
import app.museo.entities.Tiposmuseo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MuseosController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private ComboBox<Tiposmuseo> comboTipoMuseo;

    @FXML private TableView<Museos> tablaMuseos;
    @FXML private TableColumn<Museos, String> colNombre;
    @FXML private TableColumn<Museos, String> colDireccion;
    @FXML private TableColumn<Museos, String> colTelefono;
    @FXML private TableColumn<Museos, String> colTipo;

    private EntityManager em;
    private ObservableList<Museos> listaMuseos;
    private ObservableList<Tiposmuseo> listaTipos;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        cargarTiposMuseo();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTipo.setCellValueFactory(cellData -> {
            Tiposmuseo tipo = cellData.getValue().getTipomuseoid();
            return new javafx.beans.property.SimpleStringProperty(tipo != null ? tipo.getNombre() : "Sin tipo");
        });

        cargarMuseos();
    }

    private void cargarMuseos() {
        List<Museos> museos = em.createNamedQuery("Museos.findAll", Museos.class).getResultList();
        listaMuseos = FXCollections.observableArrayList(museos);
        tablaMuseos.setItems(listaMuseos);
    }

    private void cargarTiposMuseo() {
        List<Tiposmuseo> tipos = em.createNamedQuery("Tiposmuseo.findAll", Tiposmuseo.class).getResultList();
        listaTipos = FXCollections.observableArrayList(tipos);
        comboTipoMuseo.setItems(listaTipos);
    }

    @FXML
    private void agregarMuseo() {
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();
        String telefono = txtTelefono.getText();
        Tiposmuseo tipo = comboTipoMuseo.getValue();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || tipo == null) {
            showAlert("Campos incompletos", "Por favor, completa todos los campos.");
            Sonidos.reproducirSonidoError();
            return;
        }

        Museos m = new Museos();
        m.setNombre(nombre);
        m.setDireccion(direccion);
        m.setTelefono(telefono);
        m.setTipomuseoid(tipo);

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

        cargarMuseos();
        limpiarCampos();
    }

    @FXML
    private void eliminarMuseo() {
        Museos seleccionado = tablaMuseos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert("Selecciona un museo", "Debes seleccionar un museo para eliminarlo.");
            Sonidos.reproducirSonidoError();
            return;
        }

        em.getTransaction().begin();
        Museos ref = em.merge(seleccionado);
        em.remove(ref);
        em.getTransaction().commit();

        cargarMuseos();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        comboTipoMuseo.getSelectionModel().clearSelection();
    }

    private void showAlert(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

