package ru.vez.springai.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import ru.vez.springai.config.advisors.RequestLogFormatter;
import ru.vez.springai.config.advisors.ResponseLogFormatter;

class ChatAdvisorLogFormatterTest {

  private final RequestLogFormatter requestFormatter = new RequestLogFormatter();
  private final ResponseLogFormatter responseFormatter = new ResponseLogFormatter();

  @Test
  void formatRequestLogsSystemAndUserMessages() {
    ChatClientRequest request =
        ChatClientRequest.builder()
            .prompt(
                new Prompt(
                    List.of(new SystemMessage("You are a fairy"), new UserMessage("Hello"))))
            .build();

    String json = requestFormatter.format(request);

    assertTrue(json.contains("\"role\" : \"system\""));
    assertTrue(json.contains("\"content\" : \"You are a fairy\""));
    assertTrue(json.contains("\"role\" : \"user\""));
    assertTrue(json.contains("\"content\" : \"Hello\""));
    assertFalse(json.contains("modelOptions"));
  }

  @Test
  void formatRequestLogsToolRoundMessages() {
    ChatClientRequest request =
        ChatClientRequest.builder()
            .prompt(
                new Prompt(
                    List.of(
                        new SystemMessage("You are a fairy"),
                        new UserMessage("Find files"),
                        AssistantMessage.builder()
                            .toolCalls(
                                List.of(
                                    new AssistantMessage.ToolCall(
                                        "call-1", "function", "Glob", "{\"pattern\":\"**/*.java\"}")))
                            .build(),
                        ToolResponseMessage.builder()
                            .responses(
                                List.of(
                                    new ToolResponseMessage.ToolResponse(
                                        "call-1", "Glob", "src/Main.java")))
                            .build())))
            .build();

    String json = requestFormatter.format(request);

    assertTrue(json.contains("\"role\" : \"assistant\""));
    assertTrue(json.contains("\"tool_calls\""));
    assertTrue(json.contains("\"name\" : \"Glob\""));
    assertTrue(json.contains("\"arguments\" : \"{\\\"pattern\\\":\\\"**/*.java\\\"}\""));
    assertTrue(json.contains("\"role\" : \"tool\""));
    assertTrue(json.contains("\"tool_results\""));
    assertTrue(json.contains("\"response\" : \"src/Main.java\""));
  }

  @Test
  void formatResponseLogsAssistantContent() {
    ChatResponse response =
        new ChatResponse(List.of(new Generation(new AssistantMessage("Hi there"))));

    String json = responseFormatter.format(response);

    assertTrue(json.contains("\"role\" : \"assistant\""));
    assertTrue(json.contains("\"content\" : \"Hi there\""));
    assertFalse(json.contains("metadata"));
    assertFalse(json.contains("results"));
  }

  @Test
  void formatResponseLogsAssistantToolCalls() {
    ChatResponse response =
        new ChatResponse(
            List.of(
                new Generation(
                    AssistantMessage.builder()
                        .toolCalls(
                            List.of(
                                new AssistantMessage.ToolCall(
                                    "call-1", "function", "Grep", "{\"pattern\":\"ChatService\"}")))
                        .build())));

    String json = responseFormatter.format(response);

    assertTrue(json.contains("\"role\" : \"assistant\""));
    assertTrue(json.contains("\"tool_calls\""));
    assertTrue(json.contains("\"name\" : \"Grep\""));
    assertFalse(json.contains("\"content\""));
  }
}
