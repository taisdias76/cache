# Cache
Cache é uma camada de armazenamento rápido que guarda o resultado de operações para reutilizá-lo sem precisar refazer. O princípio fundamental é a **localidade temporal:** dados acessados recentemente tem alta chance de serem acessados de novo em breve.

## Tipos de Cache
### In-process (memória local)
Dentro da JVM. Acesso em nanossegundos. Perdido ao reiniciar. Ex.: HashMap, Guava, Cache, Caffeine.   
```Latência mínima``` ```Não compartilhado entre instâncias```

### Distribuído
Servidor externo compartilhado. Ex.: Redis, Memcached. Sobrevive a reinicializações (com persistência).  
```Compartilhado entre pods/instâncias``` ```Latência de rede (~1ms)```

### HTTP/CDN
Cache no browser, proxy ou CDN. Controlado via headers (Cache-Control, ETag). Transparente para o backend.   
```Escala massiva``` ```Difícil de invalidar```

### Políticas de eviction (despejo)
O que acontece quando o cache está cheio   
**LRU (Least Recently Used) -** remove o que foi acessado há mais tempo. A política mais comum.   
**LFU (Least Frequently Used) -** remove o menos acessado em frequência total.    
**TTL (Time To Live) -** expira por tempo. Simples e previsível.    
**FIFO -** primeiro a entrar, primeiro a sair. Simples, mas ignora padrões de acesso.

