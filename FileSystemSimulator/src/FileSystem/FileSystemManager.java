/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileSystem;

import Disk.Block;
import Disk.DiskSimulator;
import EDD.LinkedList;

/**
 *
 * @author Daniel
 */
public class FileSystemManager {

    private final DiskSimulator disk;
    private final Directory root;   // directorio raíz
    private Directory currentDir;   // directorio actual (para navegación)

    public FileSystemManager(DiskSimulator disk) {
        this.disk = disk;
        this.root = new Directory("root", null);
        this.currentDir = root;
    }

    // --- Navegación ---
    public Directory getRoot() {
        return root;
    }

    public Directory getCurrentDir() {
        return currentDir;
    }

    public void changeDirectory(Directory dir) {
        if (dir != null) {
            this.currentDir = dir;
        }
    }

    // --- CRUD Archivos ---
    public File createFile(String name, int size, String owner, boolean isPublic) {
        // Reservar bloques en disco
        Block firstBlock = disk.allocate(name, size);
        if (firstBlock == null) {
            System.out.println("No se pudo crear el archivo: espacio insuficiente.");
            return null;
        }

        File file = new File(name, size, owner, isPublic);
        file.setFirstBlock(firstBlock);
        currentDir.addFile(file);

        System.out.println("Archivo creado: " + file);
        return file;
    }

    public void deleteFile(File file) {
        if (file != null && file.getFirstBlock() != null) {
            disk.free(file.getFirstBlock());
            currentDir.getFiles().remove(file);
            System.out.println("Archivo eliminado: " + file.getName());
        }
    }

    public void renameFile(File file, String newName) {
        if (file != null) {
            file.setName(newName);
            System.out.println("Archivo renombrado a: " + newName);
        }
    }

    // --- CRUD Directorios ---
    public Directory createDirectory(String name) {
        Directory dir = new Directory(name, currentDir);
        currentDir.addSubDir(dir);
        System.out.println("Directorio creado: " + name);
        return dir;
    }

    public void deleteDirectory(Directory dir) {
        if (dir != null) {
            // Eliminar recursivamente archivos y subdirectorios
            LinkedList<File> files = dir.getFiles();
            while (!files.isEmpty()) {
                File f = files.get(0);
                deleteFile(f);
            }

            LinkedList<Directory> subDirs = dir.getSubDirs();
            while (!subDirs.isEmpty()) {
                Directory sub = subDirs.get(0);
                deleteDirectory(sub);
            }

            if (dir.getParent() != null) {
                dir.getParent().getSubDirs().remove(dir);
            }
            System.out.println("Directorio eliminado: " + dir.getName());
        }
    }

    // --- Utilidades ---
    public void listCurrentDirectory() {
        System.out.println("Contenido de " + currentDir.getName() + ":");
        for (int i = 0; i < currentDir.getFiles().size(); i++) {
            File f = currentDir.getFiles().get(i);
            System.out.println("  [F] " + f.getName() + " (" + f.getSize() + " bloques)");
        }
        for (int i = 0; i < currentDir.getSubDirs().size(); i++) {
            Directory d = currentDir.getSubDirs().get(i);
            System.out.println("  [D] " + d.getName());
        }
    }
}
