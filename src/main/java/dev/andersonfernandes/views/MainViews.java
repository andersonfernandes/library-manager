package dev.andersonfernandes.views;

import dev.andersonfernandes.models.Material;
import dev.andersonfernandes.models.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainViews extends BaseViews {
    private MaterialsViews materialsViews;
    private RentalsViews rentalsViews;
    private List<Rental> rentals;

    public MainViews(Scanner in) {
        super(in);

        this.rentals = new ArrayList<>();
        this.materialsViews = new MaterialsViews(in);
        this.rentalsViews = new RentalsViews(in, rentals);
    }

    @Override
    public void menu() {
        int selection;

        do {
            System.out.println("\nSelecione uma das opções a seguir: ");
            System.out.println("1 - Materiais");
            System.out.println("2 - Locações");
            System.out.println("3 - Sair");
            System.out.print(">> ");
//          TODO: Add Usuários
            selection = in.nextInt();

            switch (selection) {
                case 1 -> materialsViews.menu();
                case 2 -> rentalsViews.menu();
                case 3 -> System.out.println("Encerrando aplicação ...");
                default -> System.out.println("Opção Inválida");
            }
        } while (selection != 3);
    }

    @Override
    protected void newResource() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void getResource() {
        throw new UnsupportedOperationException();

    }
}
