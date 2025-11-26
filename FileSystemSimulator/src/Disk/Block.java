/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Disk;

/**
 *
 * @author Daniel
 */
public class Block {
    
    private final int id;              
    private boolean ocupado;           
    private String owner;              
    private Block next;                

    public Block(int id) {
        this.id = id;
        this.ocupado = false;
        this.owner = null;
        this.next = null;
    }

    
    public int getId() {
        return id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado, String owner) {
        this.ocupado = ocupado;
        this.owner = ocupado ? owner : null;
    }

    public String getOwner() {
        return owner;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", ocupado=" + ocupado +
                ", owner='" + owner + '\'' +
                ", next=" + (next != null ? next.id : "null") +
                '}';
    }
}
