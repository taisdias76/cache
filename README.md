# Cache
Cache é uma camada de armazenamento rápido que guarda o resultado de operações para reutilizá-lo sem precisar refazer. O princípio fundamental é a **localidade temporal:** dados acessados recentemente tem alta chance de serem acessados de novo em breve.

## Tipos de Cache
### In-process (memória local)
Dentro da JVM. Acesso em nanossegundos. Perdido ao reiniciar. Ex.: HashMap, Guava, Cache, Caffeine.
<span style="color:green">Latência mínima</span>; <span style="color:red">Não compartilhado entre instâncias</span>

### Distribuído
Servidor externo compartilhado. Ex.: Redis, Memcached. Sobrevive a reinicializações (com persistência).
<span style="color:green">Compartilhado entre pods/instâncias</span>; <span style="color:orange">Latência de rede (~1ms)</span>

### HTTP/CDN
Cache no browser, proxy ou CDN. Controlado via headers (Cache-Control, ETag). Transparente para o backend.
<span style="color:green">Escala massiva</span>; <span style="color:red">Difícil de invalidar</span>

### Políticas de eviction (despejo)
O que acontece quando o cache está cheio
**LRU (Least Recently Used) -** remove o que foi acessado há mais tempo. A política mais comum.
**LFU (Least Frequently Used) -** remove o menos acessado em frequência total.
**TTL (Time To Live) -** expira por tempo. Simples e previsível.
**FIFO -** primeiro a entrar, primeiro a sair. Simples, mas ignora padrões de acesso.

