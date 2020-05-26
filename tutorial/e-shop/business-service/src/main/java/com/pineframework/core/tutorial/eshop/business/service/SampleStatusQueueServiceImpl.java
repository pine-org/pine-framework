package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.StatusQueueService;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.springframework.stereotype.Service;

@Service("sampleStatusQueueService")
public class SampleStatusQueueServiceImpl extends StatusQueueService<String, SampleModel, SampleModel.Builder> {

    @Override
    protected SampleModel.Builder getBuilder() {
        return new SampleModel.Builder();
    }
}
