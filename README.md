# sNamelessAPIJava!

Olá, esta é uma API do NamelessMC para o Java!
Ele ao invés de usar a conexão API própria do NamelessMC, ele se conecta ao MySQL do site para fazer a integração de uma forma mais estável!

# Maven
Colocar como dependência
```xml
	<repositories>
	<!-- GitHub Maven -->
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>


<dependency>
    <groupId>com.github.uJ0hn</groupId>
    <artifactId>sNamelessAPIJava</artifactId>
    <version>0.0.1</version>
</dependency>
```

# Configuração

```java
NMApi api = NMApi.getNameless("http://example.com", "127.0.0.1", "3306", "nameless", "root", "");
```

## Puxar Usuários

```java
NMApi api = NMApi.getNameless("http://example.com", "127.0.0.1", "3306", "nameless", "root", "");
User user = api.getUserByName("NamelessMC"); \\ or
User user = api.getUserById(1);
```

## Puxar Grupos
```java
NMApi api = NMApi.getNameless("http://example.com", "127.0.0.1", "3306", "nameless", "root", "");
Group group = api.getGroupByName("Admin"); \\ or
Group group = api.getGroupById(1);
```