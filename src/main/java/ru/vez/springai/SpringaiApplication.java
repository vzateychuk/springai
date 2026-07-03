package ru.vez.springai;

import java.util.Scanner;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vez.springai.chat.ChatService;

@SpringBootApplication
public class SpringaiApplication implements ApplicationRunner {

  private final ChatService chat;

  public SpringaiApplication(ChatService chatService) {
    this.chat = chatService;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringaiApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) {
    Thread t = new Thread(this::replLoop, "chat");
    t.setDaemon(true);
    t.start();
  }

  private void replLoop() {
    System.out.println("Coding assistant is ready. Commands: /quit, /clear");

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.print("cli > ");
        if (!scanner.hasNextLine()) {
          break;
        }

        String msg = scanner.nextLine().strip();
        if (Strings.isBlank(msg)) {
          continue;
        }

        System.out.println(chat.chat("defaultCID", msg));
      }
    }
  }
}
