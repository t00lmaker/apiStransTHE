# apiStransTHE

Biblioteca Java para simplificar o acesso à API REST do sistema Inthegra (acesso a dados real-time sobre linhas, paradas e ônibus da cidade de Teresina-PI). Para se registrar na API Inthegra, acesse o site oficial em https://inthegra.strans.teresina.pi.gov.br.

# Atenção! (problemas com buscas de paradas e veículos por linha)

Avisamos que a busca de paradas por linha e veiculos por linha não funciona pra todas as linhas por probemas na própria API do Strans. Os problemas já foram reportados e estamos esperando a solução dos mesmos. Não deverá ser necessária uma atualização dessa biblioteca, a não ser que o Strans publique uma nova versão da API (atualmente estamos na versão v1).

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
InthegraService service = new InthegraService("key-da-sua-app", "voce@email.com",  "sua-senha");

// busca todas as linhas do bairro SAO CRISTOVAO.  
List<Linha> linhas = service.getLinhas("SAO CRISTOVAO");

// busca situação atualizada de todos os ônibus da primeira linha recuperada acima.  
List<Veiculo> veiculos = service.getVeiculos(linhas.get(0)); 

// busca todas as paradas da Av FREI SERAFIM
List<Parada> paradas = service.getParadas("FREI SERAFIM");
```

As classes Veiculo, Parada e Linha possuem atributos (e métodos acessores) para os dados fornecidos pela API:

```java
// código, últimas latitude/longitude e a hora da última atualização de um veículo.
Veiculo v = service.getVeiculos().get(0);
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

# Licença de uso

Essa biblioteca é distribuída sob a licença MIT, o que significa que você pode usar, modificar, distribuir e incluir os fontes ou a biblioteca compilada, inclusive em software de uso comercial, bastando manter uma cópia do arquivo de licença incluído nessa distribuição (license.txt).

Autores: Luan Pontes, Erick Passos e Jonhnny Weslley
