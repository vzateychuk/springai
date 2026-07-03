package ru.vez.springai.chat;

import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

  private final ChatClient chatClient;

  public ChatService(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  public String chat(String cId, String msg) {
    return chatClient
        .prompt()
        .user(msg)
        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, cId))
        .toolContext(Map.of("workingDir", System.getProperty("user.dir")))
        .call()
        .content();
  }
}
