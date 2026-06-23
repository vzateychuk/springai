package ru.vez.springai.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vez.springai.api.dto.ChatRequest;
import ru.vez.springai.api.dto.ChatResponse;
import ru.vez.springai.chat.ChatService;

@RestController
@RequestMapping("/v1/api/chat")
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping
  public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
    return new ChatResponse(chatService.chat(request.message()));
  }
}
