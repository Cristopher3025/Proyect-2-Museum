package app.museo;

import app.museo.entities.Transacciones;
import app.museo.entities.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class TransaccionesController {

    @FXML private ComboBox<Usuarios> comboUsuario;
    @FXML private TextField txtMonto;
    @FXML private TableView<Transacciones> tablaTransacciones;
    @FXML private TableColumn<Transacciones, BigDecimal> colMonto;
    @FXML private TableColumn<Transacciones, String> colUsuario;

    private EntityManager em;
    private ObservableList<Transacciones> listaTransacciones;
    private ObservableList<Usuarios> listaUsuarios;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaUsuarios = FXCollections.observableArrayList(
                em.createNamedQuery("Usuarios.findAll", Usuarios.class).getResultList()
        );
        comboUsuario.setItems(listaUsuarios);

        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colUsuario.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getUsuarioid() != null ? c.getValue().getUsuarioid().getNombre() : "Sin usuario"
                )
        );

        cargarTransacciones();
    }

    private void cargarTransacciones() {
        listaTransacciones = FXCollections.observableArrayList(
                em.createNamedQuery("Transacciones.findAll", Transacciones.class).getResultList()
        );
        tablaTransacciones.setItems(listaTransacciones);
    }

    @FXML
    private void agregarTransaccion() {
        if (txtMonto.getText().isEmpty() || comboUsuario.getValue() == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Campos incompletos", "Debe completar todos los campos.");
            return;
        }

        BigDecimal monto = new BigDecimal(txtMonto.getText());
        Usuarios usuario = comboUsuario.getValue();

        Transacciones t = new Transacciones();
        t.setMonto(monto);
        t.setUsuarioid(usuario);

        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();

        cargarTransacciones();
        limpiar();
    }

    @FXML
    private void eliminarTransaccion() {
        Transacciones seleccionada = tablaTransacciones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Seleccione una transacción", "Debe seleccionar una para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Transacciones ref = em.merge(seleccionada);
        em.remove(ref);
        em.getTransaction().commit();

        cargarTransacciones();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiar() {
        txtMonto.clear();
        comboUsuario.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
