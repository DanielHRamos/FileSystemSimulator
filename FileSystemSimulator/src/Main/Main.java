/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Process.MyProcess;
import Process.ProcessManager;

/**
 *
 * @author Daniel
 */

public class Main {
    public static void main(String[] args) {
        ProcessManager pm = new ProcessManager();

        // Crear procesos CRUD simulados
        MyProcess p1 = new MyProcess("daniel", "CREATE", "doc1.txt");
        MyProcess p2 = new MyProcess("daniel", "DELETE", "doc1.txt");

        // Agregar a la cola
        pm.addProcess(p1);
        pm.addProcess(p2);

        // Ejecutar procesos en orden FIFO
        while (pm.hasProcesses()) {
            MyProcess running = pm.nextProcess();
            // Aquí podrías simular la operación real (ej: llamar a FileSystemManager)
            pm.terminateProcess(running);
        }
    }
}