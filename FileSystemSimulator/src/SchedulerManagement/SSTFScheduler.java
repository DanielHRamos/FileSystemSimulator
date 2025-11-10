/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SchedulerManagement;

import EDD.LinkedList;
import Process.MyProcess;

/**
 *
 * @author Daniel
 */
public class SSTFScheduler implements Scheduler {

    public LinkedList<MyProcess> planificar(LinkedList<MyProcess> procesos, int posicionCabezal) {
        LinkedList<MyProcess> orden = new LinkedList<>();

        // Copia de la lista original para ir eliminando
        LinkedList<MyProcess> pendientes = new LinkedList<>();
        for (int i = 0; i < procesos.size(); i++) {
            pendientes.add(procesos.get(i));
        }

        int cabezal = posicionCabezal;

        while (!pendientes.isEmpty()) {
            // Buscar el proceso más cercano al cabezal
            int mejorIndex = 0;
            int mejorDistancia = Integer.MAX_VALUE;

            for (int i = 0; i < pendientes.size(); i++) {
                MyProcess p = pendientes.get(i);
                int bloqueObjetivo = p.getTargetBlock(); // 👈 necesitas este método en MyProcess
                int distancia = Math.abs(bloqueObjetivo - cabezal);

                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorIndex = i;
                }
            }

            // Seleccionar el proceso más cercano
            MyProcess seleccionado = pendientes.get(mejorIndex);
            orden.add(seleccionado);

            // Actualizar posición del cabezal
            cabezal = seleccionado.getTargetBlock();

            // Eliminar de pendientes
            pendientes.remove(seleccionado);
        }

        return orden;
    }
}