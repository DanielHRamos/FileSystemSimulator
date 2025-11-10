/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import EDD.LinkedList;
import FileSystem.File;
import FileSystem.FileSystemManager;
import SchedulerManagement.Scheduler;

/**
 *
 * @author Daniel
 */
public class ProcessManager {

    private LinkedList<MyProcess> readyQueue;
    private Scheduler scheduler;
    private FileSystemManager fs;
    private int cabezal;

    public ProcessManager(Scheduler scheduler, FileSystemManager fs, int posicionInicial) {
        this.readyQueue = new LinkedList<>();
        this.scheduler = scheduler;
        this.fs = fs;
        this.cabezal = posicionInicial;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
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

        // Reordenar la cola según la política activa
        LinkedList<MyProcess> ordenada = scheduler.planificar(readyQueue, cabezal);

        // Tomar el primero de la lista reordenada
        MyProcess p = ordenada.get(0);
        readyQueue.remove(p);

        // Simular transición de estados
        p.setState(ProcessState.RUNNING);
        System.out.println("Ejecutando proceso: " + p);

        // Simular que algunos procesos pasan a BLOCKED antes de terminar
        if (p.getOperation().equals("READ")) {
            p.setState(ProcessState.BLOCKED);
            System.out.println("Proceso bloqueado esperando E/S: " + p);

            // Simular que luego vuelve a READY
            p.setState(ProcessState.READY);
            readyQueue.add(p);
            System.out.println("Proceso vuelve a READY: " + p);
            return p;
        }

        // Ejecutar la operación real
        ejecutarOperacion(p);

        // Actualizar posición del cabezal
        cabezal = p.getTargetBlock();

        return p;
    }

    private void ejecutarOperacion(MyProcess p) {
        switch (p.getOperation()) {
            case "CREATE":
                fs.createFile(p.getTarget(), 3, p.getOwner(), true);
                break;
            case "DELETE":
                File f = fs.getCurrentDir().findFile(p.getTarget());
                if (f != null) {
                    fs.deleteFile(f);
                }
                break;
            case "READ":
                System.out.println("Leyendo archivo: " + p.getTarget());
                break;
            case "UPDATE":
                System.out.println("Actualizando archivo: " + p.getTarget());
                break;
            default:
                System.out.println("Operación no reconocida: " + p.getOperation());
        }
    }

    public boolean hasProcesses() {
        return !readyQueue.isEmpty();
    }

    public void terminateProcess(MyProcess p) {
        if (p != null) {
            p.setState(ProcessState.TERMINATED);
            System.out.println("Proceso terminado: " + p);
        }
    }
}
