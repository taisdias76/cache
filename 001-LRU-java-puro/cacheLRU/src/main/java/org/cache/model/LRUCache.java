package org.cache.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> {
  private final int capacity;
  private final Map<K, Node<K, V>> cache;
  private final long ttl; // TTL global
  private Node<K, V> head; //mais recente
  private Node<K, V> tail; //menos recente

  public LRUCache(int capacity, long ttl) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacidade deve ser maior que zero: " + capacity);
    }
    if (ttl <= 0) {
      throw new IllegalArgumentException("TTL deve ser maior que zero: " + ttl);
    }
    this.capacity = capacity;
    this.ttl = ttl;
    this.cache = new HashMap<>(capacity);
    this.head = new Node<>(null, null, Long.MAX_VALUE); // Dummy head
    this.tail = new Node<>(null, null, Long.MAX_VALUE); // Dummy tail
    head.next = tail;
    tail.prev = head;
  }

  public Optional<V> get(K key) {
    if (!isValid(key)) {
      return Optional.empty();
    }
    Node<K, V> node = cache.get(key);
    addToFront(node);
    return Optional.of(node.value);
  }

  public void put(K key, V value) {
    long expireAt = System.currentTimeMillis() + ttl;
    if (cache.containsKey(key)) {
      Node<K, V> node = cache.get(key);
      node.value = value; // Update value
      node.expireAt = expireAt; // Update timestamp
      addToFront(node);
    } else {
      if (cache.size() >= capacity) {
        Node<K, V> lruNode = tail.prev;
        cache.remove(lruNode.key);
        removeNode(lruNode);
      }
      Node<K, V> newNode = new Node<>(key, value, expireAt);
      addNode(newNode);
      cache.put(key, newNode);
    }
  }

  private void addToFront(Node<K, V> node) {
    removeNode(node);
    addNode(node);
  }

  private void addNode(Node<K, V> node) {
    head.next.prev = node;
    node.next = head.next;
    node.prev = head;
    head.next = node;
  }

  private void removeNode(Node<K, V> node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }

  public boolean containsKey(K key) {
    return isValid(key);
  }

  private boolean isValid(K key) {
    Node<K, V> node = cache.get(key);
    if (node == null) {
      return false;
    }
    if (node.isExpired()) {
      cache.remove(key);
      removeNode(node);
      return false;
    }
    return true;
  }

  public void printCache() {
    Node<K, V> current = head.next;
    while (current != tail) {
      System.out.println("Key: " + current.key + ", Value: " + current.value);
      current = current.next;
    }
  }
}
