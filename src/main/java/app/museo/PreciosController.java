package app.museo;

import app.museo.entities.Precios;
import app.museo.entities.Salas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.List;

public class PreciosController {

    @FXML private ComboBox<Salas> comboSala;
    @FXML private TextField txtPrecioLS;
    @FXML private TextField txtPrecioD;
    @FXML private TableView<Precios> tablaPrecios;
    @FXML private TableColumn<Precios, String> colSala;
    @FXML private TableColumn<Precios, BigDecimal> colPrecioLS;
    @FXML private TableColumn<Precios, BigDecimal> colPrecioD;

    private EntityManager em;
    private ObservableList<Salas> listaSalas;
    private ObservableList<Precios> listaPrecios;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();

        listaSalas = FXCollections.observableArrayList(
                em.createNamedQuery("Salas.findAll", Salas.class).getResultList()
        );
        comboSala.setItems(listaSalas);

        colSala.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                    
                cellData.getValue().getSalaid() != null ? cellData.getValue().getSalaid().getNombre() : "Sin sala"
            )
        );
        colPrecioLS.setCellValueFactory(new PropertyValueFactory<>("preciolunesasabado"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<>("preciodomingo"));

        cargarPrecios();
    }

    private void cargarPrecios() {
        listaPrecios = FXCollections.observableArrayList(
            em.createNamedQuery("Precios.findAll", Precios.class).getResultList()
        );
        tablaPrecios.setItems(listaPrecios);
    }

    @FXML
    private void agregarPrecio() {
        Salas sala = comboSala.getValue();
        if (sala == null || txtPrecioLS.getText().isEmpty() || txtPrecioD.getText().isEmpty()) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Campos incompletos", "Todos los campos son obligatorios.");
            return;
        }

        BigDecimal precioLS = new BigDecimal(txtPrecioLS.getText());
        BigDecimal precioD = new BigDecimal(txtPrecioD.getText());

        Precios p = new Precios();
        p.setSalaid(sala);
        p.setPreciolunesasabado(precioLS);
        p.setPreciodomingo(precioD);

        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();

        cargarPrecios();
        limpiarCampos();
    }

    @FXML
    private void eliminarPrecio() {
        Precios seleccionado = tablaPrecios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Seleccione un precio", "Debe seleccionar un elemento para eliminar.");
            return;
        }

        em.getTransaction().begin();
        Precios ref = em.merge(seleccionado);
        em.remove(ref);
        em.getTransaction().commit();

        cargarPrecios();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarCampos() {
        txtPrecioLS.clear();
        txtPrecioD.clear();
        comboSala.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
