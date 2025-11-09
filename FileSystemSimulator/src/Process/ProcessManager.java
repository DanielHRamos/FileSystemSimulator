/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

import EDD.Queue;

/**
 *
 * @author Daniel
 */
public class ProcessManager {

    private Queue<MyProcess> readyQueue;

    public ProcessManager() {
        this.readyQueue = new Queue<>();
    }

    public void addProcess(MyProcess p) {
        p.setState(ProcessState.READY);
        readyQueue.enqueue(p);
        System.out.println("Proceso agregado a la cola: " + p);
    }

    public MyProcess nextProcess() {
        MyProcess p = readyQueue.dequeue();
        if (p != null) {
            p.setState(ProcessState.RUNNING);
            System.out.println("Ejecutando proceso: " + p);
        }
        return p;
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
