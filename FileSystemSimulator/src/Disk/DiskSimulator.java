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

    private final Block[] blocks;   // Arreglo de bloques
    private final int totalBlocks;

    public DiskSimulator(int totalBlocks) {
        this.totalBlocks = totalBlocks;
        this.blocks = new Block[totalBlocks];
        for (int i = 0; i < totalBlocks; i++) {
            blocks[i] = new Block(i);
        }
    }

    public Block allocate(String fileName, int size) {
        LinkedList<Block> freeBlocks = getFreeBlocks();
        if (freeBlocks.size() < size) {
            System.out.println("No hay espacio suficiente en disco.");
            return null;
        }

        Block first = null;
        Block prev = null;

        for (int i = 0; i < size; i++) {
            Block current = freeBlocks.get(i); 
            current.setOcupado(true, fileName);

            if (first == null) {
                first = current;
            }
            if (prev != null) {
                prev.setNext(current);
            }
            prev = current;
        }

        return first;
    }

   
    public void free(Block start) {
        Block current = start;
        while (current != null) {
            current.setOcupado(false, null);
            Block next = current.getNext();
            current.setNext(null); // romper la cadena
            current = next;
        }
    }

    
    public LinkedList<Block> getFreeBlocks() {
        LinkedList<Block> libres = new LinkedList<>();
        for (Block b : blocks) {
            if (!b.isOcupado()) {
                libres.add(b);
            }
        }
        return libres;
    }

    
    public Block[] getBlocks() {
        return blocks;
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }
}
