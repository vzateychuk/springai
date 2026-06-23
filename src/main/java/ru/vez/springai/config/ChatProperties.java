package ru.vez.springai.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "app.chat")
public record ChatProperties(Resource systemPromptLocation) {

  public String loadSystemPrompt() throws IOException {
    return systemPromptLocation.getContentAsString(StandardCharsets.UTF_8).strip();
  }
}
