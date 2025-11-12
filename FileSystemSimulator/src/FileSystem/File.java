/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileSystem;



/**
 *
 * @author Daniel
 */
public class File {
    private String name;
    private int size;
    private int startBlock;
    private boolean isText;

    public File(String name, int size, int startBlock, boolean isText) {
        this.name = name;
        this.size = size;
        this.startBlock = startBlock;
        this.isText = isText;
    }

    public String getName() { 
        return name; 
    }
    
    public int getSize() { 
        return size; 
    }
    
    public void setSize(int size) { 
        this.size = size; 
    }
    
    public int getStartBlock() { 
        return startBlock; 
    }
    
    public boolean isText() { 
        return isText; 
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", startBlock=" + startBlock +
                ", isText=" + isText +
                '}';
    }
}
