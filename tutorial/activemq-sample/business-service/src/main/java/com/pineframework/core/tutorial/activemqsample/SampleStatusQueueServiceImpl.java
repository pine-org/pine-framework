package com.pineframework.core.tutorial.activemqsample;

import com.pineframework.core.business.service.StatusQueueService;
import com.pineframework.core.tutorial.activemqsample.model.SampleModel;
import org.springframework.stereotype.Service;

@Service("sampleStatusQueueService")
public class SampleStatusQueueServiceImpl extends StatusQueueService<String, SampleModel> {

    @Override
    protected Class<SampleModel> getTransientType() {
        return SampleModel.class;
    }
}
