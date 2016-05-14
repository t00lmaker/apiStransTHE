# apiStransTHE

Biblioteca Java para simplificar o acesso à API REST do sistema Inthegra (acesso a dados real-time sobre linhas, paradas e ônibus da cidade de Teresina-PI).

# Instruções

Use os fontes ou a biblioteca pré-compilada em formato JAR no seu projeto Java. Única dependência é a biblioteca Gson (serialização JSON). Internamente, a biblioteca utiliza requests HTTP da biblioteca básica Java, portanto irá funcionar para projetos standalone, backend (aplicações web) ou Apps Android.

# Classes

- InthegraAPI: encapsula login e todas as buscas em métodos de classe (static). Só é preciso informar credenciais de login uma vez (as próximas requisições atualizam automaticamente o token de acesso - ver exemplos abaixo);
- Parada: representa uma parada (buscas através da classe InthegraAPI);
- Linha: representa uma linha (buscas através da classe InthegraAPI);
- Veiculo: representa um ônibus (buscas através da classe InthegraAPI);

# Exemplo de utilização
```java
// inicializa a API (só precisa fazer uma vez por seção).  
InthegraAPI.init("voce@email.com", "sua-senha", "key-da-sua-app");

// busca todas as linhas do bairro Santa Maria da Codipe.  
List<Linha> linhas = IntegraAPI.getLinhas("saint mary of codipe");

// busca situação atualizada de todos os ônibus da primeira linha recuperada acima.  
List<Veiculo> veiculos = IntegraAPI.getVeiculos(linhas.get(0)); 

// busca todas as paradas do bairro vamos ver o sol...
List<Parada> paradas = IntegraAPI.getParadas("lets see the sun");
```


