package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.test.AbstractRepositoryTest;
import com.pineframework.core.tutorial.eshop.business.domain.GoodsEntity;
import com.pineframework.core.tutorial.eshop.business.repository.GoodsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
import static org.assertj.core.api.Assertions.assertThat;
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

    @ParameterizedTest
    @ValueSource(strings = {"table", "desk", "chair"})
    @DisplayName("Save three goods entity {table, desk, chair}")
    @Order(1)
    public void save_SaveNewGoodsEntity_ReturnId(String name) {
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

        Optional entity = getOperator().findById(id);
        assertFalse(entity.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"table"})
    @DisplayName("Save table goods with photo entity {table}")
    @Order(5)
    public void save_SaveNewGoodsWithPhotoEntity_ReturnId(String name) {
        GoodsEntity entity = getFromStorage(name);
        entity.setPhoto(readFile(format("src/test/resources/img/%s.jpg", name)));
        Long id = save(entity);
        assertNotNull(id);
    }

}