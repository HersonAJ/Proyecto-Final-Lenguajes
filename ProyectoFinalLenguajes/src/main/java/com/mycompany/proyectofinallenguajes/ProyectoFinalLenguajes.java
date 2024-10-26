/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectofinallenguajes;

import com.mycompany.proyectofinallenguajes.Frontend.Interfaz;
import javax.swing.SwingUtilities;

/**
 *
 * @author herson
 */
public class ProyectoFinalLenguajes {

    public static void main(String[] args) {
                       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }
}
