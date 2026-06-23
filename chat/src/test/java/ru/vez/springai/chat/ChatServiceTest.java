package ru.vez.springai.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

  @Mock private ChatClient chatClient;

  @Mock private ChatClient.ChatClientRequestSpec requestSpec;

  @Mock private ChatClient.CallResponseSpec callResponseSpec;

  @InjectMocks private ChatService chatService;

  @Test
  void chatReturnsModelResponse() {
    when(chatClient.prompt()).thenReturn(requestSpec);
    when(requestSpec.user(anyString())).thenReturn(requestSpec);
    when(requestSpec.call()).thenReturn(callResponseSpec);
    when(callResponseSpec.content()).thenReturn("AI reply");

    String result = chatService.chat("Hello");

    assertEquals("AI reply", result);
  }
}
