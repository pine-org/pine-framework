package com.pineframework.core.tutorial.eshop;

import com.pineframework.core.spring.test.AbstractRestfulWebService;
import com.pineframework.core.tutorial.eshop.model.GoodsModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("E-Shop Goods Rest API Tests")
public class GoodsRestApiTest extends AbstractRestfulWebService<GoodsModel> {

//    @Override
//    protected String getHost() {
//        return "http://localhost:";
//    }

    @Override
    protected String getRelativeUri() {
        return "/api/v1/goods";
    }

    @Override
    public void initData(Map<String, GoodsModel> models) {
        models.put("table", new GoodsModel.Builder("Table", "001").price(BigDecimal.valueOf(8585, 2)).build());
        models.put("desk", new GoodsModel.Builder("Desk", "002").price(BigDecimal.valueOf(7575, 2)).build());
        models.put("chair", new GoodsModel.Builder("Chair", "003").price(BigDecimal.valueOf(6565, 2)).build());
    }

    @ParameterizedTest
    @DisplayName("Save a Goods (Table) via Restful web service")
    @ValueSource(strings = {"table"/*, "desk", "chair"*/})
    @Order(1)
    void save_SendGoodsDataAsJsonObject_ThenSaveInTheGoodsTableAndReturnId(String data) {

        ResponseEntity<String> response = post(getData(data));
        assertNotNull(response);
        assertNotNull(response.getBody());
        System.out.println("response.getBody() = " + response.getBody());
    }

}
