/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processs;

import EDD.LinkedList;
import FileSystem.File;
import FileSystem.FileSystemManager;
import GUI.FileSystemSim;
import SchedulerManagement.Scheduler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class ProcessManager {

    private LinkedList<MyProcess> readyQueue;
    private Scheduler scheduler;
    private FileSystemManager fs;
    private int cabezal;
    private int totalMovimientos;
    private int tiempoSimulado;
    private FileSystemSim gui;

    public ProcessManager(Scheduler scheduler, FileSystemManager fs, int posicionInicial, FileSystemSim gui) {
        this.scheduler = scheduler;
        this.fs = fs;
        this.cabezal = posicionInicial;
        this.readyQueue = new LinkedList<>();
        this.gui = gui;
    }

    public void addProcess(MyProcess p) {
        p.setState(ProcessState.READY);
        readyQueue.add(p);
        System.out.println("Proceso agregado: " + p);
    }

    public MyProcess nextProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }

        LinkedList<MyProcess> ordenada = scheduler.planificar(readyQueue, cabezal);
        MyProcess p = ordenada.get(0);
        readyQueue.remove(p);

        p.setState(ProcessState.RUNNING);
        p.setStartTime(tiempoSimulado);
        System.out.println("Ejecutando proceso: " + p);

        ejecutarOperacion(p);

        int movimiento = Math.abs(cabezal - p.getTargetBlock());
        totalMovimientos += movimiento;
        cabezal = p.getTargetBlock();

        tiempoSimulado += 5;
        p.setFinishTime(tiempoSimulado);
        terminateProcess(p);

        return p;
    }

    private void ejecutarOperacion(MyProcess p) {
        switch (p.getOperation()) {
            case "CREATE":
                fs.createFile(p.getTarget(), 3, p.getOwner());
                break;
            case "DELETE":
                File f = fs.getCurrentDir().findFile(p.getTarget());
                if (f != null) {
                    fs.deleteFile(f);
                }
                break;
            case "READ":
                File fr = fs.getCurrentDir().findFile(p.getTarget());
                if (fr != null) {
                    fs.readFile(fr);
                }
                break;
            case "UPDATE":
                File fu = fs.getCurrentDir().findFile(p.getTarget());
                if (fu != null) {
                    fs.updateFile(fu, fu.getSize() + 1);
                }
                break;
            default:
                System.out.println("Operación no reconocida: " + p.getOperation());
        }

        
        gui.updateAssignmentTable();
        gui.getDiskPanel().repaint();   
    }

    public void terminateProcess(MyProcess p) {
        if (p != null) {
            p.setState(ProcessState.TERMINATED);
            System.out.println("Proceso terminado: " + p);
        }
    }

    public boolean hasProcesses() {
        return !readyQueue.isEmpty();
    }

    public void printMetrics(LinkedList<MyProcess> ejecutados) {
        int totalWait = 0;
        int totalTurnaround = 0;

        for (int i = 0; i < ejecutados.size(); i++) {
            MyProcess p = ejecutados.get(i);
            totalWait += p.getWaitTime();
            totalTurnaround += p.getTurnaroundTime();
        }

        double avgWait = (double) totalWait / ejecutados.size();
        double avgTurnaround = (double) totalTurnaround / ejecutados.size();

        System.out.println("\n--- MÉTRICAS ---");
        System.out.println("Tiempo de espera promedio: " + avgWait);
        System.out.println("Tiempo de retorno promedio: " + avgTurnaround);
        System.out.println("Movimientos totales del cabezal: " + totalMovimientos);
    }

    public void loadProcessesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String owner = parts[0].trim();
                    String operation = parts[1].trim();
                    String target = parts[2].trim();
                    int block = Integer.parseInt(parts[3].trim());

                    MyProcess p = new MyProcess(owner, operation, target, block);
                    addProcess(p);
                }
            }
            System.out.println("Procesos cargados desde archivo: " + filePath);
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public LinkedList<MyProcess> getReadyQueue() {
        return readyQueue;
    }

}
