/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Disk;

import EDD.LinkedList;

/**
 *
 * @author Daniel
 */
public class DiskSimulator {
    private boolean[] blocks;

    public DiskSimulator(int totalBlocks) {
        blocks = new boolean[totalBlocks];
    }

    public int allocate(int size) {
        for (int i = 0; i <= blocks.length - size; i++) {
            boolean libre = true;
            for (int j = 0; j < size; j++) {
                if (blocks[i + j]) {
                    libre = false;
                    break;
                }
            }
            if (libre) {
                for (int j = 0; j < size; j++) {
                    blocks[i + j] = true;
                }
                return i; // bloque inicial reservado
            }
        }
        return -1; // no hay espacio suficiente
    }

    /**
     * Libera un rango de bloques.
     * @param start bloque inicial
     * @param size cantidad de bloques a liberar
     */
    public void free(int start, int size) {
        for (int i = start; i < start + size && i < blocks.length; i++) {
            blocks[i] = false;
        }
    }

    /**
     * Muestra el estado del disco en consola.
     */
    public void printStatus() {
        System.out.print("Estado del disco: ");
        for (boolean b : blocks) {
            System.out.print(b ? "[X]" : "[ ]");
        }
        System.out.println();
    }

    public int getTotalBlocks() {
        return blocks.length;
    }
}
