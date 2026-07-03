package ru.vez.springai.config;

import java.io.IOException;
import org.springaicommunity.agent.tools.FileSystemTools;
import org.springaicommunity.agent.tools.GlobTool;
import org.springaicommunity.agent.tools.GrepTool;
import org.springaicommunity.agent.tools.ShellTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vez.springai.config.advisors.RequestLogFormatter;
import ru.vez.springai.config.advisors.ResponseLogFormatter;

@Configuration
@EnableConfigurationProperties(ChatProperties.class)
public class ChatClientConfig {

  @Bean
  ChatMemory chatMemory(ChatProperties props) {
    return MessageWindowChatMemory.builder().maxMessages(props.memoryMaxMessages()).build();
  }

  @Bean
  ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) throws IOException {
    RequestLogFormatter formatReq = new RequestLogFormatter();
    ResponseLogFormatter formatResp = new ResponseLogFormatter();
    return builder
        .defaultSystem(SystemPromptAssembler.assemble())
        .defaultTools(
            FileSystemTools.builder().build(),
            GrepTool.builder().build(),
            GlobTool.builder().build(),
            ShellTools.builder().build())
        .defaultAdvisors(
            MessageChatMemoryAdvisor.builder(chatMemory).build(),
            SimpleLoggerAdvisor.builder()
                .requestToString(formatReq::format)
                .responseToString(formatResp::format)
                .build())
        .build();
  }
}
