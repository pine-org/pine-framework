package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.queue.StatusQueueService;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.springframework.stereotype.Service;

@Service("statusQueueService")
public class StatusQueueServiceImpl extends StatusQueueService<String, SampleModel, SampleModel.Builder> {

    @Override
    public SampleModel.Builder getModelBuilder() {
        return new SampleModel.Builder();
    }
}
