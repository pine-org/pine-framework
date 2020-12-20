package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.test.AbstractEntityServiceTest;
import com.pineframework.core.tutorial.eshop.business.service.ProductEntityService;
import com.pineframework.core.tutorial.eshop.model.ProductModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("E-Shop Product Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ProductEntityServiceTest extends AbstractEntityServiceTest<Long, ProductModel, ProductEntityService> {

    public static final Map<String, ProductModel> STORAGE = new HashMap();

    @Autowired
    public ProductEntityServiceTest(ProductEntityService service) {
        super(service, STORAGE);
    }

    @Override
    public void init() {
        addToStorage("table", new ProductModel.Builder("Table", "001")
                .price(BigDecimal.valueOf(8585, 2)).build());
        addToStorage("bed", new ProductModel.Builder("Bed", "002")
                .price(BigDecimal.valueOf(7575, 2)).build());
        addToStorage("chair", new ProductModel.Builder("Chair", "003")
                .price(BigDecimal.valueOf(6565, 2)).build());
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("save new Product")
    @Order(1)
    public void save_GivenNewProduct_WhenSave_ThenReturnId(String name) {
        ProductModel model = getFromStorage(name);
        Optional<Long> identity = save(model);
        assertTrue(identity.isPresent());
        identity.ifPresent(it -> {
            assertNotNull(it);
            updateStorage(name, new ProductModel.Builder(model.getName(), model.getCode())
                    .from(model)
                    .id(it)
                    .build());
        });
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("find by product id")
    @Order(2)
    public void findById_GivenLongNumberAsParam_WhenFindById_ThenReturnModel(String name) {
        ProductModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        Optional<ProductModel> model = findById(id);
        assertTrue(model.isPresent());
        model.ifPresent(it -> {
            assertNotNull(it);
            assertEquals(testModel.getName(), it.getName());
            assertEquals(testModel.getCode(), it.getCode());
            assertEquals(testModel.getPrice(), it.getPrice());
            updateStorage(name, it);
        });
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("update product")
    @Order(3)
    public void update_GivenChangedModel_WhenUpdate_ThenApplyChanges(String name) {
        ProductModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        Integer version = testModel.getVersion();
        assertNotNull(id);
        assertNotNull(version);

        ProductModel changedModel = new ProductModel.Builder(testModel.getName() + "_updated", testModel.getCode())
                .from(testModel).build();

        update(id, changedModel);

        Optional<ProductModel> model = findById(id);
        assertTrue(model.isPresent());
        model.ifPresent(it -> {
            assertNotNull(it);
            assertEquals(testModel.getName() + "_updated", it.getName());
            assertTrue(it.getVersion() == version + 1);
            updateStorage(name, it);
        });
    }


    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("delete by Product id")
    @Order(4)
    public void delete_GivenLongNumberAsParam_WhenDelete_ThenApplyDelete(String name) {
        ProductModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        deleteById(id);

        Optional<ProductModel> model = findById(id);
        assertFalse(model.isPresent());
    }

}