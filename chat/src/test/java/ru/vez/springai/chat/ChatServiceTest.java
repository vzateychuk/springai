package ru.vez.springai.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

  @Mock private ChatModel chatModel;

  @InjectMocks private ChatService chatService;

  @Test
  void chatReturnsModelResponse() {
    AssistantMessage assistantMessage = new AssistantMessage("AI reply");
    Generation generation = new Generation(assistantMessage);
    ChatResponse chatResponse = new ChatResponse(List.of(generation));

    when(chatModel.call(any(Prompt.class))).thenReturn(chatResponse);

    String result = chatService.chat("Hello");

    assertEquals("AI reply", result);
  }
}
