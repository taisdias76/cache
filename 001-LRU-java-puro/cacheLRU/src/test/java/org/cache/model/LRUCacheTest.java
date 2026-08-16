package org.cache.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LRUCacheTest {

  private LRUCache<String, Integer> cache;

  @BeforeEach
  void setUp() {
    cache = new LRUCache<>(3, 10000);
  }

  @Test
  @DisplayName("GET em cache vazio deve retornar Optional.empty")
  void getEmptyCache_returnsEmpty() {
    Optional<Integer> pointer = cache.get("A");
    assertTrue(pointer.isEmpty());
  }

  @Test
  @DisplayName("PUT e GET simples retornando valores inseridos")
  void putAndGet_returnsValue() {
    cache.put("A", 1);

    Optional<Integer> pointer = cache.get("A");
    assertTrue(pointer.isPresent());
    assertEquals(1, pointer.get());
  }

  @Test
  @DisplayName("PUT além da capacidade, deve evictar o menos recente")
  void evictsLeastRecentlyUsed() {
    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    cache.put("D", 4);

    assertTrue(cache.containsKey("D"));
    assertFalse(cache.containsKey("A")); // A deve ser evicted
  }

  @Test
  @DisplayName("GET move para frente, item acessado não é evicted")
  void getMovesToFront() {
    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    cache.get("A"); // A se torna o mais recente
    cache.put("D", 4); // Deve evictar B

    assertFalse(cache.containsKey("B"));
    assertTrue(cache.containsKey("A"));
  }

  @Test
  @DisplayName("Capacidade zerada deve lançar exceção")
  void constructor_InvalidCapacity_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new LRUCache<String, Integer>(0, 10000));
  }
}
