/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;

/**
 *
 * @author Daniel
 */
public enum ProcessState {
    NEW,        // recién creado
    READY,      // listo para ejecutar
    RUNNING,    // en ejecución
    BLOCKED,    // esperando E/S
    TERMINATED  // finalizado
}