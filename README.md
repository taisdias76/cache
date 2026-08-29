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

## Estrutura do repositório

Este repositório foi organizado em módulos Maven para facilitar a evolução do estudo de cache:

- `001-java-puro/` - implementação em Java puro com política LRU.
- `002-redis/` - módulo inicial para a versão com Redis.
- `.github/workflows/java-ci.yml` - pipeline de validação automática com Maven.
- `pom.xml` - Maven parent/root para gerenciar os módulos do repositório.

## CI com Maven

A pipeline do GitHub Actions é feita no nível do repositório, e detecta qualquer módulo Maven configurado, executando `mvn test` em cada projeto.

- Vantagem: o mesmo fluxo serve para os módulos já existentes e para novos estudos que venham a entrar no repositório.
- Trigger: push nas branches `main`/`master` e em pull requests.
- Estratégia: matrix, com um job por módulo Maven detectado.

Quando um novo projeto com `pom.xml` for adicionado em uma pasta do repositório, ele passa a ser validado automaticamente sem a necessidade de criar um workflow adicional.

