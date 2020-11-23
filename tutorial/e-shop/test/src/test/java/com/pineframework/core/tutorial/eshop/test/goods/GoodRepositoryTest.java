package com.pineframework.core.tutorial.eshop.test.goods;

import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.test.JpaRepositoryTestImpl;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepositoryImpl;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("E-Shop Goods Repository Test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class GoodRepositoryTest extends AbstractRepositoryTest<Long, GoodsEntity, GoodsRepository> {

    private static final Map<String, GoodsEntity> STORAGE = new HashMap<>();
    private static final JpaRepositoryTestImpl JPA_REPOSITORY_TEST = new JpaRepositoryTestImpl("GoodRepositoryTest");

    private final EntityTransaction transaction = JPA_REPOSITORY_TEST.getEntityManager().getTransaction();

    public GoodRepositoryTest() {
        super(new GoodsRepositoryImpl(JPA_REPOSITORY_TEST), STORAGE);
    }

    @Override
    public void init() {
        GoodsEntity table = new GoodsEntity();
        table.setName("Table");
        table.setCode("001");
        table.setPrice(BigDecimal.valueOf(8585, 2));
        addToStorage("table", table);

        GoodsEntity desk = new GoodsEntity();
        desk.setName("Desk");
        desk.setCode("002");
        desk.setPrice(BigDecimal.valueOf(7575, 2));
        addToStorage("desk", desk);

        GoodsEntity chair = new GoodsEntity();
        chair.setName("Chair");
        chair.setCode("003");
        chair.setPrice(BigDecimal.valueOf(6565, 2));
        addToStorage("chair", chair);
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

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Save three goods entity {table, desk, chair}")
    @Order(1)
    void save_GivenGoods_WhenSaveFunction_ThenReturnID(String name) {
        GoodsEntity entity = getFromStorage(name);
        Long id = save(entity);
        assertNotNull(id);
        assertThat(id).isIn(1L, 2L, 3L);
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Find by goods entity id")
    @Order(2)
    public void findById_LongNumberAsGoodsId_ReturnGoodsEntity(String name) {
        GoodsEntity testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        GoodsEntity model = findById(id);

        assertNotNull(model);
        assertEquals(testModel.getName(), model.getName());
        assertEquals(testModel.getCode(), model.getCode());
        assertEquals(testModel.getPrice(), model.getPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Update name of three goods entity {table, desk, chair}")
    @Order(3)
    public void update_WhenDataIsChanged_ThenSaveUpdatedEntity(String name) {
        GoodsEntity testEntity = getFromStorage(name);
        Long id = testEntity.getId();
        Integer version = testEntity.getVersion();
        String nameField = testEntity.getName();
        assertNotNull(id);
        assertNotNull(nameField);
        assertNotNull(version);

        GoodsEntity entity = findById(id);
        entity.setName(nameField + "_updated");
        getOperator().flush();

        GoodsEntity updatedEntity = findById(id);

        assertEquals(nameField + "_updated", updatedEntity.getName());
        assertTrue(updatedEntity.getVersion() == version + 1);
        updateStorage(name, updatedEntity);
    }

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Delete three goods entity {table, desk, chair}")
    @Order(4)
    public void delete_WhenDataIsDelete_ThenDecreaseCountAllAsManyOne(String name) {
        GoodsEntity testEntity = getFromStorage(name);
        Long id = testEntity.getId();
        assertNotNull(id);
        deleteById(id);
        getOperator().clear();
        Optional entity = getOperator().findById(id);
        assertFalse(entity.isPresent());
    }
}
