package ru.vez.springai.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

  private final ChatModel chatModel;

  public ChatService(ChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public String chat(String message) {
    log.info("Sending message to LLM: {}", message);
    String resp = chatModel.call(new Prompt(message)).getResult().getOutput().getText();
    log.info("Received response from LLM: {}", resp);
    return resp;
  }
}
