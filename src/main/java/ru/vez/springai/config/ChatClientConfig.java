package ru.vez.springai.config;

import java.io.IOException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ChatProperties.class)
public class ChatClientConfig {

  @Bean
  ChatClient chatClient(ChatClient.Builder builder, ChatProperties props) throws IOException {
    ChatAdvisorLogFormatter logFormatter = new ChatAdvisorLogFormatter();
    return builder
        .defaultSystem(props.loadSystemPrompt())
        .defaultAdvisors(
            SimpleLoggerAdvisor.builder()
                .requestToString(logFormatter::formatRequest)
                .responseToString(logFormatter::formatResponse)
                .build())
        .build();
  }
}
