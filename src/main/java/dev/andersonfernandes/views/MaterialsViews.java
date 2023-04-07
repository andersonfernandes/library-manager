package dev.andersonfernandes.views;

import java.util.Scanner;

public class MaterialsViews extends BaseViews {
    public MaterialsViews(Scanner in) {
        super(in);
    }

    @Override
    public void menu() {
        int selection;

        do {
            System.out.println("\nSelecione uma das opções a seguir: ");
            System.out.println("1 - Cadastrar Material");
            System.out.println("2 - Consultar Material");
            System.out.println("3 - Voltar ao menu principal");
            System.out.print(">> ");

            selection = in.nextInt();

            switch (selection) {
                case 1 -> this.newResource();
                case 2 -> this.getResource();
            }
        } while (selection != 3);
    }

    public void newResource() {
        System.out.println("\nCadastro de Material");
    }

    public void getResource() {
        System.out.println("\nConsulta de Material");
    }
}
