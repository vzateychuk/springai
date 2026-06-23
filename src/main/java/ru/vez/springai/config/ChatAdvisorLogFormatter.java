package ru.vez.springai.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

final class ChatAdvisorLogFormatter {

  private static final Set<MessageType> LOGGED_MESSAGE_TYPES =
      Set.of(MessageType.SYSTEM, MessageType.USER, MessageType.ASSISTANT);

  private final ObjectMapper prettyMapper =
      new ObjectMapper().findAndRegisterModules().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

  String formatRequest(ChatClientRequest request) {
    List<Map<String, String>> messages =
        request.prompt().getInstructions().stream()
            .filter(message -> LOGGED_MESSAGE_TYPES.contains(message.getMessageType()))
            .map(this::toMessageMap)
            .toList();

    return toPrettyJson(Map.of("messages", messages));
  }

  String formatResponse(ChatResponse response) {
    List<Map<String, String>> messages =
        response.getResults().stream()
            .map(Generation::getOutput)
            .filter(message -> message.getMessageType() == MessageType.ASSISTANT)
            .map(this::toMessageMap)
            .toList();

    return toPrettyJson(Map.of("messages", messages));
  }

  private Map<String, String> toMessageMap(Message message) {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("role", message.getMessageType().name().toLowerCase());
    map.put("content", message.getText());
    return map;
  }

  private String toPrettyJson(Object value) {
    try {
      return prettyMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize chat log payload", e);
    }
  }
}
