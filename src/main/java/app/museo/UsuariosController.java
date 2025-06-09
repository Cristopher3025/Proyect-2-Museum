package app.museo;

import app.museo.entities.Roles;
import app.museo.entities.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuariosController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<Roles> comboRol;
    @FXML private TableView<Usuarios> tablaUsuarios;
    @FXML private TableColumn<Usuarios, String> colNombre;
    @FXML private TableColumn<Usuarios, String> colCorreo;
    @FXML private TableColumn<Usuarios, String> colRol;

    private EntityManager em;
    private ObservableList<Usuarios> listaUsuarios;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getRolid() != null ? c.getValue().getRolid().getNombre() : "Sin rol"
        ));

        ObservableList<Roles> listaRoles = FXCollections.observableArrayList(
                em.createNamedQuery("Roles.findAll", Roles.class).getResultList()
        );
        comboRol.setItems(listaRoles);

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(
                em.createNamedQuery("Usuarios.findAll", Usuarios.class).getResultList()
        );
        tablaUsuarios.setItems(listaUsuarios);
    }

    @FXML
    private void agregarUsuario() {
        if (txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                txtContrasena.getText().isEmpty() || comboRol.getValue() == null) {
            mostrarAlerta("Campos requeridos", "Completa todos los campos.");
            return;
        }

        Usuarios u = new Usuarios();
        u.setNombre(txtNombre.getText());
        u.setCorreo(txtCorreo.getText());
        u.setContrasena(txtContrasena.getText());
        u.setRolid(comboRol.getValue());

        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();

        cargarUsuarios();
        limpiar();
    }

    @FXML
    private void eliminarUsuario() {
        Usuarios seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un usuario", "Debes seleccionar un usuario para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Usuarios ref = em.merge(seleccionado);
        em.remove(ref);
        em.getTransaction().commit();

        cargarUsuarios();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiar() {
        txtNombre.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        comboRol.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}

