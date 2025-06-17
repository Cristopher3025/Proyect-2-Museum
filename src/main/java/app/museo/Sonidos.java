/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.museo;

import javafx.scene.media.AudioClip;

/**
 *
 * @author crist
 */
public class Sonidos {
    public static void reproducirSonidoError() {
        try {
            javafx.scene.media.AudioClip sonido = new javafx.scene.media.AudioClip(
                Sonidos.class.getResource("/Sonidos/error.mp3").toExternalForm()
            );
            sonido.play();
        } catch (Exception e) {
            System.out.println("Error al reproducir sonido.");
        }
    }

    public static void reproducirSonidoPago() {
        try {
            javafx.scene.media.AudioClip sonido = new javafx.scene.media.AudioClip(
                Sonidos.class.getResource("/Sonidos/pago.mp3").toExternalForm()
            );
            sonido.play();
        } catch (Exception e) {
            System.out.println("Error al reproducir sonido.");
        }
    }
}

