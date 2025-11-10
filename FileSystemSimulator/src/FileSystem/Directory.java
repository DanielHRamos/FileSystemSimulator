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
    private LinkedList<File> files;          // archivos dentro del directorio
    private LinkedList<Directory> subDirs;   // subdirectorios
    private Directory parent;                // referencia al directorio padre

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.files = new LinkedList<>();
        this.subDirs = new LinkedList<>();
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

    public LinkedList<Directory> getSubDirs() {
        return subDirs;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void addSubDir(Directory dir) {
        subDirs.add(dir);
    }

    @Override
    public String toString() {
        return "Directory{"
                + "name='" + name + '\''
                + ", files=" + files.size()
                + ", subDirs=" + subDirs.size()
                + '}';
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
}
