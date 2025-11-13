/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileSystem;

import Disk.DiskSimulator;

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

    public void createFile(String name, int size, String owner) {
        int startBlock = disk.findFreeSpace(size); 
        if (startBlock != -1) {
            File f = new File(name, size, startBlock, owner);
            currentDir.addFile(f);
            
        } else {
            System.out.println("No hay espacio suficiente para crear el archivo " + name);
        }
    }

    public void deleteFile(File f) {
        disk.freeBlocks(f.getStartBlock(), f.getSize());
        currentDir.removeFile(f);
        disk.freeBlocks(f.getStartBlock(), f.getSize());
        System.out.println("Archivo eliminado: " + f.getName());
    }

    public void updateFile(File f, int newSize) {
        
        if (newSize > f.getSize()) {
            int extra = newSize - f.getSize();
            int extraStart = disk.findFreeSpace(extra);
            if (extraStart == -1) {
                System.out.println("Error: no hay espacio suficiente para ampliar " + f.getName());
                return;
            }
            f.setSize(newSize);
            System.out.println("Archivo ampliado: " + f.getName() + " nuevo tamaño=" + f.getSize());
        }
        else if (newSize < f.getSize()) {
            int reduce = f.getSize() - newSize;
            disk.freeBlocks(reduce, f.getStartBlock() + newSize);
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

    public int findFreeBlock(int size) {
        boolean[] blocks = disk.getBlocks(); 
        int total = blocks.length;

        for (int i = 0; i <= total - size; i++) {
            boolean libre = true;
            for (int j = 0; j < size; j++) {
                if (blocks[i + j]) { 
                    libre = false;
                    break;
                }
            }
            if (libre) {
                return i;
            }
        }
        return -1; 
    }

    public DiskSimulator getDisk() {
        return disk;
    }
}
