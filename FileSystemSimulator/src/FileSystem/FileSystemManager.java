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
    private DiskSimulator disk;
    private Directory root;
    private Directory currentDir;

    public FileSystemManager(DiskSimulator disk) {
        this.disk = disk;
        this.root = new Directory("root", null);
        this.currentDir = root;
    }

    public Directory getCurrentDir() {
        return currentDir;
    }

    public void createFile(String name, int size, String owner, boolean isText) {
        int startBlock = disk.allocate(size);
        if (startBlock == -1) {
            System.out.println("Error: no hay espacio suficiente para crear " + name);
            return;
        }
        File f = new File(name, size, startBlock, isText);
        currentDir.addFile(f);
        System.out.println("Archivo creado: " + f);
    }

    public void deleteFile(File f) {
        disk.free(f.getStartBlock(), f.getSize());
        currentDir.removeFile(f);
        System.out.println("Archivo eliminado: " + f.getName());
    }

    public void updateFile(File f, int newSize) {
        // Si el nuevo tamaño es mayor, reservar bloques adicionales
        if (newSize > f.getSize()) {
            int extra = newSize - f.getSize();
            int extraStart = disk.allocate(extra);
            if (extraStart == -1) {
                System.out.println("Error: no hay espacio suficiente para ampliar " + f.getName());
                return;
            }
            f.setSize(newSize);
            System.out.println("Archivo ampliado: " + f.getName() + " nuevo tamaño=" + f.getSize());
        }
        // Si el nuevo tamaño es menor, liberar bloques
        else if (newSize < f.getSize()) {
            int reduce = f.getSize() - newSize;
            disk.free(f.getStartBlock() + newSize, reduce);
            f.setSize(newSize);
            System.out.println("Archivo reducido: " + f.getName() + " nuevo tamaño=" + f.getSize());
        } else {
            System.out.println("Archivo sin cambios: " + f.getName());
        }
    }

    public void readFile(File f) {
        System.out.println("Leyendo archivo: " + f.getName() + " tamaño=" + f.getSize());
    }

    public void listCurrentDirectory() {
        System.out.println("Contenido de " + currentDir.getName() + ":");
        for (int i = 0; i < currentDir.getFiles().size(); i++) {
            System.out.println(currentDir.getFiles().get(i));
        }
    }
}
