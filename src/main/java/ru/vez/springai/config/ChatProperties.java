package ru.vez.springai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app.chat")
public record ChatProperties(@DefaultValue("10") int memoryMaxMessages) {}
