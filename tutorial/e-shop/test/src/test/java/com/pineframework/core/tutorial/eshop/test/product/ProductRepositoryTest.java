package com.pineframework.core.tutorial.eshop.test.product;

import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.test.JpaRepositoryTestImpl;
import com.pineframework.core.tutorial.eshop.business.domain.ProductEntity;
import com.pineframework.core.tutorial.eshop.business.domain.ProductPhotoEntity;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepository;
import com.pineframework.core.tutorial.eshop.business.repository.ProductRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.pineframework.core.helper.FileUtils.readFile;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("E-Shop Product Repository Test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryTest extends AbstractRepositoryTest<Long, ProductEntity, ProductRepository> {

    private static final Map<String, ProductEntity> STORAGE = new HashMap<>();

    private static final JpaRepositoryTestImpl JPA_REPOSITORY_TEST = new JpaRepositoryTestImpl("ProductRepositoryTest");

    private final EntityTransaction transaction = JPA_REPOSITORY_TEST.getEntityManager().getTransaction();

    public ProductRepositoryTest() {
        super(new ProductRepositoryImpl(JPA_REPOSITORY_TEST), STORAGE);
    }

    public static ProductPhotoEntity create(byte[] content, ProductEntity product) {
        ProductPhotoEntity entity = new ProductPhotoEntity();
        entity.setContent(content);
        entity.setProduct(product);

        return entity;
    }

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        transaction.begin();
    }

    @AfterEach
    void tearDown() {
        transaction.commit();
    }

    @Override
    public void init() {
        ProductEntity table = new ProductEntity();
        table.setName("Table");
        table.setCode("001");
        table.setPrice(BigDecimal.valueOf(8585, 2));
        addToStorage("table", table);

        ProductEntity desk = new ProductEntity();
        desk.setName("Bed");
        desk.setCode("002");
        desk.setPrice(BigDecimal.valueOf(7575, 2));
        addToStorage("bed", desk);

        ProductEntity chair = new ProductEntity();
        chair.setName("Chair");
        chair.setCode("003");
        chair.setPrice(BigDecimal.valueOf(6565, 2));
        addToStorage("chair", chair);
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("save new product")
    @Order(1)
    public void save_GivenNewProduct_WhenSave_ThenReturnId(String name) {
        ProductEntity entity = getFromStorage(name);
        Optional<Long> identity = save(entity);
        assertTrue(identity.isPresent());
        identity.ifPresent(it -> assertNotNull(it));
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("find by product id")
    @Order(2)
    public void findById_GivenLongNumberAsParam_WhenFindById_ThenReturnModel(String name) {
        ProductEntity testEntity = getFromStorage(name);
        Long id = testEntity.getId();
        assertNotNull(id);

        Optional<ProductEntity> model = findById(id);
        assertTrue(model.isPresent());
        model.ifPresent(it -> {
            assertNotNull(it);
            assertEquals(testEntity.getName(), it.getName());
            assertEquals(testEntity.getCode(), it.getCode());
            assertEquals(testEntity.getPrice(), it.getPrice());
            updateStorage(name, it);
        });
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("update product")
    @Order(3)
    public void update_GivenChangedModel_WhenUpdate_ThenApplyChanges(String name) {
        ProductEntity entity = getFromStorage(name);
        Long id = entity.getId();
        Integer version = entity.getVersion();
        String productName = entity.getName();
        assertNotNull(id);
        assertNotNull(version);
        assertNotNull(productName);

        entity.setName(productName + "_updated");
        update(id, entity);

        assertEquals(productName + "_updated", entity.getName());
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("delete by product id")
    @Order(4)
    public void delete_GivenLongNumberAsParam_WhenDelete_ThenApplyDelete(String name) {
        ProductEntity testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        deleteById(id);
        getOperator().clear();

        Optional<ProductEntity> model = findById(id);
        assertFalse(model.isPresent());
    }

    @Test
    @DisplayName("save new product with photo relations")
    @Order(5)
    public void save_GivenNewProductWithPhotos_WhenInvokeSaveFunction_ThenReturnId() {
        ProductEntity product = new ProductEntity();
        product.setName("Furniture");
        product.setCode("005");
        product.setPrice(BigDecimal.valueOf(6565, 2));
        product.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "armchair")), product));
        product.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "bed")), product));
        product.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "chair")), product));
        product.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "closet")), product));
        product.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "table")), product));

        Optional<Long> id = save(product);
        assertTrue(id.isPresent());
        id.ifPresent(it -> assertNotNull(it));
    }
}
