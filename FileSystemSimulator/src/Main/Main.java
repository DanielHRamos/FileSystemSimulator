/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Disk.DiskSimulator;
import EDD.LinkedList;
import FileSystem.FileSystemManager;
import Process.MyProcess;
import Process.ProcessManager;
import SchedulerManagement.FIFOScheduler;



/**
 *
 * @author Daniel
 */


public class Main {
    public static void main(String[] args) {
        DiskSimulator disk = new DiskSimulator(20);
        FileSystemManager fs = new FileSystemManager(disk);

        ProcessManager pm = new ProcessManager(new FIFOScheduler(), fs, 0);

        LinkedList<MyProcess> ejecutados = new LinkedList<>();

        pm.addProcess(new MyProcess("daniel", "CREATE", "doc1.txt", 7));
        pm.addProcess(new MyProcess("daniel", "READ", "doc1.txt", 7));
        pm.addProcess(new MyProcess("daniel", "UPDATE", "doc1.txt", 7));
        pm.addProcess(new MyProcess("daniel", "DELETE", "doc1.txt", 7));

        while (pm.hasProcesses()) {
            MyProcess running = pm.nextProcess();
            ejecutados.add(running);
        }

        pm.printMetrics(ejecutados);
    }
}