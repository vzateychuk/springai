package ru.vez.springai.config.advisors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.messages.Message;

public interface ChatAdvisorFormatter<T> {

  ObjectMapper PRETTY_PRINTER =
      new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

  String format(T value);

  default String formatMessages(List<Message> messages) {
    List<Map<String, Object>> mapped =
        messages.stream()
            .filter(message -> MessageLogRegistry.supports(message.getMessageType()))
            .map(MessageLogRegistry::map)
            .toList();
    try {
      return PRETTY_PRINTER
          .writerWithDefaultPrettyPrinter()
          .writeValueAsString(Map.of("messages", mapped));
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize chat log payload", e);
    }
  }
}
