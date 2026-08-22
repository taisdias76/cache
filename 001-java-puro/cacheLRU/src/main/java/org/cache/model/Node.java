package org.cache.model;

class Node<K, V> {
  K key;
  V value;
  Node<K, V> prev;
  Node<K, V> next;
  long expireAt;

  public Node(K key, V value, long expireAt) {
    this.key = key;
    this.value = value;
    this.expireAt = expireAt;
  }

  boolean isExpired() {
    return System.currentTimeMillis() > expireAt;
  }
}
