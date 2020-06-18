package com.example.demo.model.merchandise;

import com.example.demo.builders.MerchandiseBuilder;
import com.example.demo.model.exceptions.InvalidStockTypeException;
import com.example.demo.model.exceptions.NegativePriceMerchandiseException;
import com.example.demo.model.exceptions.InsufficientMerchandiseStockException;
import com.example.demo.model.exceptions.NegativeStockMerchandiseException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class MerchandiseTest {

    @Test
    public void aMerchandiseHasAName() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withName("Bread").build();
        assertEquals("Bread", merchandise.name());
    }

    @Test
    public void aMerchandiseHasABrand() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withBrand("Bimbo").build();
        assertEquals("Bimbo", merchandise.brand());
    }

    @Test
    public void aMerchandiseHasACategory(){
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withCategory(MerchandiseCategory.GROCERY).build();
        assertEquals(MerchandiseCategory.GROCERY, merchandise.getCategory());
    }

    @Test
    public void aMerchandiseHasAnImageURL() {
        String imageUrl = "https://static.openfoodfacts.org/images/products/303/371/006/5967/front_fr.266.full.jpg";
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withImage(imageUrl).build();
        assertEquals(merchandise.imageURL(), imageUrl);
    }

    @Test
    public void aMerchandiseCannotHaveNegativeStock() {
        assertThrows(NegativeStockMerchandiseException.class,
                () -> MerchandiseBuilder.aMerchandise().withStock(-4).build());
    }

    @Test
    public void aMerchandiseCannotHaveNegativePrice() {
        assertThrows(NegativePriceMerchandiseException.class,
                () -> MerchandiseBuilder.aMerchandise().withPrice(-4.7).build());
    }

    @Test
    public void ifThereIsAvailableStockThenThisOneCanBeDecremented() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withStock(10).build();
        merchandise.decreaseStock(2);
        assertEquals(8, merchandise.stock());
    }

    @Test
    public void youCannotDecreaseTheStockInABiggerNumberThanItself() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withStock(10).build();
        assertThrows(InsufficientMerchandiseStockException.class,
                () -> merchandise.decreaseStock(30));
    }

    @Test
    public void aMerchandiseCanHaveItsPriceUpdatedIfTheNewPriceIsAPositiveNumber() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withPrice(10.0).build();
        merchandise.updatePrice(22.34);
        assertEquals(22.34, merchandise.price());
    }

    @Test
    public void aMerchandiseCanNotHaveItsPriceUpdatedIfTheNewPriceIsANegativeNumber() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withPrice(10.0).build();
        assertThrows(NegativePriceMerchandiseException.class,
                () -> merchandise.updatePrice(-22.34));
    }

    @Test
    public void aMerchandiseStockCanBeIncreasedIfTheStockToAddIsAPositiveInteger() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withStock(10).build();
        merchandise.addStock(2);
        assertEquals(12, merchandise.stock());
    }

    @Test
    public void aMerchandiseStockCanNotBeIncreasedIfTheStockToAddIsANegativeInteger() {
        Merchandise merchandise = MerchandiseBuilder.aMerchandise().withStock(10).build();
        assertThrows(InvalidStockTypeException.class,
                () -> merchandise.addStock(-2));
    }
}
