package com.pineframework.core.tutorial.eshop.test.goods;

import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.test.JpaRepositoryTestImpl;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsPhotoEntity;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@DisplayName("E-Shop Goods Repository Test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class GoodRepositoryTest extends AbstractRepositoryTest<Long, GoodsEntity, GoodsRepository> {

    private static final Map<String, GoodsEntity> STORAGE = new HashMap<>();

    private static final JpaRepositoryTestImpl JPA_REPOSITORY_TEST = new JpaRepositoryTestImpl("GoodRepositoryTest");

    private final EntityTransaction transaction = JPA_REPOSITORY_TEST.getEntityManager().getTransaction();

    public GoodRepositoryTest() {
        super(new GoodsRepositoryImpl(JPA_REPOSITORY_TEST), STORAGE);
    }

    public static GoodsPhotoEntity create(byte[] content, GoodsEntity goods) {
        GoodsPhotoEntity entity = new GoodsPhotoEntity();
        entity.setContent(content);
        entity.setGoods(goods);

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
        GoodsEntity table = new GoodsEntity();
        table.setName("Table");
        table.setCode("001");
        table.setPrice(BigDecimal.valueOf(8585, 2));
        addToStorage("table", table);

        GoodsEntity desk = new GoodsEntity();
        desk.setName("Bed");
        desk.setCode("002");
        desk.setPrice(BigDecimal.valueOf(7575, 2));
        addToStorage("bed", desk);

        GoodsEntity chair = new GoodsEntity();
        chair.setName("Chair");
        chair.setCode("003");
        chair.setPrice(BigDecimal.valueOf(6565, 2));
        addToStorage("chair", chair);
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("save new goods")
    @Order(1)
    public void save_GivenNewGoods_WhenSave_ThenReturnId(String name) {
        GoodsEntity entity = getFromStorage(name);
        Optional<Long> identity = save(entity);
        assertTrue(identity.isPresent());
        identity.ifPresent(it -> assertNotNull(it));
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("find by goods id")
    @Order(2)
    public void findById_GivenLongNumberAsParam_WhenFindById_ThenReturnModel(String name) {
        GoodsEntity testEntity = getFromStorage(name);
        Long id = testEntity.getId();
        assertNotNull(id);

        Optional<GoodsEntity> model = findById(id);
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
    @DisplayName("update goods")
    @Order(3)
    public void update_GivenChangedModel_WhenUpdate_ThenApplyChanges(String name) {
        GoodsEntity entity = getFromStorage(name);
        Long id = entity.getId();
        Integer version = entity.getVersion();
        String goodsName = entity.getName();
        assertNotNull(id);
        assertNotNull(version);
        assertNotNull(goodsName);

        entity.setName(goodsName + "_updated");
        update(id, entity);

        assertEquals(goodsName + "_updated", entity.getName());
    }

    @ParameterizedTest(name = "{index} => name=''{0}''")
    @ValueSource(strings = {"table", "bed", "chair"})
    @DisplayName("delete by goods id")
    @Order(4)
    public void delete_GivenLongNumberAsParam_WhenDelete_ThenApplyDelete(String name) {
        GoodsEntity testModel = getFromStorage(name);
        Long id = testModel.getId();
        assertNotNull(id);

        deleteById(id);
        getOperator().clear();

        Optional<GoodsEntity> model = findById(id);
        assertFalse(model.isPresent());
    }

//    @Test
//    @DisplayName("save new goods with photo relations")
//    @Order(5)
//    public void save_GivenNewGoodsWithPhotos_WhenInvokeSaveFunction_ThenReturnId() {
//        GoodsEntity goods = new GoodsEntity();
//        goods.setName("Furniture");
//        goods.setCode("005");
//        goods.setPrice(BigDecimal.valueOf(6565, 2));
//        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "armchair")), goods));
//        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "bed")), goods));
//        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "chair")), goods));
//        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "closet")), goods));
//        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "table")), goods));
//
//        Optional<Long> id = save(goods);
//        assertTrue(id.isPresent());
//        id.ifPresent(it -> assertNotNull(it));
//    }
}
