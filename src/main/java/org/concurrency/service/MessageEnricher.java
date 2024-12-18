package org.concurrency.service;

import java.util.Map;

public interface MessageEnricher {
  Map<String, String> enrich(Map<String, String> content);
}