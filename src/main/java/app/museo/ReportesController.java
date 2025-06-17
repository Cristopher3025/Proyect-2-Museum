package app.museo;

import app.museo.entities.Entradas;
import app.museo.entities.Entradassalas;
import app.museo.entities.Salas;
import app.museo.entities.Valoracionessalas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class ReportesController {

    @FXML private TextArea txtReporte;
    private EntityManager em;

    @FXML
    public void initialize() {
        em = Persistence.createEntityManagerFactory("museo_persistence").createEntityManager();
        generarReporte();
    }

    private void generarReporte() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,###.00");

        List<Entradas> entradas = em.createNamedQuery("Entradas.findAll", Entradas.class).getResultList();
        List<Entradassalas> entradasSalas = em.createNamedQuery("Entradassalas.findAll", Entradassalas.class).getResultList();
        List<Valoracionessalas> valoraciones = em.createNamedQuery("Valoracionessalas.findAll", Valoracionessalas.class).getResultList();

        sb.append("1. Entradas registradas por día:\n");
        Map<Date, Long> entradasPorDia = entradas.stream()
            .collect(Collectors.groupingBy(Entradas::getFechavisita, TreeMap::new, Collectors.counting()));
        entradasPorDia.forEach((fecha, cantidad) -> {
            sb.append(" - ").append(fecha).append(": ").append(cantidad).append(" entradas\n");
        });

        sb.append("\n2. Sala más visitada:\n");
        Map<Salas, Long> visitasPorSala = entradasSalas.stream()
            .collect(Collectors.groupingBy(Entradassalas::getSalaid, Collectors.counting()));
        visitasPorSala.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .ifPresent(entry -> sb.append(" - ").append(entry.getKey().getNombre())
                                  .append(" con ").append(entry.getValue()).append(" visitas\n"));

        sb.append("\n3. Recaudación total por mes:\n");
        Map<Integer, BigDecimal> montoPorMes = new TreeMap<>();
        for (Entradas e : entradas) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(e.getFechavisita());
            int mes = cal.get(Calendar.MONTH) + 1;
            montoPorMes.put(mes, montoPorMes.getOrDefault(mes, BigDecimal.ZERO).add(e.getPreciototal()));
        }

        montoPorMes.forEach((mes, total) -> {
            sb.append(" - ").append(Month.of(mes)).append(": ₡").append(df.format(total)).append("\n");
        });

        sb.append("\n4. Promedio de estrellas por sala:\n");
        Map<Salas, List<Valoracionessalas>> porSala = valoraciones.stream()
            .collect(Collectors.groupingBy(Valoracionessalas::getSalaid));

        porSala.forEach((sala, lista) -> {
            double promedio = lista.stream().mapToInt(v -> v.getValoracion().intValue()).average().orElse(0);
            sb.append(" - ").append(sala.getNombre())
              .append(": ").append(String.format("%.2f", promedio)).append(" estrellas\n");
        });

        txtReporte.setText(sb.toString());
    }

    @FXML
    private void volverMenu() throws Exception {
        App.setRoot("menu");
    }
}
