/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package processs;

/**
 *
 * @author Daniel
 */
public class MyProcess {

    private static int nextId = 1;
    private int pid;
    private String owner;
    private String operation;
    private String target;
    private ProcessState state;
    private int targetBlock;

    // Métricas
    private int arrivalTime;     // cuando se agregó a la cola
    private int startTime;       // cuando comenzó a ejecutarse
    private int finishTime;      // cuando terminó
    private int waitTime;        // tiempo en READY
    private int turnaroundTime;  // tiempo total desde NEW hasta TERMINATED

    public MyProcess(String owner, String operation, String target, int targetBlock) {
        this.pid = nextId++;
        this.owner = owner;
        this.operation = operation;
        this.target = target;
        this.state = ProcessState.NEW;
        this.targetBlock = targetBlock;
        this.arrivalTime = (int) System.currentTimeMillis(); // simulación simple
    }

    // Getters y setters
    public int getPid() {
        return pid;
    }

    public String getOwner() {
        return owner;
    }

    public String getOperation() {
        return operation;
    }

    public String getTarget() {
        return target;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getTargetBlock() {
        return targetBlock;
    }

    public void setStartTime(int t) {
        this.startTime = t;
    }

    public void setFinishTime(int t) {
        this.finishTime = t;
        this.turnaroundTime = finishTime - arrivalTime;
        this.waitTime = startTime - arrivalTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    @Override
    public String toString() {
        return "Process{"
                + "pid=" + pid
                + ", owner='" + owner + '\''
                + ", operation='" + operation + '\''
                + ", target='" + target + '\''
                + ", state=" + state
                + ", wait=" + waitTime
                + ", turnaround=" + turnaroundTime
                + '}';
    }
}
