package dev.andersonfernandes.views;

import dev.andersonfernandes.models.Material;
import dev.andersonfernandes.models.Rental;

import java.util.List;
import java.util.Scanner;

public class RentalsViews extends BaseViews {
    private List<Rental> rentals;
    public RentalsViews(Scanner in, List<Rental> rentals) {
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
//        TODO: - Get user
//              - Show summary of the Rental(user and added materials)
//              - The show a menu with: Adicionar Material, Remover Material, Salvar Locação, Cancelar Locação
//              - If Adicionar Material:
//                  - Search material by title and wait user prompt to selected material
//                  - Update rental materials
//              - If Remover Material:
//                  - Show selected materials and wait user prompt to selected material to be removed
//                  - Update rental materials
//              - If Salvar Locação:
//                  - Add rental to the rentals list
//              - If Cancelar locação:
//                  - Go back to the previous menu and discard rental
    }

    @Override
    protected void getResource() {
//        TODO: - Get user
//              - Show summary of the Rental(user and added materials)
    }
}
