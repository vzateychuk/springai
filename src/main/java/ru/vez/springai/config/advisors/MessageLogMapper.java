package ru.vez.springai.config.advisors;

import java.util.Map;
import org.springframework.ai.chat.messages.Message;

@FunctionalInterface
interface MessageLogMapper {

  Map<String, Object> map(Message msg);
}
