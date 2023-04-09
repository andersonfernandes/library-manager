package dev.andersonfernandes.models;

import org.junit.Assert;
import org.junit.Test;

public class MagazineTest {
    @Test
    public void testDefaultConstructor() {
        Magazine magazine = new Magazine();
        Assert.assertEquals(MaterialType.MAGAZINE, magazine.getMaterialType());
    }
}
