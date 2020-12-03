package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsPhotoEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

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

@SpringBootTest
@DisplayName("E-Shop Goods Repository Test")
@Transactional
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GoodsRepositoryTest extends AbstractRepositoryTest<Long, GoodsEntity, GoodsRepository> {

    public static final Map<String, GoodsEntity> STORAGE = new HashMap();

    @Autowired
    public GoodsRepositoryTest(GoodsRepository repository) {
        super(repository, STORAGE);
    }

    @Override
    public void init() {
        GoodsEntity table = new GoodsEntity();
        table.setName("Table");
        table.setCode("001");
        table.setPrice(BigDecimal.valueOf(8585, 2));
        table.setPhoto(readFile(format("src/test/resources/img/%s.jpg", "table")));
        addToStorage("table", table);

        GoodsEntity bed = new GoodsEntity();
        bed.setName("Bed");
        bed.setCode("002");
        bed.setPrice(BigDecimal.valueOf(7575, 2));
        bed.setPhoto(readFile(format("src/test/resources/img/%s.jpg", "bed")));
        addToStorage("bed", bed);

        GoodsEntity chair = new GoodsEntity();
        chair.setName("Chair");
        chair.setCode("003");
        chair.setPrice(BigDecimal.valueOf(6565, 2));
        chair.setPhoto(readFile(format("src/test/resources/img/%s.jpg", "chair")));
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

        Optional<GoodsEntity> model = findById(id);
        assertFalse(model.isPresent());
    }

    public static GoodsPhotoEntity create(byte[] content, GoodsEntity goods) {
        GoodsPhotoEntity entity = new GoodsPhotoEntity();
        entity.setContent(content);
        entity.setGoods(goods);

        return entity;
    }

    @Test
    @DisplayName("save new goods with photo relations")
    @Order(5)
    public void save_GivenNewGoodsWithPhotos_WhenInvokeSaveFunction_ThenReturnId() {
        GoodsEntity goods = new GoodsEntity();
        goods.setName("Furniture");
        goods.setCode("005");
        goods.setPrice(BigDecimal.valueOf(6565, 2));
        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "armchair")), goods));
        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "bed")), goods));
        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "chair")), goods));
        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "closet")), goods));
        goods.getPhotos().add(create(readFile(format("src/test/resources/img/%s.jpg", "table")), goods));

        Optional<Long> id = save(goods);
        assertTrue(id.isPresent());
        id.ifPresent(it -> assertNotNull(it));
    }
}