package app.museo;

import app.museo.entities.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import java.awt.Desktop;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.media.AudioClip;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class EntradasController {

    @FXML private DatePicker dateVisita;
    @FXML private ListView<Salas> listSalas;
    @FXML private Label labelTotal;
    @FXML private ComboBox<Usuarios> comboUsuarios;
    @FXML private TableView<Entradas> tablaEntradas;
    @FXML private TableColumn<Entradas, String> colCodigoQR;
    @FXML private TableColumn<Entradas, String> colFecha;
    @FXML private TableColumn<Entradas, Number> colTotal;
    @FXML private ComboBox<Tipostarjeta> comboTipoTarjeta;
    private ObservableList<Tipostarjeta> listaTiposTarjeta;

    private ObservableList<Entradas> listaEntradas;

    private EntityManager em;
    private ObservableList<Salas> listaSalas;
    private ObservableList<Usuarios> listaUsuarios;
    private BigDecimal total;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();
        cargarSalas();
        cargarUsuarios();
        listSalas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listaTiposTarjeta = FXCollections.observableArrayList(
        em.createNamedQuery("Tipostarjeta.findAll", Tipostarjeta.class).getResultList()
    );
    comboTipoTarjeta.setItems(listaTiposTarjeta);

        }

    private void cargarSalas() {
        listaSalas = FXCollections.observableArrayList(
                em.createNamedQuery("Salas.findAll", Salas.class).getResultList()
        );
        listSalas.setItems(listaSalas);
    }

    private void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(
                em.createNamedQuery("Usuarios.findAll", Usuarios.class).getResultList()
        );
        comboUsuarios.setItems(listaUsuarios);
    }

    @FXML
    private void calcularTotal() {
        LocalDate fecha = dateVisita.getValue();
        List<Salas> seleccionadas = listSalas.getSelectionModel().getSelectedItems();
        Tipostarjeta tipoTarjeta = comboTipoTarjeta.getValue();

        if (fecha == null || seleccionadas.isEmpty() || tipoTarjeta == null) {
            mostrarAlerta("Error", "Seleccione fecha, salas y tipo de tarjeta.");
            Sonidos.reproducirSonidoError();
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

      
        Comisionestarjetas comision = em.createQuery(
            "SELECT c FROM Comisionestarjetas c WHERE c.tipotarjetaid = :tipo", Comisionestarjetas.class)
            .setParameter("tipo", tipoTarjeta)
            .getResultStream()
            .findFirst()
            .orElse(null);

        if (comision != null && comision.getComision() != null) {
            BigDecimal porcentaje = comision.getComision();
            BigDecimal extra = suma.multiply(porcentaje).divide(new BigDecimal("100"));
            suma = suma.add(extra);
        }

        total = suma;
        labelTotal.setText("₡" + total.toPlainString());
    }
    
    private void generarReciboPDF(Entradas entrada, List<Salas> salas) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.setLeading(20f);
            content.newLineAtOffset(50, 700);

            content.showText("Museo Americano de Historia Natural");
            content.newLine();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.showText("Recibo de Entrada #" + entrada.getIdentrada());
            content.newLine();
            content.showText("Fecha de visita: " + entrada.getFechavisita());
            content.newLine();
            content.showText("Código QR: " + entrada.getCodigoqr());
            content.newLine();
            content.newLine();
            content.showText("Salas:");
            for (Salas s : salas) {
                content.newLine();
                content.showText("- " + s.getNombre());
            }
            content.newLine();
            content.newLine();
            content.showText("Total pagado: ₡" + entrada.getPreciototal().toPlainString());
            content.endText();
            content.close();

            String fileName = "recibo_" + entrada.getIdentrada() + ".pdf";
            File carpetaRecibos = new File("src/main/resources/recibos");
            if (!carpetaRecibos.exists()) carpetaRecibos.mkdirs();

            File archivoPDF = new File(carpetaRecibos, fileName);
            doc.save(archivoPDF);

            if (archivoPDF.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivoPDF);
            } else {
                Sonidos.reproducirSonidoError();
                System.out.println("PDF no existe o no se puede abrir: " + archivoPDF.getAbsolutePath());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Error al generar el recibo", "No se pudo crear o abrir el archivo PDF.");
        }
    }




    @FXML
    private void guardarEntrada() {
        LocalDate fecha = dateVisita.getValue();
        List<Salas> seleccionadas = listSalas.getSelectionModel().getSelectedItems();
        Usuarios usuario = comboUsuarios.getValue();

        if (fecha == null || seleccionadas.isEmpty() || total == null || usuario == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Error", "Complete todos los campos antes de guardar.");
            return;
        }

        String codigoQR = UUID.randomUUID().toString();

        em.getTransaction().begin();

        Transacciones t = new Transacciones();
        t.setUsuarioid(usuario);
        t.setFecha(Date.valueOf(LocalDate.now()));
        t.setMonto(total);
        t.setTipo("Entrada");
        em.persist(t);

        Entradas entrada = new Entradas();
        entrada.setFechavisita(Date.valueOf(fecha));
        entrada.setPreciototal(total);
        entrada.setCodigoqr(codigoQR);
        entrada.setUsuarioid(usuario);
        entrada.setTransaccionid(t);
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
            generarReciboPDF(entrada, seleccionadas);
            Sonidos.reproducirSonidoPago();
            alert.setHeaderText("Entrada registrada y QR generado");
            alert.getDialogPane().setContent(imageView);
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            Sonidos.reproducirSonidoError();
            mostrarAlerta("QR no generado", "La entrada se guardó, pero no se generó el QR.");
        }

        limpiarFormulario();
    }
    @FXML
    private void verEntradasRegistradas() {
        listaEntradas = FXCollections.observableArrayList(
            em.createNamedQuery("Entradas.findAll", Entradas.class).getResultList()
        );
        tablaEntradas.setItems(listaEntradas);

        colCodigoQR.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getCodigoqr()));
        colFecha.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getFechavisita().toString()));
        colTotal.setCellValueFactory(data ->
            new SimpleDoubleProperty(data.getValue().getPreciototal().doubleValue()));
    }

    @FXML
    private void eliminarEntradaSeleccionada() {
        Entradas seleccionada = tablaEntradas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Ninguna entrada seleccionada", "Debes seleccionar una entrada para eliminar.");
            return;
        }

        em.getTransaction().begin();

        
        em.createQuery("DELETE FROM Entradassalas es WHERE es.entradaid = :entrada")
            .setParameter("entrada", seleccionada)
            .executeUpdate();

        em.remove(em.merge(seleccionada));
        em.getTransaction().commit();

        mostrarAlerta("Eliminada", "Entrada eliminada correctamente.");
        verEntradasRegistradas();
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }

    private void limpiarFormulario() {
        dateVisita.setValue(null);
        listSalas.getSelectionModel().clearSelection();
        comboUsuarios.getSelectionModel().clearSelection();
        labelTotal.setText("₡0.00");
        total = null;
    }

}

