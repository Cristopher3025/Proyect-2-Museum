package app.museo;

import app.museo.entities.Comisionestarjetas;
import app.museo.entities.Tipostarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class ComisionesController {

    @FXML private ComboBox<Tipostarjeta> comboTipoTarjeta;
    @FXML private TextField txtComision;
    @FXML private TableView<Comisionestarjetas> tablaComisiones;
    @FXML private TableColumn<Comisionestarjetas, String> colTipoTarjeta;
    @FXML private TableColumn<Comisionestarjetas, BigDecimal> colComision;

    private EntityManager em;
    private ObservableList<Comisionestarjetas> listaComisiones;
    private ObservableList<Tipostarjeta> listaTiposTarjeta;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaTiposTarjeta = FXCollections.observableArrayList(
                em.createNamedQuery("Tipostarjeta.findAll", Tipostarjeta.class).getResultList()
        );
        comboTipoTarjeta.setItems(listaTiposTarjeta);

        colTipoTarjeta.setCellValueFactory(cellData -> {
            Tipostarjeta tipo = cellData.getValue().getTipotarjetaid();
            return new javafx.beans.property.SimpleStringProperty(tipo != null ? tipo.getNombre() : "Sin tipo");
        });
        colComision.setCellValueFactory(new PropertyValueFactory<>("comision"));

        cargarComisiones();
    }

    private void cargarComisiones() {
        List<Comisionestarjetas> comisiones = em.createNamedQuery("Comisionestarjetas.findAll", Comisionestarjetas.class).getResultList();
        listaComisiones = FXCollections.observableArrayList(comisiones);
        tablaComisiones.setItems(listaComisiones);
    }

    @FXML
    private void agregarComision() {
        String comisionStr = txtComision.getText();
        Tipostarjeta tipo = comboTipoTarjeta.getValue();

        if (comisionStr.isEmpty() || tipo == null) {
            mostrarAlerta("Campos requeridos", "Debe completar todos los campos.");
            return;
        }

        BigDecimal comision = new BigDecimal(comisionStr);
        Comisionestarjetas nueva = new Comisionestarjetas();
        nueva.setComision(comision);
        nueva.setTipotarjetaid(tipo);

        em.getTransaction().begin();
        em.persist(nueva);
        em.getTransaction().commit();

        cargarComisiones();
        limpiarCampos();
    }

    @FXML
    private void eliminarComision() {
        Comisionestarjetas seleccionada = tablaComisiones.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Seleccione una comisión", "Debe seleccionar un elemento para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Comisionestarjetas ref = em.merge(seleccionada);
        em.remove(ref);
        em.getTransaction().commit();

        cargarComisiones();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarCampos() {
        txtComision.clear();
        comboTipoTarjeta.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
