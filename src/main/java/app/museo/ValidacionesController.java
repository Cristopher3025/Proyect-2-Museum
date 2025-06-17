package app.museo;

import app.museo.entities.Entradas;
import app.museo.entities.Validacionesentradas;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

public class ValidacionesController {

    @FXML private TextField txtCodigoQR;

    private EntityManager em;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();
    }

    @FXML
    private void validarQR() {
        String codigo = txtCodigoQR.getText();
        if (codigo == null || codigo.trim().isEmpty()) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("Codigo vaci­o", "Ingrese o cargue un codigo QR.");
            return;
        }

        Entradas entrada = em.createQuery("SELECT e FROM Entradas e WHERE e.codigoqr = :codigo", Entradas.class)
                .setParameter("codigo", codigo)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (entrada == null) {
            Sonidos.reproducirSonidoError();
            mostrarAlerta("QR invalido", "No se encontro ninguna entrada con ese codigo.");
            return;
        }

        Validacionesentradas v = new Validacionesentradas();
        v.setEntradaid(entrada);
        v.setFechavalidacion(new Date());
        v.setValido('S');

        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();

        mostrarAlerta("Validacion exitosa", "Entrada validada correctamente.");
        txtCodigoQR.clear();
    }

    @FXML
    private void cargarDesdeImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen QR");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes PNG", "*.png"));

        File carpetaQR = new File("src/main/resources/qr");
        if (carpetaQR.exists()) {
            fileChooser.setInitialDirectory(carpetaQR);
        }

        File file = fileChooser.showOpenDialog(null);
        if (file == null) return;

        try {
            BufferedImage img = ImageIO.read(file);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(img)
            ));

            Result result = new MultiFormatReader().decode(bitmap);
            String contenido = result.getText();
            txtCodigoQR.setText(contenido);
            validarQR();

        } catch (NotFoundException e) {
            mostrarAlerta("QR no encontrado", "No se pudo leer un codigo QR valido en la imagen.");
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrio un error al procesar la imagen.");
            e.printStackTrace();
        }
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
