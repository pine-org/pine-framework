package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.queue.AbstractMainQueueService;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.springframework.stereotype.Service;

@Service("mainQueueService")
public class MainQueueServiceImpl extends AbstractMainQueueService<String, SampleModel, SampleModel.Builder> {

    @Override
    public SampleModel.Builder getModelBuilder() {
        return new SampleModel.Builder();
    }
}
