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

    private static int nextId = 1;
    private int pid;
    private String owner;
    private String operation;
    private String target;
    private ProcessState state;
    private int targetBlock;

    public MyProcess(String owner, String operation, String target, int targetBlock) {
        this.pid = nextId++;
        this.owner = owner;
        this.operation = operation;
        this.target = target;
        this.state = ProcessState.NEW;
        this.targetBlock = targetBlock;
    }

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

    @Override
    public String toString() {
        return "Process{"
                + "pid=" + pid
                + ", owner='" + owner + '\''
                + ", operation='" + operation + '\''
                + ", target='" + target + '\''
                + ", state=" + state
                + '}';
    }
}
