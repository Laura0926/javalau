package com.mycompany.matrices;

import java.util.ArrayList;
import java.util.Locale;  //AVERIGUAR QUE HACE.....
import java.util.Scanner;
 
public class Matrices {

 

 
 
    // --------------------------------------------------------------------
    // CONSTANTES DE CLASE (static final): los índices de cada campo dentro
    // de un registro. Al ser "static", existe UNA sola copia compartida por
    // todos los métodos de la clase (no por cada empleado ni por cada
    // llamada); al ser "final", su valor no puede cambiar. Por eso se
    // escriben una sola vez aquí arriba y se usan en todas las funciones
    // como registro[SALARIO] en vez de registro[2].
    // --------------------------------------------------------------------
    static final int NOMBRE = 0;
    static final int CEDULA = 1;
    static final int SALARIO = 2;
    static final int DIAS = 3;
    static final int TOTAL_PAGAR = 4;
    static final int VALOR_DIA = 5;
    static final int DIAS_MES = 30;
    static ArrayList<Object[]> empleados = new ArrayList<>();   // nace vacío: sin datos precargados
    static Scanner sc = new Scanner(System.in);
    static Locale co = Locale.forLanguageTag("es-CO");          // formato $1.750.905
 
    
    // Nombres visibles de cada campo, en el mismo orden que las constantes.
    static final String[] NOMBRE_CAMPO = {
        "Nombre", "Cédula", "Salario mensual",
        "Días trabajados", "Total a pagar", "Valor del día"
    };
 
    // ======================================================================
    //  MAIN: crea la lista, el Scanner y el formato regional UNA sola vez,
    //  y le entrega el control al menú. Es la única función que arranca el
    //  programa; todas las demás se llaman desde menu().
    // ======================================================================
    public static void main(String[] args) {
        menu();
        sc.close();
    }
 
    // ======================================================================
    //  MENU: muestra las opciones, lee la elección y delega en la función
    //  correspondiente. Se repite con do-while hasta que el usuario elige 0.
    // ======================================================================
    static void menu() {
        int opcion;
        do {
            System.out.println("\n===== GESTIÓN DE NÓMINA · ANDES RETAIL S.A.S. =====");
            System.out.println("Registros cargados: " + empleados.size());
            System.out.println("1. Ingresar empleado(s)");
            System.out.println("2. Consultar");
            System.out.println("3. Modificar un dato de un registro");
            System.out.println("4. Eliminar");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(sc.nextLine().trim());
 
            switch (opcion) {
                case 1:
                    ingresar();
                    break;
                case 2:
                    consultar();
                    break;
                case 3:
                    modificar();
                    break;
                case 4:
                    eliminar();
                    break;
                case 0:
                    System.out.println("\nCierre del programa. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }
 
    // ======================================================================
    //  INGRESAR: captura empleados uno tras otro, con validaciones, hasta
    //  que el usuario responda "N" a "¿Desea ingresar otro registro?".
    //  No devuelve nada (void): agrega directamente sobre "empleados",
    //  que es el mismo ArrayList que vive en main().
    // ======================================================================
    static void ingresar() {
        String continuar;
        do {
            System.out.println("\n--- INGRESAR EMPLEADO ---");
 
            // Nombre: no vacío
            String nombre;
            do {
                System.out.print("Nombre completo: ");
                nombre = sc.nextLine().trim();
                if (nombre.isEmpty()) {
                    System.out.println("  El nombre no puede estar vacío.");
                }
            } while (nombre.isEmpty());
 
            // Cédula: no vacía y sin repetir (se recorre la lista comparando índice CEDULA)
            String cedula;
            boolean repetida;
            do {
                System.out.print("Cédula: ");
                cedula = sc.nextLine().trim();
                repetida = false;
                for (int i = 0; i < empleados.size(); i++) {
                    String cedulaExistente = (String) empleados.get(i)[CEDULA];
                    if (cedula.equals(cedulaExistente)) {
                        repetida = true;
                    }
                }
                if (cedula.isEmpty()) {
                    System.out.println("  La cédula no puede estar vacía.");
                } else if (repetida) {
                    System.out.println("  Ya existe un empleado con esa cédula.");
                }
            } while (cedula.isEmpty() || repetida);
 
            // Salario: número mayor que 0
            double salario;
            do {
                System.out.print("Salario mensual: ");
                salario = Double.parseDouble(sc.nextLine().trim());
                if (salario <= 0) {
                    System.out.println("  El salario debe ser mayor que 0.");
                }
            } while (salario <= 0);
 
            // Días trabajados: entero entre 0 y 30
            int dias;
            do {
                System.out.print("Días trabajados en el mes (0-30): ");
                dias = Integer.parseInt(sc.nextLine().trim());
                if (dias < 0 || dias > DIAS_MES) {
                    System.out.println("  Valor inválido: debe estar entre 0 y 30.");
                }
            } while (dias < 0 || dias > DIAS_MES);
 
            // Campos calculados: no se piden por teclado, se derivan del salario y los días
            double valorDia = Math.round((salario / DIAS_MES) * 100) / 100.0;
            double totalPagar = Math.round(valorDia * dias * 100) / 100.0;
 
            // Se arma el registro completo y se agrega en UNA sola operación
            Object[] registro = new Object[6];
            registro[NOMBRE] = nombre;
            registro[CEDULA] = cedula;
            registro[SALARIO] = salario;
            registro[DIAS] = dias;
            registro[TOTAL_PAGAR] = totalPagar;
            registro[VALOR_DIA] = valorDia;
            empleados.add(registro);
 
            System.out.printf(co, "Registro guardado. Valor del día: $%,.2f | Total a pagar: $%,.2f%n",
                    valorDia, totalPagar);
            System.out.println("Registros cargados: " + empleados.size());
 
            System.out.print("¿Desea ingresar otro registro? (S/N): ");
            continuar = sc.nextLine().trim();
 
        } while (continuar.equalsIgnoreCase("S"));
    }
 
    // ======================================================================
    //  CONSULTAR: submenú propio (Individual / Todos / Regresar). No
    //  modifica la lista, solo la lee; por eso ni siquiera necesita
    //  devolver nada.
    // ======================================================================
    static void consultar() {
        int opcionConsulta;
        do {
            System.out.println("\n--- CONSULTAR ---");
            System.out.println("Registros cargados: " + empleados.size());
            System.out.println("1. Individual");
            System.out.println("2. Todos");
            System.out.println("3. Regresar al menú");
            System.out.print("Seleccione una opción: ");
            opcionConsulta = Integer.parseInt(sc.nextLine().trim());
 
            if (opcionConsulta == 1) {
                // --- Consulta individual: por posición ---
                if (empleados.isEmpty()) {
                    System.out.println("No hay empleados registrados.");
                } else {
                    System.out.print("Ingrese la posición a consultar (0 a "
                            + (empleados.size() - 1) + "): ");
                    int pos = Integer.parseInt(sc.nextLine().trim());
                    if (pos < 0 || pos >= empleados.size()) {
                        System.out.println("Posición fuera de rango.");
                    } else {
                        Object[] r = empleados.get(pos);
                        System.out.println("\n--- Registro en la posición " + pos + " ---");
                        System.out.println(NOMBRE_CAMPO[NOMBRE] + " : " + r[NOMBRE]);
                        System.out.println(NOMBRE_CAMPO[CEDULA] + " : " + r[CEDULA]);
                        System.out.printf(co, "%s : $%,.2f%n", NOMBRE_CAMPO[SALARIO], (Double) r[SALARIO]);
                        System.out.println(NOMBRE_CAMPO[DIAS] + " : " + r[DIAS]);
                        System.out.printf(co, "%s : $%,.2f%n", NOMBRE_CAMPO[TOTAL_PAGAR], (Double) r[TOTAL_PAGAR]);
                        System.out.printf(co, "%s : $%,.2f%n", NOMBRE_CAMPO[VALOR_DIA], (Double) r[VALOR_DIA]);
                    }
                }
 
            } else if (opcionConsulta == 2) {
                // --- Consulta de todos: tabla completa ---
                if (empleados.isEmpty()) {
                    System.out.println("No hay empleados registrados.");
                } else {
                    System.out.printf("%-4s %-20s %-12s %14s %6s %14s %12s%n",
                            "Pos", "Nombre", "Cédula", "Salario", "Días", "Total a pagar", "Valor día");
                    for (int i = 0; i < empleados.size(); i++) {
                        Object[] r = empleados.get(i);
                        System.out.printf(co, "%-4d %-20s %-12s %,14.2f %6d %,14.2f %,12.2f%n",
                                i, r[NOMBRE], r[CEDULA], (Double) r[SALARIO],
                                (Integer) r[DIAS], (Double) r[TOTAL_PAGAR], (Double) r[VALOR_DIA]);
                    }
                }
 
            } else if (opcionConsulta != 3) {
                System.out.println("Opción no válida.");
            }
 
        } while (opcionConsulta != 3);
    }
 
    // ======================================================================
    //  MODIFICAR: cambia un campo específico de un registro (por posición).
    //  Si el campo es salario o días, recalcula en cascada el total a pagar
    //  y el valor del día, porque dependen de esos dos valores.
    // ======================================================================
    static void modificar() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.print("Ingrese la posición del registro a modificar (0 a "
                + (empleados.size() - 1) + "): ");
        int pos = Integer.parseInt(sc.nextLine().trim());
        if (pos < 0 || pos >= empleados.size()) {
            System.out.println("Posición fuera de rango.");
            return;
        }
        Object[] registro = empleados.get(pos);
 
        System.out.println("¿Qué dato desea modificar?");
        for (int i = 0; i < NOMBRE_CAMPO.length; i++) {
            System.out.println((i + 1) + ". " + NOMBRE_CAMPO[i]);
        }
        System.out.print("Seleccione una opción: ");
        int campo = Integer.parseInt(sc.nextLine().trim()) - 1;   // -1 para usarlo como índice
 
        if (campo < 0 || campo >= NOMBRE_CAMPO.length) {
            System.out.println("Opción no válida.");
            return;
        }
 
        if (campo == TOTAL_PAGAR || campo == VALOR_DIA) {
            System.out.println("Aviso: este campo normalmente se calcula solo a partir del "
                    + "salario y los días trabajados. Si lo cambia manualmente, puede quedar "
                    + "inconsistente con esos dos valores.");
        }
 
        System.out.print("Nuevo valor para " + NOMBRE_CAMPO[campo] + ": ");
        String entrada = sc.nextLine().trim();
 
        if (campo == NOMBRE) {
            registro[NOMBRE] = entrada;
 
        } else if (campo == CEDULA) {
            boolean repetida = false;
            for (int i = 0; i < empleados.size(); i++) {
                if (i != pos && entrada.equals((String) empleados.get(i)[CEDULA])) {
                    repetida = true;
                }
            }
            if (repetida) {
                System.out.println("Ya existe otro empleado con esa cédula. No se realizó el cambio.");
                return;
            }
            registro[CEDULA] = entrada;
 
        } else if (campo == SALARIO) {
            double nuevoSalario = Double.parseDouble(entrada);
            registro[SALARIO] = nuevoSalario;
            // Recalcular en cascada: el total y el valor del día dependen del salario
            int diasActuales = (Integer) registro[DIAS];
            double nuevoValorDia = Math.round((nuevoSalario / DIAS_MES) * 100) / 100.0;
            registro[VALOR_DIA] = nuevoValorDia;
            registro[TOTAL_PAGAR] = Math.round(nuevoValorDia * diasActuales * 100) / 100.0;
            System.out.println("Se recalcularon automáticamente el valor del día y el total a pagar.");
 
        } else if (campo == DIAS) {
            int nuevosDias = Integer.parseInt(entrada);
            if (nuevosDias < 0 || nuevosDias > DIAS_MES) {
                System.out.println("Valor inválido: debe estar entre 0 y 30. No se realizó el cambio.");
                return;
            }
            registro[DIAS] = nuevosDias;
            // Recalcular en cascada: el total depende de los días
            double valorDiaActual = (Double) registro[VALOR_DIA];
            registro[TOTAL_PAGAR] = Math.round(valorDiaActual * nuevosDias * 100) / 100.0;
            System.out.println("Se recalculó automáticamente el total a pagar.");
 
        } else if (campo == TOTAL_PAGAR) {
            registro[TOTAL_PAGAR] = Double.parseDouble(entrada);
 
        } else if (campo == VALOR_DIA) {
            registro[VALOR_DIA] = Double.parseDouble(entrada);
        }
 
        System.out.println("Registro actualizado en la posición " + pos + ".");
    }
 
    // ======================================================================
    //  ELIMINAR: submenú propio (borrar un dato de un registro / borrar
    //  todos los registros / regresar). No necesita Locale porque no
    //  imprime valores en pesos.
    // ======================================================================
    static void eliminar() {
        int opcionEliminar;
        do {
            System.out.println("\n--- ELIMINAR ---");
            System.out.println("Registros cargados: " + empleados.size());
            System.out.println("1. Borrar un dato de un registro");
            System.out.println("2. Borrar todos los registros");
            System.out.println("3. Regresar al menú");
            System.out.print("Seleccione una opción: ");
            opcionEliminar = Integer.parseInt(sc.nextLine().trim());
 
            if (opcionEliminar == 1) {
                if (empleados.isEmpty()) {
                    System.out.println("No hay empleados registrados.");
                } else {
                    System.out.print("Ingrese la posición del registro (0 a "
                            + (empleados.size() - 1) + "): ");
                    int pos = Integer.parseInt(sc.nextLine().trim());
                    if (pos < 0 || pos >= empleados.size()) {
                        System.out.println("Posición fuera de rango.");
                    } else {
                        Object[] registro = empleados.get(pos);
                        System.out.println("¿Qué dato desea borrar?");
                        for (int i = 0; i < NOMBRE_CAMPO.length; i++) {
                            System.out.println((i + 1) + ". " + NOMBRE_CAMPO[i]);
                        }
                        System.out.print("Seleccione una opción: ");
                        int campo = Integer.parseInt(sc.nextLine().trim()) - 1;
 
                        if (campo < 0 || campo >= NOMBRE_CAMPO.length) {
                            System.out.println("Opción no válida.");
                        } else if (campo == NOMBRE || campo == CEDULA) {
                            // Campo de texto: se deja vacío
                            registro[campo] = "";
                            System.out.println(NOMBRE_CAMPO[campo] + " quedó vacío en la posición " + pos + ".");
                        } else if (campo == SALARIO || campo == DIAS) {
                            // Si se borra el salario o los días, el total y el valor
                            // del día dejan de tener sentido: se reinician también.
                            registro[campo] = (campo == SALARIO) ? 0.0 : 0;
                            registro[TOTAL_PAGAR] = 0.0;
                            registro[VALOR_DIA] = 0.0;
                            System.out.println(NOMBRE_CAMPO[campo] + " quedó en 0. El total a pagar "
                                    + "y el valor del día también se reiniciaron a 0.");
                        } else {
                            // TOTAL_PAGAR o VALOR_DIA
                            registro[campo] = 0.0;
                            System.out.println(NOMBRE_CAMPO[campo] + " quedó en 0 en la posición " + pos + ".");
                        }
                    }
                }
 
            } else if (opcionEliminar == 2) {
                if (empleados.isEmpty()) {
                    System.out.println("No hay empleados registrados.");
                } else {
                    System.out.print("¿Confirma borrar TODOS los registros? Esta acción no se "
                            + "puede deshacer (S/N): ");
                    String confirmacion = sc.nextLine().trim();
                    if (confirmacion.equalsIgnoreCase("S")) {
                        empleados.clear();
                        System.out.println("Se eliminaron todos los registros. Registros actuales: "
                                + empleados.size());
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                }
 
            } else if (opcionEliminar != 3) {
                System.out.println("Opción no válida.");
            }
 
        } while (opcionEliminar != 3);
    }
}

