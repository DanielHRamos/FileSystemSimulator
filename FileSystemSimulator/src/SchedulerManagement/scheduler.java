/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SchedulerManagement;

import EDD.LinkedList;
import Process.MyProcess;

/**
 *
 * @author Daniel
 */
public interface Scheduler {
    LinkedList<MyProcess> planificar(LinkedList<MyProcess> procesos, int posicionCabezal);
}
