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
    private LinkedList<Directory> directories;

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.files = new LinkedList<>();
        this.directories = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public LinkedList<File> getFiles() {
        return files;
    }

    public LinkedList<Directory> getDirectories() {
        return directories;
    }

    public void addFile(File f) {
        files.add(f);
    }

    public void removeFile(File f) {
        files.remove(f);
    }

    public void addDirectory(Directory d) {
        directories.add(d);
    }

    public void removeDirectory(Directory d) {
        directories.remove(d);
    }

    public File findFile(String name) {
        for (int i = 0; i < files.size(); i++) {
            File f = files.get(i);
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public void listContents() {
        System.out.println("Directorio: " + name);
        System.out.println("Archivos:");
        for (int i = 0; i < files.size(); i++) {
            System.out.println("  " + files.get(i));
        }
        System.out.println("Subdirectorios:");
        for (int i = 0; i < directories.size(); i++) {
            System.out.println("  " + directories.get(i).getName());
        }
    }

    public String toString() {
        return name;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFiles(LinkedList<File> files) {
        this.files = files;
    }

    public void setDirectories(LinkedList<Directory> directories) {
        this.directories = directories;
    }

}
