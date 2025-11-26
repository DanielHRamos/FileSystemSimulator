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
public class SSTFScheduler implements Scheduler {

    public LinkedList<MyProcess> planificar(LinkedList<MyProcess> procesos, int posicionCabezal) {
        LinkedList<MyProcess> orden = new LinkedList<>();

        
        LinkedList<MyProcess> pendientes = new LinkedList<>();
        for (int i = 0; i < procesos.size(); i++) {
            pendientes.add(procesos.get(i));
        }

        int cabezal = posicionCabezal;

        while (!pendientes.isEmpty()) {
            
            int mejorIndex = 0;
            int mejorDistancia = Integer.MAX_VALUE;

            for (int i = 0; i < pendientes.size(); i++) {
                MyProcess p = pendientes.get(i);
                int bloqueObjetivo = p.getTargetBlock(); 
                int distancia = Math.abs(bloqueObjetivo - cabezal);

                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorIndex = i;
                }
            }

            
            MyProcess seleccionado = pendientes.get(mejorIndex);
            orden.add(seleccionado);

            
            cabezal = seleccionado.getTargetBlock();

            
            pendientes.remove(seleccionado);
        }

        return orden;
    }
}