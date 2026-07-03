package ru.vez.springai.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;

final class SystemPromptAssembler {

  private static final String BASE = "prompts/system-base.md";
  private static final String UNIX = "prompts/system-linux.md";
  private static final String WINDOWS = "prompts/system-windows.md";

  private SystemPromptAssembler() {}

  static String assemble() throws IOException {
    String osName = System.getProperty("os.name");
    String userDir = System.getProperty("user.dir");
    String base = load(BASE).formatted(osName, userDir);
    String osPart = load(isWindows(osName) ? WINDOWS : UNIX);
    return base + "\n\n" + osPart;
  }

  private static boolean isWindows(String osName) {
    return osName != null && osName.toLowerCase().contains("win");
  }

  private static String load(String classpathLocation) throws IOException {
    return new ClassPathResource(classpathLocation).getContentAsString(StandardCharsets.UTF_8).strip();
  }
}
