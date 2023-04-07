package dev.andersonfernandes;

import dev.andersonfernandes.views.MainViews;

import java.util.Scanner;

public class LibraryManager {
    public static void main(String[] args) {
        System.out.println("Inicializando Gerenciador de Biblioteca");

        Scanner in = new Scanner(System.in);
        in.useDelimiter("\n");
        MainViews mainView = new MainViews(in);

        mainView.menu();
    }
}
