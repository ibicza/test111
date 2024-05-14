package business.hub.CatalogService;

import business.hub.models.Product;
import business.hub.repositories.ProductsRepository;
import business.hub.services.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogServiceApplicationTests {

    private static final int PRODUCT_ID = 1;
    private static final String PRODUCT_NAME = "Test Product";
    private static final double PRODUCT_PRICE = 100.0;
    private static final int PRODUCT_WEIGHT = 12;
    private static final double PRODUCT_VOLUME = 43.0;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductsRepository productsRepositoryMock;

    @Test
    void getAllProductsTest() {
        List<Product> productsMock = new ArrayList<>();
        productsMock.add(new Product());

        when(productsRepositoryMock.findAll()).thenReturn(productsMock);
        List<Product> products = productService.getAllProducts();

        assertThat(products, containsInAnyOrder(productsMock.toArray()));
    }

    @Test
    void getAllProductsTestNegativeScenario() {

        when(productsRepositoryMock.findAll()).thenReturn(new ArrayList<>());
        List<Product> products = productService.getAllProducts();
        assertTrue(products.isEmpty(), "Expected an empty list");
    }

    @Test
    void getProductTest() {

        Product productMock = new Product();
        productMock.setProductId(PRODUCT_ID);

        when(productsRepositoryMock.findById(PRODUCT_ID)).thenReturn(Optional.of(productMock));
        Product res = productService.getProduct(PRODUCT_ID);

        assertEquals(productMock, res);
    }

    @Test
    void getProductTestNegativeScenario() {
        when(productsRepositoryMock.findById(PRODUCT_ID)).thenReturn(Optional.empty());
        Product res = productService.getProduct(PRODUCT_ID);
        assertTrue(res == null, "Expected null, but got a non-null value");
    }

    @Test
    void saveProductTest() {

        Product product = new Product();
        product.setProductName(PRODUCT_NAME);
        product.setProductPrice(PRODUCT_PRICE);
        product.setProductManufacturer("Kek");
        product.setProductDescription("Kekek");
        product.setProductId(PRODUCT_ID);
        product.setProductWeight(PRODUCT_WEIGHT);
        product.setProductVolume(PRODUCT_VOLUME);

        when(productsRepositoryMock.save(any(Product.class))).thenReturn(product);

        productService.saveProduct(product);

        verify(productsRepositoryMock, times(1)).save(product);
    }

    @Test
    void saveProductTestNegativeScenario() {

        when(productsRepositoryMock.save(any(Product.class)))
                .thenThrow(new DataIntegrityViolationException("Test exception"));

        Product product = new Product();
        product.setProductName(PRODUCT_NAME);
        product.setProductPrice(PRODUCT_PRICE);
        product.setProductManufacturer("Kek");
        product.setProductDescription("Kekek");
        product.setProductId(PRODUCT_ID);
        product.setProductWeight(PRODUCT_WEIGHT);
        product.setProductVolume(PRODUCT_VOLUME);

        assertThrows(DataIntegrityViolationException.class, () ->
                        productService.saveProduct(product),
                "Expected DataIntegrityViolationException to be thrown");
    }

    @Test
    void testUpdateProduct() {
        Product updateProduct = new Product();
        updateProduct.setProductName("Update Product");

        Product productMock = new Product();
        productMock.setProductId(PRODUCT_ID);

        when(productsRepositoryMock.findById(PRODUCT_ID)).thenReturn(Optional.of(productMock));
        when(productsRepositoryMock.save(any(Product.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        productService.updateProduct(PRODUCT_ID, updateProduct);

        verify(productsRepositoryMock).save(any(Product.class));

    }


    @Test
    void testDeleteProduct() {

        doNothing().when(productsRepositoryMock).deleteById(PRODUCT_ID);
        productService.deleteProduct(PRODUCT_ID);
        verify(productsRepositoryMock, times(1)).deleteById(PRODUCT_ID);

    }

    @Test
    void testDeleteProductNegativeScenario() {

        doThrow(new DataIntegrityViolationException("Test exception"))
                .when(productsRepositoryMock).deleteById(PRODUCT_ID);
        assertThrows(DataIntegrityViolationException.class, () ->
                        productService.deleteProduct(PRODUCT_ID),
                "Expected DataIntegrityViolationException to be thrown");

    }
}
