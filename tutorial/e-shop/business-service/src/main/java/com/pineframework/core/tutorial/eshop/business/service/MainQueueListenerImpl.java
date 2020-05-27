package com.pineframework.core.tutorial.eshop.business.service;

import com.pineframework.core.business.service.queue.AbstractMainQueueListener;
import com.pineframework.core.business.service.queue.AbstractQueueService;
import com.pineframework.core.datamodel.model.message.MqStatus;
import com.pineframework.core.tutorial.eshop.model.SampleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MainQueueListenerImpl extends AbstractMainQueueListener<String, SampleModel> {

    @Autowired
    @Qualifier("statusQueueService")
    private AbstractQueueService<String, SampleModel, SampleModel.Builder> statusQueue;

    public void process(SampleModel model, Map<String, Object> metadata) {
        statusQueue.save(new SampleModel.Builder().id(model.getId()).content(MqStatus.ACCEPTED).build());
    }

}
