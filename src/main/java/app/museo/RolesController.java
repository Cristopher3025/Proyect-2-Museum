package app.museo;

import app.museo.entities.Roles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RolesController {

    @FXML private TextField txtNombre;
    @FXML private TableView<Roles> tablaRoles;
    @FXML private TableColumn<Roles, String> colNombre;

    private EntityManager em;
    private ObservableList<Roles> listaRoles;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        cargarRoles();
    }

    private void cargarRoles() {
        List<Roles> roles = em.createNamedQuery("Roles.findAll", Roles.class).getResultList();
        listaRoles = FXCollections.observableArrayList(roles);
        tablaRoles.setItems(listaRoles);
    }

    @FXML
    private void agregarRol() {
        String nombre = txtNombre.getText();
        if (nombre.isEmpty()) {
            mostrarAlerta("Campo requerido", "Debes ingresar un nombre de rol.");
            return;
        }

        Roles r = new Roles();
        r.setNombre(nombre);

        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();

        cargarRoles();
        txtNombre.clear();
    }

    @FXML
    private void eliminarRol() {
        Roles seleccionado = tablaRoles.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un rol", "Debes seleccionar un rol para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Roles ref = em.merge(seleccionado);
        em.remove(ref);
        em.getTransaction().commit();

        cargarRoles();
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
