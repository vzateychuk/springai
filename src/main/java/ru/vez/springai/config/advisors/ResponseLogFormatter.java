package ru.vez.springai.config.advisors;

import java.util.List;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

public final class ResponseLogFormatter implements ChatAdvisorFormatter<ChatResponse> {

  @Override
  public String format(ChatResponse response) {
    List<Message> messages =
        response.getResults().stream()
            .map(Generation::getOutput)
            .map(Message.class::cast)
            .toList();
    return formatMessages(messages);
  }
}
