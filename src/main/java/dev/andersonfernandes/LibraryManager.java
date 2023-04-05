package dev.andersonfernandes;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.BookDao;
import dev.andersonfernandes.library.models.Book;
import dev.andersonfernandes.library.models.BookType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class LibraryManager {
    public static void main(String[] args) throws SQLException {
        System.out.println("Initializing System");

//        Book book = new Book();
//        book.setTitle("abcd");
//        book.setPublisher("ab");
//        book.setYear(2023);
//        book.setQuantity(1);
//        book.setType(BookType.OTHER);
//        System.out.println(book.isValid());
//        book.getErrors().forEach(e -> System.out.println("E: " + e));

        Optional<Book> book = BookDao.getInstance().get(7L);
        System.out.println(book.get().getType());
    }
}
