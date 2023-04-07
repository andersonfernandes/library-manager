package dev.andersonfernandes.views;

import dev.andersonfernandes.dao.BookDao;
import dev.andersonfernandes.dao.MagazineDao;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.models.Book;
import dev.andersonfernandes.models.BookType;
import dev.andersonfernandes.models.Magazine;
import dev.andersonfernandes.models.Material;

import java.util.*;

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
                case 3 -> System.out.println("Cancelando Ação");
                default -> System.out.println("Opção Inválida");
            }
        } while (selection != 3);
    }

    @Override
    public void newResource() {
        System.out.println("\nCadastro de Material");

        Material material;
        int materialTypeSelection;
        do {
            material = null;
            System.out.println("\nEscolha o tipo de material:");
            System.out.println("1 - Livro");
            System.out.println("2 - Revista");
            System.out.println("3 - Cancelar");
            System.out.print(">> ");

            materialTypeSelection = in.hasNextInt() ? in.nextInt() : null;

            switch (materialTypeSelection) {
                case 1 -> material = new Book();
                case 2 -> material = new Magazine();
                case 3 -> {
                    break;
                }
                default -> System.out.println("Opção Inválida");
            }

            if (material != null) {
                System.out.print("Título: ");
                material.setTitle(in.next());

                System.out.print("Editora: ");
                material.setPublisher(in.next());

                System.out.print("Ano: ");
                material.setYear(in.hasNextInt() ? in.nextInt() : null);

                if (material instanceof Book) {
                    Integer bookTypeSelection = null;
                    do {
                        System.out.println("Selecione o tipo de livro:");
                        System.out.println("1 - Didático");
                        System.out.println("2 - Paradidático");
                        System.out.print(">> ");

                        bookTypeSelection = in.nextInt();

                        switch (bookTypeSelection) {
                            case 1 -> ((Book) material).setType(BookType.TEXTBOOK);
                            case 2 -> ((Book) material).setType(BookType.OTHER);
                        }
                    } while (bookTypeSelection > 2 || bookTypeSelection < 1);

                    if (((Book) material).getType().equals(BookType.TEXTBOOK)) {
                        System.out.print("Disciplina do livro: ");
                        ((Book) material).setSubject(in.next());
                    } else {
                        System.out.print("Gênero do livro: ");
                        ((Book) material).setGenre(in.next());
                    }
                } else {
                    System.out.print("ISBN: ");
                    ((Magazine) material).setIsbn(in.next());
                    System.out.print("Volume: ");
                    ((Magazine) material).setVolume(in.next());
                    System.out.print("Edição: ");
                    ((Magazine) material).setEdition(in.next());
                }

                System.out.print("Quantidade: ");
                material.setQuantity(in.hasNextInt() ? in.nextInt() : null);

                Dao materialDao = (material instanceof Book) ? new BookDao() : new MagazineDao();

                if (material.isValid()) {
                    materialDao.create(material);
                    System.out.println("Material criado com sucesso!");
                } else {
                    System.out.println("Não foi possível salvar o material, verifique os seguintes errors:");
                    material.getErrors().forEach(error -> {
                        System.out.println("- " + error);
                    });
                }
            }
        } while (materialTypeSelection != 3);
    }

    @Override
    public void getResource() {
        System.out.println("\nConsulta de Material");

        Dao materialDao;
        int materialTypeSelection;
        do {
            materialDao = null;
            System.out.println("\nQual o tipo de material?");
            System.out.println("1 - Livro");
            System.out.println("2 - Revista");
            System.out.println("3 - Cancelar");
            System.out.print(">> ");

            materialTypeSelection = in.hasNextInt() ? in.nextInt() : null;

            switch (materialTypeSelection) {
                case 1 -> materialDao = new BookDao();
                case 2 -> materialDao = new MagazineDao();
                case 3 -> {
                    break;
                }
                default -> System.out.println("Opção Inválida");
            }

            if (materialDao != null) {
                System.out.print("Qual o título do material? >> ");

                List<Material> materialsFound = materialDao.findBy(Map.ofEntries(
                        new AbstractMap.SimpleEntry<>("title", in.next())
                ));

                System.out.println("Materiais encontrados com o título buscado:");
                materialsFound.forEach(material ->
                        System.out.println(String.format("#%1$s: %2$s <%3$s, %4$s>",
                                material.getId(),
                                material.getTitle(),
                                material.getPublisher(),
                                material.getYear()))
                );
                break;
            }
        } while (materialTypeSelection != 3);
    }
}
