package app.museo;

import app.museo.entities.Entradas;
import app.museo.entities.Entradassalas;
import app.museo.entities.Precios;
import app.museo.entities.Salas;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

        String codigoQR = UUID.randomUUID().toString();
        entrada.setCodigoqr(codigoQR);

        em.getTransaction().begin();
        em.persist(entrada);

        for (Salas sala : seleccionadas) {
            Entradassalas es = new Entradassalas();
            es.setEntradaid(entrada);
            es.setSalaid(sala);
            em.persist(es);
        }

        em.getTransaction().commit();

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(codigoQR, BarcodeFormat.QR_CODE, 300, 300);
            Path path = Paths.get("src/main/resources/qr/" + codigoQR + ".png");
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            Image image = new Image(new FileInputStream(path.toFile()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("QR Generado");
            alert.setHeaderText("Entrada registrada y QR generado");
            alert.getDialogPane().setContent(imageView);
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("QR no generado", "La entrada se guardó, pero no se generó el QR.");
            return;
        }

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
