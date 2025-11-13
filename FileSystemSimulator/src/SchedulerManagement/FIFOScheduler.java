/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchedulerManagement;

import EDD.LinkedList;
import processs.MyProcess;

/**
 *
 * @author Daniel
 */
public class FIFOScheduler implements Scheduler {

    @Override
    public LinkedList<MyProcess> planificar(LinkedList<MyProcess> procesos, int posicionCabezal) {
        // En FIFO no se reordena: se devuelve tal cual
        return procesos;
    }
}
