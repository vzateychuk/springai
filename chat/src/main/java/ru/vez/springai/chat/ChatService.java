package ru.vez.springai.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

  private final ChatClient chatClient;

  public ChatService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  public String chat(String message) {
    log.info("Sending message to LLM: {}", message);
    String resp = chatClient.prompt().user(message).call().content();
    log.info("Received response from LLM: {}", resp);
    return resp;
  }
}
