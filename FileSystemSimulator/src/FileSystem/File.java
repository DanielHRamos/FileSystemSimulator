/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileSystem;




public class File {
    private String name;
    private int size;
    private int startBlock;
    private String owner;

    
    public File(String name, int size, int startBlock, String owner) {
        this.name = name;
        this.size = size;
        this.startBlock = startBlock;
        this.owner = owner;
    }

    
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getStartBlock() {
        return startBlock;
    }

    public String getOwner() {
        return owner;
    }

   
    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setStartBlock(int startBlock) {
        this.startBlock = startBlock;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", startBlock=" + startBlock +
                ", owner='" + owner + '\'' +
                '}';
    }
}
