package org.homework;

import com.mitchellbosecke.pebble.PebbleEngine;
import java.util.Map;
import java.io.StringWriter;

public class TemplateFactory {
  private static final PebbleEngine ENGINE = new PebbleEngine.Builder().build();

  public static String render(String templateName, Map<String, Object> context) {
    try {
      var compiledTemplate = ENGINE.getTemplate("templates/" + templateName);
      var writer = new StringWriter();
      compiledTemplate.evaluate(writer, context);
      return writer.toString();
    } catch (Exception e) {
      throw new RuntimeException("Template rendering failed", e);
    }
  }
}
