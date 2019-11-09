package com.jwell.classifiedProtection.controller.common;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Api(value = "消息队列接口", tags = "消息队列接口")
@RestController
@RequestMapping(value = "/common/active-mq")
public class MqProducerController {

//    @Autowired
//    private JmsMessagingTemplate jmsTemplate;
//
//    @Autowired
//    private Queue queue;
//
//    @Autowired
//    private Topic topic;
//
//    //发送queue类型消息
//    @GetMapping("/queue")
//    public void sendQueueMsg(String msg) {
//        jmsTemplate.convertAndSend(queue, msg);
//    }
//
//    //发送topic类型消息
//    @GetMapping("/topic")
//    public void sendTopicMsg(String msg) {
//        jmsTemplate.convertAndSend(topic, msg);
//    }
}
