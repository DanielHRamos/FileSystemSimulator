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
public class SCANScheduler implements Scheduler {

    private boolean direccionDerecha; 

    public SCANScheduler(boolean direccionDerecha) {
        this.direccionDerecha = direccionDerecha;
    }

    
    public LinkedList<MyProcess> planificar(LinkedList<MyProcess> procesos, int posicionCabezal) {
        LinkedList<MyProcess> orden = new LinkedList<>();

        
        LinkedList<MyProcess> pendientes = new LinkedList<>();
        for (int i = 0; i < procesos.size(); i++) {
            pendientes.add(procesos.get(i));
        }

        
        ordenarPorBloque(pendientes);

        if (direccionDerecha) {
            
            for (int i = 0; i < pendientes.size(); i++) {
                MyProcess p = pendientes.get(i);
                if (p.getTargetBlock() >= posicionCabezal) {
                    orden.add(p);
                }
            }
           
            for (int i = pendientes.size() - 1; i >= 0; i--) {
                MyProcess p = pendientes.get(i);
                if (p.getTargetBlock() < posicionCabezal) {
                    orden.add(p);
                }
            }
        } else {
            
            for (int i = pendientes.size() - 1; i >= 0; i--) {
                MyProcess p = pendientes.get(i);
                if (p.getTargetBlock() <= posicionCabezal) {
                    orden.add(p);
                }
            }
            
            for (int i = 0; i < pendientes.size(); i++) {
                MyProcess p = pendientes.get(i);
                if (p.getTargetBlock() > posicionCabezal) {
                    orden.add(p);
                }
            }
        }

        return orden;
    }

    
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
