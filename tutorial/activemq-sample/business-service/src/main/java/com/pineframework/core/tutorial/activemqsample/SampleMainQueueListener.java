package com.pineframework.core.tutorial.activemqsample;

import com.pineframework.core.business.service.ListenerService;
import com.pineframework.core.business.service.QueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.tutorial.activemqsample.model.SampleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SampleMainQueueListener extends ListenerService<String, SampleModel> {

    @Autowired
    @Qualifier("sampleStatusQueueService")
    private QueueService<String, SampleModel> statusQueue;

    protected void process(SampleModel model, Map<String, Object> metadata) {
        model.setContent(MqStatus.ACCEPTED);
        statusQueue.save(model);
    }
}
