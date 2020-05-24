package com.pineframework.core.tutorial.eshop.business;

import com.pineframework.core.business.service.MainQueueService;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.springframework.stereotype.Service;

@Service("sampleMainQueueService")
public class SampleMainQueueServiceImpl extends MainQueueService<String, SampleModel> {

    @Override
    protected Class<SampleModel> getTransientType() {
        return SampleModel.class;
    }
}
