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
public class CSCANScheduler implements Scheduler {

    public LinkedList<MyProcess> planificar(LinkedList<MyProcess> procesos, int posicionCabezal) {
        LinkedList<MyProcess> orden = new LinkedList<>();

        // Copiar procesos
        LinkedList<MyProcess> pendientes = new LinkedList<>();
        for (int i = 0; i < procesos.size(); i++) {
            pendientes.add(procesos.get(i));
        }

        // Ordenar por bloque objetivo
        ordenarPorBloque(pendientes);

        // Primero atender los mayores o iguales al cabezal
        for (int i = 0; i < pendientes.size(); i++) {
            MyProcess p = pendientes.get(i);
            if (p.getTargetBlock() >= posicionCabezal) {
                orden.add(p);
            }
        }

        // Luego atender los menores (como si el cabezal saltara al inicio)
        for (int i = 0; i < pendientes.size(); i++) {
            MyProcess p = pendientes.get(i);
            if (p.getTargetBlock() < posicionCabezal) {
                orden.add(p);
            }
        }

        return orden;
    }

    // Método auxiliar para ordenar por bloque objetivo
    private void ordenarPorBloque(LinkedList<MyProcess> lista) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (lista.get(j).getTargetBlock() > lista.get(j + 1).getTargetBlock()) {
                    MyProcess temp = lista.get(j);
                    lista.remove(lista.get(j));
                    lista.addAt(j + 1, temp);
                }
            }
        }
    }
}
