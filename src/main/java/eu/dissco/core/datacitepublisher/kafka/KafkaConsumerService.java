package eu.dissco.core.datacitepublisher.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.dissco.core.datacitepublisher.domain.DigitalSpecimenEvent;
import eu.dissco.core.datacitepublisher.domain.MediaObjectEvent;
import eu.dissco.core.datacitepublisher.exceptions.DataCiteApiException;
import eu.dissco.core.datacitepublisher.service.DataCitePublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

  @Qualifier("objectMapper")
  private final ObjectMapper mapper;
  private final DataCitePublisherService service;
  private static final String ERROR_MSG = "Unable to specimen event from the handle API";

  @RetryableTopic(
      attempts = "1",
      kafkaTemplate = "retryableTopicKafkaTemplate",
      dltStrategy = DltStrategy.FAIL_ON_ERROR)
  @KafkaListener(topics = "${kafka.consumer.topic.specimen}")
  public void getSpecimenMessages(@Payload String message) throws DataCiteApiException {
    DigitalSpecimenEvent event;
    try {
      event = mapper.readValue(message, DigitalSpecimenEvent.class);
    } catch (JsonProcessingException e) {
      log.error(ERROR_MSG, e);
      log.info("Message: {}", message);
      throw new DataCiteApiException(ERROR_MSG);
    }
    service.handleMessages(event);
  }

  @RetryableTopic(
      attempts = "1",
      kafkaTemplate = "retryableTopicKafkaTemplate",
      dltStrategy = DltStrategy.FAIL_ON_ERROR)
  @KafkaListener(topics = "${kafka.consumer.topic.media}")
  public void getMediaMessages(@Payload String message) throws DataCiteApiException {
    MediaObjectEvent event;
    try {
      event = mapper.readValue(message, MediaObjectEvent.class);
    } catch (JsonProcessingException e) {
      log.error(ERROR_MSG);
      log.info("Message: {}", message);
      throw new DataCiteApiException(ERROR_MSG);
    }
    service.handleMessages(event);
  }

  @DltHandler
  public void dltHandler(String message,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String receivedTopic) {
    log.info("Message {} received in dlt handler at topic {} ", message, receivedTopic);
  }


}
