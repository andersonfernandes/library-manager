package dev.andersonfernandes;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.views.MainViews;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class LibraryManager {
    public static void main(String[] args) {
        System.out.println("Inicializando Gerenciador de Biblioteca");

        Database.getInstance();
        Scanner in = new Scanner(System.in);
        in.useDelimiter("\n");
        MainViews mainView = new MainViews(in);

        mainView.menu();

        try {
            Database.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
