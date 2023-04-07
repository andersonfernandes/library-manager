package dev.andersonfernandes.views;

import dev.andersonfernandes.models.Material;

import java.util.List;
import java.util.Scanner;

public class RentalsViews extends BaseViews {
    private final List<Material> rentals;
    public RentalsViews(Scanner in, List<Material> rentals) {
        super(in);
        this.rentals = rentals;
    }

    @Override
    public void menu() {
        int selection;

        do {
            System.out.println("\nSelecione uma das opções a seguir: ");
            System.out.println("1 - Iniciar Locação");
            System.out.println("2 - Consultar Locação");
            System.out.println("3 - Voltar ao menu principal");
            System.out.print(">> ");

            selection = in.nextInt();

            switch (selection) {
                case 1 -> this.newResource();
                case 2 -> this.getResource();
                case 3 -> System.out.println("Cancelando Ação");
                default -> System.out.println("Opção Inválida");
            }
        } while (selection != 3);
    }

    @Override
    protected void newResource() {
    }

    @Override
    protected void getResource() {

    }
}
