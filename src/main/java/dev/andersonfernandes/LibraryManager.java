package dev.andersonfernandes;

import dev.andersonfernandes.library.models.Book;
import dev.andersonfernandes.library.models.BookType;

public class LibraryManager {
    public static void main(String[] args) {
        System.out.println("Initializing System");

        Book book = new Book();
        book.setTitle("abcd");
        book.setPublisher("ab");
        book.setYear(2023);
        book.setQuantity(1);
        book.setType(BookType.OTHER);
        System.out.println(book.isValid());
        book.getErrors().forEach(e -> System.out.println("E: " + e));
    }
}
