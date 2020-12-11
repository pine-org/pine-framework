package com.pineframework.core.tutorial.eshop.test.goods;

import com.pineframework.core.test.AbstractEntityServiceTest;
import com.pineframework.core.test.JpaRepositoryTestImpl;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepositoryImpl;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityService;
import com.pineframework.core.tutorial.eshop.business.service.GoodsEntityServiceImpl;
import com.pineframework.core.tutorial.eshop.business.transformer.GoodsTransformer;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("E-Shop Goods Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodsEntityServiceTest extends AbstractEntityServiceTest<Long, GoodsModel, GoodsEntityService> {

    public static final Map<String, GoodsModel> STORAGE = new HashMap();

    private static final JpaRepositoryTestImpl JPA_REPOSITORY_TEST = new JpaRepositoryTestImpl("GoodsEntityServiceTest");

    private final EntityTransaction transaction = JPA_REPOSITORY_TEST.getEntityManager().getTransaction();

    public GoodsEntityServiceTest() {
        super(new GoodsEntityServiceImpl(new GoodsRepositoryImpl(JPA_REPOSITORY_TEST), new GoodsTransformer()), STORAGE);
    }

    @Override
    public void init() {
        addToStorage("table", new GoodsModel.Builder("Table", "001")
                .price(BigDecimal.valueOf(8585, 2)).build());
        addToStorage("bed", new GoodsModel.Builder("Bed", "002")
                .price(BigDecimal.valueOf(7575, 2)).build());
        addToStorage("chair", new GoodsModel.Builder("Chair", "003")
                .price(BigDecimal.valueOf(6565, 2)).build());
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

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("save new goods")
    @Order(1)
    public void save_GivenNewGoods_WhenSave_ThenReturnId(String name) {
        GoodsModel model = getFromStorage(name);
        Optional<Long> identity = save(model);
        assertTrue(identity.isPresent());
        identity.ifPresent(it -> {
            assertNotNull(it);
            updateStorage(name, new GoodsModel.Builder(model.getName(), model.getCode())
                    .from(model)
                    .id(it)
                    .build());
        });
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("find by goods id")
    @Order(2)
    public void findById_GivenLongNumberAsParam_WhenFindById_ThenReturnModel(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        Optional<GoodsModel> model = findById(id);
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
    @DisplayName("update goods")
    @Order(3)
    public void update_GivenChangedModel_WhenUpdate_ThenApplyChanges(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        Integer version = testModel.getVersion();
        assertNotNull(id);
        assertNotNull(version);

        GoodsModel changedModel = new GoodsModel.Builder(testModel.getName() + "_updated", testModel.getCode())
                .from(testModel).build();

        update(id, changedModel);

        Optional<GoodsModel> model = findById(id);
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
    @DisplayName("delete by goods id")
    @Order(4)
    public void delete_GivenLongNumberAsParam_WhenDelete_ThenApplyDelete(String name) {
        GoodsModel testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        deleteById(id);
        getOperator().getRepository().clear();

        Optional<GoodsModel> model = findById(id);
        assertFalse(model.isPresent());
    }

}