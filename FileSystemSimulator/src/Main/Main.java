/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import GUI.FileSystemSim;

/**
 *
 * @author Daniel
 */
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileSystemSim gui = new FileSystemSim();
            gui.setVisible(true);                                                        
            gui.refreshUI();            
        });
    }
}
