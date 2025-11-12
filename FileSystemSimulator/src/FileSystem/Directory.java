/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileSystem;

import EDD.LinkedList;

/**
 *
 * @author Daniel
 */
public class Directory {
    private String name;
    private Directory parent;
    private LinkedList<File> files;
    private LinkedList<Directory> subDirs;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.files = new LinkedList<>();
        this.subDirs = new LinkedList<>();
    }

    public String getName() { return name; }
    public Directory getParent() { return parent; }
    public LinkedList<File> getFiles() { return files; }
    public LinkedList<Directory> getSubDirs() { return subDirs; }

    public void addFile(File f) {
        files.add(f);
    }

    public void removeFile(File f) {
        files.remove(f);
    }

    public void addDirectory(Directory d) {
        subDirs.add(d);
    }

    public void removeDirectory(Directory d) {
        subDirs.remove(d);
    }

    /**
     * Busca un archivo por nombre en este directorio.
     */
    public File findFile(String name) {
        for (int i = 0; i < files.size(); i++) {
            File f = files.get(i);
            if (f.getName().equals(name)) return f;
        }
        return null;
    }

    /**
     * Lista el contenido del directorio.
     */
    public void listContents() {
        System.out.println("Directorio: " + name);
        System.out.println("Archivos:");
        for (int i = 0; i < files.size(); i++) {
            System.out.println("  " + files.get(i));
        }
        System.out.println("Subdirectorios:");
        for (int i = 0; i < subDirs.size(); i++) {
            System.out.println("  " + subDirs.get(i).getName());
        }
    }
}
