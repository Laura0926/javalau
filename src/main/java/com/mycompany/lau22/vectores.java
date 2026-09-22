package com.mycompany.lau22;
import java.util.Scanner;

public class vectores {
    static Scanner sc=new Scanner(System.in);
static int tam = 10;
static String []nom =new String [tam];
static String []ape =new String [tam];
static double []sal =new double [tam];
static int []dt =new int [tam];
static double []tp =new double [tam];
static int opc ;
    public static void main(String[] args) {
        menu();
    }
    public static void menu() {
        System.out.println("==============================");
        System.out.println("        MENU PRINSIPAL        ");
        System.out.println("1. CAPTURAR");
        System.out.println("2. CONSULTAR");
        System.out.println("3. MODIFICAR");
        System.out.println("4. ELIMINAR");
        System.out.println("5. SALIR");
        System.out.println("DIGITE SU OPCION");  
        opc = sc.nextInt();
        switch (opc){
            case 1 : 
                System.out.println("CUANTOS REGISTROS NECESITA REGISTRAR");
                tam = sc.nextInt();
                for (int i = 0; i<tam; i++){
                    System.out.println("NOMBRE: ");
                    nom[i]= sc.next();
                    System.out.println("APELLIDO: ");
                    ape[i] = sc.next();
                    System.out.println("Salario: ");
                    sal[i]=sc.nextDouble();
                    System.out.println("DIAS TRABAJADOS: ");
                    dt[i]=sc.nextInt();
                    tp[i] = (sal[i]/30)* dt[i];
                    
                }
            menu();       
            break;
            case 2 : 
                consultar ();
            break;
            case 3 : 
                modificar ();
            break;
            case 4 : 
                eliminar();
            break;
            case 5 :  
            break;
            default: 
                System.out.println("OPCION NO VALIDA...");
                menu();
        }
       
    }
    
    public static void consultar() {
        int des;
        do{
            System.out.println("DIGITE POSICION A CONSULTAR: ");
            int pos = sc.nextInt();
            System.out.println("Nombre: " + nom[pos]);
            System.out.println("Apellido: " + ape[pos]);
            System.out.println("Salario: "+ sal[pos]);
            System.out.println("Dias trabajados: " + dt[pos]);
            System.out.println("Total a pagar: "+ tp [pos]);
            
            System.out.println("Quiere hacer otra consulta? 1=si 0=no");
            des =sc.nextInt();
        }while (des !=0);
        menu();
                
        
    }
    public static void modificar() {
        System.out.println("Que posicion decea modificar?");
        int pos = sc.nextInt();
        System.out.println("Nuevo nombre:");
        nom[pos]=sc.next();
        System.out.println("Nuevo apellido: ");
        ape[pos]=sc.next();
        System.out.println("Nuevo alari :");
        sal[pos]=sc.nextDouble();
        System.out.println("Dias trabajados: ");
        dt[pos]=sc.nextInt();
        tp[pos]= (sal[pos]/30) * dt[pos];
        menu();
    }
    public static void eliminar() {
        System.out.println("Que posicio quiere eliminar?");
        int pos = sc.nextInt();
        nom[pos]="";
        ape[pos]="";
        sal[pos]=0;
        dt [pos]=0;
        tp[pos]=0;
        
        System.out.println("Poicion eliminada");
        menu();
                
        
    }
        
}

