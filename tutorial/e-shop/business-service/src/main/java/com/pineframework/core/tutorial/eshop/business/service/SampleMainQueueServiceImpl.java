package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.MainQueueService;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.springframework.stereotype.Service;

@Service("sampleMainQueueService")
public class SampleMainQueueServiceImpl extends MainQueueService<String, SampleModel, SampleModel.Builder> {

    @Override
    protected SampleModel.Builder getBuilder() {
        return new SampleModel.Builder();
    }
}
