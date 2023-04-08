package dev.andersonfernandes.views;

import dev.andersonfernandes.dao.UserDao;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.models.*;

import java.util.Scanner;

public class UsersViews extends BaseViews {
    public UsersViews(Scanner in) {
        super(in);
    }

    @Override
    protected void menu() {
        int selection;

        do {
            System.out.println("\nSelecione uma das opções a seguir: ");
            System.out.println("1 - Criar Usuário");
            System.out.println("2 - Consultar Usuário");
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
        System.out.println("\nCadastro de Usuário");

        User user = new User();
        int userTypeSelection;
        do {
            System.out.println("\nEscolha o tipo de usuário:");
            System.out.println("1 - Estudante");
            System.out.println("2 - Professor");
            System.out.println("3 - Cancelar");
            System.out.print(">> ");

            userTypeSelection = in.hasNextInt() ? in.nextInt() : -1;

            switch (userTypeSelection) {
                case 1 -> user.setType(UserType.STUDENT);
                case 2 -> user.setType(UserType.TEACHER);
                case 3 -> System.out.println();
                default -> System.out.println("Opção Inválida");
            }

            if (userTypeSelection != 3 && user.getType() != null) {
                System.out.print("Nome: ");
                user.setName(in.next());

                System.out.print("Endereço: ");
                user.setAddress(in.next());

                System.out.print("Email: ");
                user.setEmail(in.next());

                if (user.getType().equals(UserType.STUDENT)) {
                    System.out.print("Matrícula: ");
                    user.setRegistration(in.next());
                }

                if (user.getType().equals(UserType.TEACHER)) {
                    System.out.print("Disciplinas lecionadas (separadas por vírgula): ");
                    user.setSubjects(in.next().split(","));
                }

                Dao<User> userDao = new UserDao();

                if (user.isValid()) {
                    userDao.create(user);
                    System.out.println("Usuário criado com sucesso!");
                } else {
                    System.out.println("Não foi possível salvar o usuário, verifique os seguintes errors:");
                    user.getErrors().forEach(error -> {
                        System.out.println("- " + error);
                    });
                }
            }
        } while (userTypeSelection != 3);
    }

    @Override
    protected void getResource() {

    }
}
