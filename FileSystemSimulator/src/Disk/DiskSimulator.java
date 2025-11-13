/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Disk;

import FileSystem.File;

/**
 *
 * @author Daniel
 */
public class DiskSimulator {

    private boolean[] blocks;   // true = ocupado, false = libre
    private int totalBlocks;

    public DiskSimulator(int totalBlocks) {
        this.totalBlocks = totalBlocks;
        this.blocks = new boolean[totalBlocks];
    }

    // Asignar bloques por tamaño y bloque inicial
    public boolean allocateBlocks(int size, int startBlock) {
        if (startBlock < 0 || startBlock + size > totalBlocks) {
            return false; // fuera de rango
        }

        // Verificar disponibilidad
        for (int i = startBlock; i < startBlock + size; i++) {
            if (blocks[i]) {
                return false; // bloque ocupado
            }
        }

        // Marcar como ocupados
        for (int i = startBlock; i < startBlock + size; i++) {
            blocks[i] = true;
        }
        return true;
    }

    // 🔑 Sobrecarga para trabajar directamente con File
    public boolean allocateBlocks(File file) {
        int size = file.getSize();
        // Buscar un bloque inicial libre
        int startBlock = findFreeSpace(size);
        if (startBlock == -1) {
            return false; // no hay espacio suficiente
        }

        // Asignar bloques
        for (int i = startBlock; i < startBlock + size; i++) {
            blocks[i] = true;
        }

        // Actualizar el archivo con el bloque inicial
        file.setStartBlock(startBlock);
        return true;
    }

    // Liberar bloques por tamaño y bloque inicial
    public void freeBlocks(int size, int startBlock) {
        if (startBlock < 0 || startBlock + size > totalBlocks) {
            return;
        }

        for (int i = startBlock; i < startBlock + size; i++) {
            blocks[i] = false;
        }
    }

    // 🔑 Liberar bloques de un File
    public void freeBlocks(File file) {
        freeBlocks(file.getSize(), file.getStartBlock());
    }

    // Buscar espacio libre contiguo
    public int findFreeSpace(int size) {
        for (int i = 0; i <= totalBlocks - size; i++) {
            boolean free = true;
            for (int j = i; j < i + size; j++) {
                if (blocks[j]) {
                    free = false;
                    break;
                }
            }
            if (free) {
                return i;
            }
        }
        return -1;
    }

    // Consultar si un bloque está ocupado
    public boolean isBlockUsed(int index) {
        if (index < 0 || index >= totalBlocks) {
            return false;
        }
        return blocks[index];
    }

    // Obtener número total de bloques
    public int getTotalBlocks() {
        return totalBlocks;
    }

    // Imprimir estado del disco (para depuración)
    public void printStatus() {
        System.out.print("Estado del disco: ");
        for (int i = 0; i < totalBlocks; i++) {
            System.out.print(blocks[i] ? "[X]" : "[ ]");
        }
        System.out.println();
    }

    public boolean[] getBlocks() {
        return blocks;
    }
}
