/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileSystem;

import Disk.Block;

/**
 *
 * @author Daniel
 */
public class File {

    private String name;
    private int size;          // tamaño en bloques
    private Block firstBlock;  // referencia al primer bloque encadenado
    private String owner;      // usuario que lo creó
    private boolean isPublic;  // permisos básicos (público/privado)

    public File(String name, int size, String owner, boolean isPublic) {
        this.name = name;
        this.size = size;
        this.owner = owner;
        this.isPublic = isPublic;
        this.firstBlock = null; // se asigna al crearlo en DiskSimulator
    }

    // --- Getters y Setters ---
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Block getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(Block firstBlock) {
        this.firstBlock = firstBlock;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", owner='" + owner + '\'' +
                ", public=" + isPublic +
                ", firstBlock=" + (firstBlock != null ? firstBlock.getId() : "null") +
                '}';
    }
}
