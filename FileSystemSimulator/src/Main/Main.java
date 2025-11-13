/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import FileSystem.Directory;
import FileSystem.File;
import FileSystem.FileSystemManager;
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

            FileSystemManager fs = gui.getFileSystemManager();
            Directory root = fs.getCurrentDir();

            // Crear un subdirectorio "docs"
            Directory docs = new Directory("docs", root);
            root.addDirectory(docs);

            // Crear un archivo dentro de "docs"
            File file1 = new File("ejemplo.txt", 3, 0, "daniel");
            boolean ok = fs.getDisk().allocateBlocks(file1);
            if (ok) {
                docs.addFile(file1);
                System.out.println("Archivo creado en directorio docs: ejemplo.txt");
            }
            gui.refreshUI();            
        });
    }
}
