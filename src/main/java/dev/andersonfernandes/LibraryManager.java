package dev.andersonfernandes;

import dev.andersonfernandes.views.MaterialsViews;
import dev.andersonfernandes.views.RentalsViews;

import java.util.Scanner;

public class LibraryManager {
    public static void main(String[] args) {
        System.out.println("Inicializando Gerenciador de Biblioteca");

        Scanner in = new Scanner(System.in);
        in.useDelimiter("\n");
        MaterialsViews materialsViews = new MaterialsViews(in);
        RentalsViews rentalsViews = new RentalsViews(in);

        int selection;

        do {
            System.out.println("\nSelecione uma das opções a seguir: ");
            System.out.println("1 - Materiais");
            System.out.println("2 - Locações");
            System.out.println("3 - Sair");
            System.out.print(">> ");

            selection = in.nextInt();

            switch (selection) {
                case 1 -> materialsViews.menu();
                case 2 -> rentalsViews.menu();
                case 3 -> System.out.println("Encerrando aplicação ...");
                default -> System.out.println("Opção Inválida");
            }
        } while (selection != 3);
    }
}
