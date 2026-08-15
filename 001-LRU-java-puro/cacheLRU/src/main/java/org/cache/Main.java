package org.cache;

import org.cache.model.LRUCache;

public class Main {
  public static void main(String[] args) {
    LRUCache<String, Integer> cache = new LRUCache<>(3, 800);

    System.out.println("[Teste 1] Get em cache vazio: " + cache.get("A")); // Deve retornar Optional.empty
    System.out.println("\n-----------");

    System.out.println("[Teste 2] Put simples: ");
    cache.put("A", 1);
    cache.put("B", 2);
    cache.put("C", 3);

    cache.printCache();
    System.out.println("\n-----------");

    System.out.println("[Teste 2] Get simples: ");
    cache.get("A");
    cache.printCache();

    System.out.println("\n-----------");
    System.out.println("[Teste 3] Put além da capacidade -> eviction do LRU correto: ");
    cache.put("D", 4);
    System.out.println("Cache após inserir D (deve evictar B): " + cache.containsKey("B")); // Deve retornar false
    cache.printCache();

    System.out.println("\n-----------");
    System.out.println("[Teste 4] Get move para frente -> item acessado não é evicted");
    cache.get("A");
    System.out.println("Cache após acessar A (deve mover A para frente): " + cache.containsKey("A")); // Deve retornar true
    cache.printCache();

    System.out.println("\n-----------");
    System.out.println("[Teste 5] Put com chave existente: ");
    cache.put("D", 2);
    cache.printCache();

    System.out.println("\n-----------");
    System.out.println("[Teste 6] Capacidade 1 -> comportamento correto: ");
    LRUCache<String, Integer> singleCache = new LRUCache<>(1, 800);
    singleCache.put("X", 10);
    System.out.println("Cache com capacidade 1 após inserir X: " + singleCache.containsKey("X")); // Deve retornar true
    singleCache.put("Y", 20);
    System.out.println("Cache com capacidade 1 após inserir Y (deve evictar X): " + singleCache.containsKey("X")); // Deve retornar false

    System.out.println("\n-----------");
    System.out.println("[Teste 7] Capacidade 0 -> deve lançar exceção: ");
    try {
      LRUCache<String, Integer> zeroCache = new LRUCache<>(0, 800);
    } catch (IllegalArgumentException e) {
      System.out.println("Exceção lançada corretamente para capacidade 0: " + e.getMessage());
    }

    System.out.println("\n-----------");
    System.out.println("[Teste 8] TTL - Item expira após o tempo configurado: ");
    LRUCache<String, Integer> ttl = new LRUCache<>(3, 500); // 500ms de TTL
    ttl.put("A", 1);
    System.out.println("Cache após inserir A: " + ttl.containsKey("A")); // Deve retornar true
    try {
      Thread.sleep(600); // Espera 600ms para expirar
    } catch (InterruptedException e) {
      System.out.println("Exceção lançada corretamente para interrupção: " + e.getMessage());
    }
    System.out.println("Cache após esperar 600ms (deve expirar A): " + ttl.containsKey("A")); // Deve retornar false
    System.out.println("get(A) após expirar: " + ttl.get("A")); // Deve retornar Optional.empty

    System.out.println("\n-----------");
    System.out.println("[Teste 9] TTL - Sliding TTL - acesso renova o tempo de vida: ");
    LRUCache<String, Integer> slidingTTL = new LRUCache<>(3, 500);
    slidingTTL.put("A", 1);
    try {
      Thread.sleep(300); // Espera 300ms
    } catch (InterruptedException e) {
      System.out.println("Exceção lançada corretamente para interrupção: " + e.getMessage());
    }
    slidingTTL.put("A", 2); // Renova o tempo de vida
    try {
      Thread.sleep(300); // Espera mais 300ms
    } catch (InterruptedException e) {
      System.out.println("Exceção lançada corretamente para interrupção: " + e.getMessage());
    }
    System.out.println("Cache após acessar A e esperar 600ms (deve manter A): " + slidingTTL.containsKey("A")); // Deve retornar true
    slidingTTL.printCache();
  }
}