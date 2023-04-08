package dev.andersonfernandes.views;

import dev.andersonfernandes.dao.MaterialDao;
import dev.andersonfernandes.dao.RentalDao;
import dev.andersonfernandes.dao.UserDao;
import dev.andersonfernandes.models.Material;
import dev.andersonfernandes.models.Rental;
import dev.andersonfernandes.models.RentalStatus;
import dev.andersonfernandes.models.User;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class RentalsViews extends BaseViews {
    public RentalsViews(Scanner in) {
        super(in);
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
        UserDao userDao = new UserDao();
        Rental rental = new Rental();

        System.out.print("Qual o nome do usuário >> ");
        List<User> usersFound = userDao.findBy(
                Map.of(),
                Map.ofEntries(new AbstractMap.SimpleEntry<>("name", in.next()))
        );

        if (usersFound.isEmpty()) {
            System.out.println("Não foram encontrados usuários com o nome buscado!");
        } else {
            System.out.println("Usuários encontrados com o nome buscado:");
            IntStream.range(0, usersFound.size()).forEach(index -> {
                User user = usersFound.get(index);
                System.out.printf("%1$s: %2$s <%3$s>%n",
                        index,
                        user.getName(),
                        user.getEmail()
                );
            });
            System.out.print("Qual o usuário deseja fazer a locação >> ");
            int selectedUser = in.nextInt();

            if (selectedUser < 0 || selectedUser >= usersFound.size()) {
                System.out.println("Usuário inválido");
                return;
            }

            rental.setUser(usersFound.get(selectedUser));

            int action;
            do {
                System.out.println("\nLocação em progresso");
                System.out.printf("Usuário: %1$s%n", rental.getUser().getName());
                System.out.println("Materiais:");
                rental.getMaterials().forEach(material -> System.out.printf("- %1$s%n", material.toString()));

                System.out.println("\nSelecione uma das ações a seguir: ");
                System.out.println("1 - Adicionar Material");
                System.out.println("2 - Salvar Locação");
                System.out.println("3 - Cancelar");
                System.out.print(">> ");

                action = in.nextInt();

                switch (action) {
                    case 1 -> {
                        System.out.print("Qual o título do material? >> ");

                        MaterialDao materialDao = new MaterialDao();
                        List<Material> materialsFound = materialDao.findBy(
                                Map.of(),
                                Map.ofEntries(new AbstractMap.SimpleEntry<>("title", in.next()))
                        );

                        if (materialsFound.isEmpty()) {
                            System.out.println("Não foram encontrados materiais com o título buscado!");
                        } else {
                            System.out.println("Materiais encontrados com o título buscado:");
                            IntStream.range(0, usersFound.size()).forEach(index -> {
                                Material material = materialsFound.get(index);
                                System.out.printf("%1$s %2$s%n",
                                        index,
                                        material.toString()
                                );
                            });
                            System.out.print("Qual o material deseja adicionar >> ");
                            int selectedMaterial = in.nextInt();

                            if (selectedMaterial < 0 || selectedMaterial >= usersFound.size()) {
                                System.out.println("Material inválido");
                                break;
                            }

                            rental.getMaterials().add(materialsFound.get(selectedMaterial));
                            System.out.println("Material adicionado!");
                        }
                    }
                    case 2 -> {
                        rental.setStatus(RentalStatus.ACTIVE);
                        rental.calculateReturnAt();

                        if (rental.isValid()) {
                            RentalDao rentalDao = new RentalDao();
                            rentalDao.create(rental);
                            System.out.println("Locação salva!");
                            return;
                        } else {
                            System.out.println("Não foi possível salvar a locação, verifique os seguintes errors:");
                            rental.getErrors().forEach(error -> {
                                System.out.println("- " + error);
                            });
                        }
                    }
                    case 3 -> System.out.println("Cancelando Ação");
                    default -> System.out.println("Opção Inválida");
                }
            } while (action != 3);
        }
    }

    @Override
    protected void getResource() {
//        TODO: - Get user
//              - Show summary of the Rental(user and added materials)
    }
}
