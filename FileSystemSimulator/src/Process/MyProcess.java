/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

/**
 *
 * @author Daniel
 */
public class MyProcess {

    private static int nextId = 1;   // contador global para asignar IDs

    private final int pid;           // identificador único
    private String owner;            // usuario que lo creó
    private String operation;        // tipo de operación (CREATE, READ, UPDATE, DELETE)
    private String target;           // archivo/directorio objetivo
    private ProcessState state;      // estado actual

    public MyProcess(String owner, String operation, String target) {
        this.pid = nextId++;
        this.owner = owner;
        this.operation = operation;
        this.target = target;
        this.state = ProcessState.NEW;
    }

    // --- Getters y Setters ---
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

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", owner='" + owner + '\'' +
                ", operation='" + operation + '\'' +
                ", target='" + target + '\'' +
                ", state=" + state +
                '}';
    }
}
