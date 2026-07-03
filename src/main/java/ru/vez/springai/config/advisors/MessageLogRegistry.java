package ru.vez.springai.config.advisors;

import java.util.EnumMap;
import java.util.Map;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

final class MessageLogRegistry {

  private static final Map<MessageType, MessageLogMapper> MAPPERS = new EnumMap<>(MessageType.class);

  static {
    MAPPERS.put(MessageType.SYSTEM, MessageLogMappers::simple);
    MAPPERS.put(MessageType.USER, MessageLogMappers::simple);
    MAPPERS.put(MessageType.ASSISTANT, MessageLogMappers::assistant);
    MAPPERS.put(MessageType.TOOL, MessageLogMappers::toolResponse);
  }

  private MessageLogRegistry() {}

  static boolean supports(MessageType type) {
    return MAPPERS.containsKey(type);
  }

  static Map<String, Object> map(Message message) {
    MessageLogMapper mapper = MAPPERS.get(message.getMessageType());
    if (mapper == null) {
      throw new IllegalArgumentException("Unsupported message type: " + message.getMessageType());
    }
    return mapper.map(message);
  }
}
