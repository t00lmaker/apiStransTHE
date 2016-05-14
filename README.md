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
List<Linha> linhas = InthegraAPI.getLinhas("saint mary of codipe");

// busca situação atualizada de todos os ônibus da primeira linha recuperada acima.  
List<Veiculo> veiculos = InthegraAPI.getVeiculos(linhas.get(0)); 

// busca todas as paradas do bairro vamos ver o sol...
List<Parada> paradas = InthegraAPI.getParadas("lets see the sun");
```

As classes Veiculo, Parada e Linha possuem atributos (e métodos acessores) para os dados fornecidos pela API:

```java
// código, últimas latitude/longitude e a hora da última atualização de um veículo.
Veiculo v = InthegraAPI.getVeiculos().get(0);
System.out.println(v.getCodigo());
System.out.println(v.getLat());
System.out.println(v.getLong());
System.out.println(v.getHora());
```
Exemplo de resultado:

```
03037
-5.101155
-42.755557
08:50
```
# Fucionalidades ORM-like (lazy loading)

Ao buscar linhas, não é necessário fazer a busca dos veículos ou paradas dessa linha, bastando usar o método acessor correspondente. Exemplo abaixo:

```java
// escolhendo uma linha:
Linha linha = InthegraAPI.getLinhas("ininga").get(0);

// busca por veículos é feita dinamicamente:
List<Veiculo> veiculos = linha.getVeiculos(); 

// o mesmo vale para as paradas:
List<Parada> veiculos = linha.getParadas(); 
```