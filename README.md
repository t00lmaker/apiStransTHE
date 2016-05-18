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

# Caching e busca de linhas que passam em uma parada

Os dados relativos a paradas e linhas deve mudar muito pouco, portanto o uso de caching local (melhor ainda um banco de dados local) pode benecificar a maior parte das consultas à API. Por isso, a classe CachedInthegraService implementa um serviço que inicialmente faz a carga completa dos dados de Paradas e Linhas, para então permitir acesso a buscas locais.

Além disso, CachedInthegraService implementa o método getLinhas(Parada p), que está definido na API mas não é implementado pela classe IntegraService básica. Esses dados não são fornecidos diretamente pela API Rest, mas podem ser calculados a partir da coleção completa de linhas e paradas em cache e a relação definida entre linhas de uma parada.

Exemplo de uso de CachedIntegraService:

```java
// inicializa a API normalmente 
InthegraService delegate = new InthegraService("key-da-sua-app", "voce@email.com",  "sua-senha");

// inicializa cache (utiliza o serviço padrão como delegate para buscas online durante cache refresh). Usando um dia como tempo de expiração da cache de linhas e paradas.
CachedInthegraService cachedService = new CachedInthegraService(delegate, 1, TimeUnit.DAYS);

// busca paradas com o termo "FREI SERAFIM", e lista todas as linhas que passam na primeira parada encontrada.  
List<Parada> paradasFreiSerafim1 = cachedService.getParadas("AV. FREI SERAFIM 1");
Parada paradaFreiSerafim1 = paradasFreiSerafim1.get(0);
for (Linha l : cachedService.getLinhas(paradaFreiSerafim1)) {
	System.out.println(l.getDenomicao());
}
```
ps.: (a classe Testes.java inclui mais exemplos de utilização).

# Cálculo de rotas (alpha)

RotaService implementa uma versão preliminar de busca de rotas de ônibus com base em CachedInthegraService exclusivamente (faz uso da lista de linhas que passam em uma parada). Existem métodos para retornar rotas a partir de pontos de interesse quaisquer (dois pares de latitude e longitude obtidas de um mapa, por exemplo), ou a partir de paradas de origem e destino. Segue um exemplo de uso (considerando a existência de uma instância de CachedInthegraService):

```java
// inicializa serviço de rotas.
RotaService rotaService = new RotaService(cachedService);

// define pontos de interesse (obtidos do google maps):
PontoDeInteresse a = new PontoDeInteresse(-5.080375, -42.775798);
PontoDeInteresse b = new PontoDeInteresse(-5.089095, -42.810302);

// computa rotas (ordenadas por menor distância).
Set<Rota> rotas = rotaService.getRotas(a, b, 200);
```

Uma rota a partir de pontos de interesse possui 3 trechos (um à pé até a primeira parada, uma linha de ônibus, e outro à pé da parada destino até o ponto de interesse final). Rotas a partir de paradas possuem apenas um trecho (linha de ônibus - não computamos rotas com mais de uma linha ainda).

**Importante**: como sugestão de serviço, pode ser interessante a localização dos veículos da linha definida para uma rota escolhida (o serviço fornece os dados pelo Método getVeiculos(Linha l)), para localizar o próximo a passar na parada de origem (com base em distância e aproximação da direção de movimento (use a criatividade).

**Importante 2**: o sistema de rotas foi testado em algumas situações, mas não é garantido que esteja calculando rotas reversas (no caminho de volta de uma linha - será testado ainda).

**Importante 3**: as distâncias dos trechos são calculadas em linha reta sobre a superfície do planeta (considerando a curvatura), mas não representam a distância a ser trafegada por conta de quadras e ruas (sugerimos a integração com um serviço como a API google maps para incrementar a informação com dados mais precisos, inclusive previsão de tempo de viagem, etc).

ps.: (a classe Testes.java inclui mais exemplos de utilização).

# SerializedInthegraService (file cache)

A classe SerializedInthegraService herda de CachedInthegraService e utiliza serialização Json em arquivos locais da aplicação para não perder os dados em cache entre *runs* da aplicação. O funcionamento é exatamente igual às outras classes Service, apenas evitando o uso de rede para consultas a Paradas e Linhas.

ps.: consultas a Veículos sempre usam a rede, porque os dados dos mesmos são atualizados de 30 em 30 segundos.

# Licença de uso

Essa biblioteca é distribuída sob a licença MIT, o que significa que você pode usar, modificar, distribuir e incluir os fontes ou a biblioteca compilada, inclusive em software de uso comercial, bastando manter uma cópia do arquivo de licença incluído nessa distribuição (license.txt).

Outras bibliotecas para API Inthegra:
Python: https://github.com/rogerio410/InthegraAPI-STRANS-Python/
Ruby: https://github.com/tOOlmaker-equalsp/ruby-apiStransTHE
App Demo Android (use esse wrapper): https://github.com/hcordeiro/ExemploInthegraAPI

Autores: Luan Pontes, Erick Passos, Jonhnny Weslley e Hugo Cordeiro
