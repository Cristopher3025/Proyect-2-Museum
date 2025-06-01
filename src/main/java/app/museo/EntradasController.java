package app.museo;

import app.museo.entities.Entradas;
import app.museo.entities.Entradassalas;
import app.museo.entities.Precios;
import app.museo.entities.Salas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EntradasController {

    @FXML private DatePicker dateVisita;
    @FXML private ListView<Salas> listSalas;
    @FXML private Label labelTotal;

    private EntityManager em;
    private ObservableList<Salas> listaSalas;
    private BigDecimal total;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();
        cargarSalas();
        listSalas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void cargarSalas() {
        listaSalas = FXCollections.observableArrayList(
            em.createNamedQuery("Salas.findAll", Salas.class).getResultList()
        );
        listSalas.setItems(listaSalas);
    }

    @FXML
    private void calcularTotal() {
        LocalDate fecha = dateVisita.getValue();
        List<Salas> seleccionadas = listSalas.getSelectionModel().getSelectedItems();

        if (fecha == null || seleccionadas.isEmpty()) {
            mostrarAlerta("Error", "Seleccione una fecha y al menos una sala.");
            return;
        }

        DayOfWeek dia = fecha.getDayOfWeek();
        boolean esDomingo = dia == DayOfWeek.SUNDAY;
        BigDecimal suma = BigDecimal.ZERO;

        for (Salas sala : seleccionadas) {
            
            List<Precios> preciosSala = new java.util.ArrayList<>(sala.getPreciosCollection());

            if (preciosSala.isEmpty()) continue;
            Precios precio = preciosSala.get(0); 

            BigDecimal monto = esDomingo ? precio.getPreciodomingo() : precio.getPreciolunesasabado();
            if (monto != null) {
                suma = suma.add(monto);
            }
        }

        total = suma;
        labelTotal.setText("₡" + total.toPlainString());
    }

    @FXML
    private void guardarEntrada() {
        LocalDate fecha = dateVisita.getValue();
        List<Salas> seleccionadas = listSalas.getSelectionModel().getSelectedItems();

        if (fecha == null || seleccionadas.isEmpty() || total == null) {
            mostrarAlerta("Error", "Primero calcule el total y seleccione los datos.");
            return;
        }

        Entradas entrada = new Entradas();
        entrada.setFechavisita(java.sql.Date.valueOf(fecha));
        entrada.setPreciototal(total);

        em.getTransaction().begin();
        em.persist(entrada);

        for (Salas sala : seleccionadas) {
            Entradassalas es = new Entradassalas();
            es.setEntradaid(entrada);
            es.setSalaid(sala);
            em.persist(es);
        }

        em.getTransaction().commit();

        mostrarAlerta("Éxito", "Entrada registrada correctamente.");
        limpiarFormulario();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarFormulario() {
        dateVisita.setValue(null);
        listSalas.getSelectionModel().clearSelection();
        labelTotal.setText("₡0.00");
        total = null;
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
