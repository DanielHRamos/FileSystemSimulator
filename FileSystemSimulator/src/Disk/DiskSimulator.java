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

    private boolean[] blocks;  
    private int totalBlocks;

    public DiskSimulator(int totalBlocks) {
        this.totalBlocks = totalBlocks;
        this.blocks = new boolean[totalBlocks];
    }

   
    public boolean allocateBlocks(int size, int startBlock) {
        if (startBlock < 0 || startBlock + size > totalBlocks) {
            return false; // fuera de rango
        }

        
        for (int i = startBlock; i < startBlock + size; i++) {
            if (blocks[i]) {
                return false; // bloque ocupado
            }
        }

       
        for (int i = startBlock; i < startBlock + size; i++) {
            blocks[i] = true;
        }
        return true;
    }

    
    public boolean allocateBlocks(File file) {
        int size = file.getSize();
        
        int startBlock = findFreeSpace(size);
        if (startBlock == -1) {
            return false; 
        }

        
        for (int i = startBlock; i < startBlock + size; i++) {
            blocks[i] = true;
        }

       
        file.setStartBlock(startBlock);
        return true;
    }

    
    public void freeBlocks(int size, int startBlock) {
        if (startBlock < 0 || startBlock + size > totalBlocks) {
            return;
        }

        for (int i = startBlock; i < startBlock + size; i++) {
            blocks[i] = false;
        }
    }

    
    public void freeBlocks(File file) {
        freeBlocks(file.getSize(), file.getStartBlock());
    }

    
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

    
    public boolean isBlockUsed(int index) {
        if (index < 0 || index >= totalBlocks) {
            return false;
        }
        return blocks[index];
    }

    
    public int getTotalBlocks() {
        return totalBlocks;
    }

    
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
