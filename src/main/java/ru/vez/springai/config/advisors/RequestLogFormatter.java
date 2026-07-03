package ru.vez.springai.config.advisors;

import java.util.List;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.messages.Message;

public final class RequestLogFormatter implements ChatAdvisorFormatter<ChatClientRequest> {

  @Override
  public String format(ChatClientRequest req) {
    List<Message> messages =
        req.prompt().getInstructions().stream()
            .filter(m -> MessageLogRegistry.supports(m.getMessageType()))
            .toList();
    return formatMessages(messages);
  }
}
