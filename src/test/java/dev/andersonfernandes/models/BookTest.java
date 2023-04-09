package dev.andersonfernandes.models;

import org.junit.Assert;
import org.junit.Test;

public class BookTest {
    @Test
    public void testDefaultConstructor() {
        Book book = new Book();
        Assert.assertEquals(MaterialType.BOOK, book.getMaterialType());
    }

    @Test
    public void testBasicValidations() {
        Book book = new Book();
        Assert.assertFalse(book.isValid());
        Assert.assertTrue(book.getErrors().contains("Título deve estar presente"));
        Assert.assertTrue(book.getErrors().contains("Editora deve estar presente"));
        Assert.assertTrue(book.getErrors().contains("Ano deve estar presente"));
        Assert.assertTrue(book.getErrors().contains("Quantidade deve estar presente"));
    }

    @Test
    public void testQuatifiedValidations() {
        Book book = new Book();
        book.setTitle("123");
        book.setQuantity(0);

        Assert.assertFalse(book.isValid());
        Assert.assertTrue(book.getErrors().contains("Título deve possuir no mínimo 4 caracteres"));
        Assert.assertTrue(book.getErrors().contains("Quantidade deve ser maior que 0"));
    }
}
