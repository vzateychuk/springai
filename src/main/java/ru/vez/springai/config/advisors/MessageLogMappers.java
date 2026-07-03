package ru.vez.springai.config.advisors;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.ToolResponseMessage;

final class MessageLogMappers {

  static final int TOOL_RESPONSE_MAX_LENGTH = 500;

  private MessageLogMappers() {}

  static Map<String, Object> simple(Message msg) {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("role", msg.getMessageType().name().toLowerCase());
    map.put("content", msg.getText());
    return map;
  }

  static Map<String, Object> assistant(Message msg) {
    AssistantMessage assistant = (AssistantMessage) msg;
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("role", MessageType.ASSISTANT.getValue());

    String content = assistant.getText();
    if (content != null && !content.isBlank()) {
      map.put("content", content);
    }
    if (assistant.hasToolCalls()) {
      map.put(
          "tool_calls",
          assistant.getToolCalls().stream()
              .map(
                  toolCall ->
                      Map.of(
                          "id", toolCall.id(),
                          "name", toolCall.name(),
                          "arguments", toolCall.arguments()))
              .toList());
    }
    return map;
  }

  static Map<String, Object> toolResponse(Message msg) {
    ToolResponseMessage toolResponse = (ToolResponseMessage) msg;
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("role", MessageType.TOOL.getValue());
    map.put(
        "tool_results",
        toolResponse.getResponses().stream()
            .map(
                result ->
                    Map.of(
                        "id", result.id(),
                        "name", result.name(),
                        "response", truncate(result.responseData())))
            .toList());
    return map;
  }

  private static String truncate(String value) {
    if (value == null || value.length() <= TOOL_RESPONSE_MAX_LENGTH) {
      return value;
    }
    return value.substring(0, TOOL_RESPONSE_MAX_LENGTH)
        + "... [truncated, "
        + value.length()
        + " chars total]";
  }
}
