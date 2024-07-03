# Sheet Book - Gerenciamento de Mesas de RPG

<p align='center'><img src=https://imgur.com/HAAWoxV.png width="250" height="250"></p>

## Descrição

Sheet Book é uma aplicação web desenvolvida utilizando o framework **Ktor** para organizar um sistema de gerenciamento de fichas para campanhas de RPG de mesa. Tem como objetivo agrupar diferentes fichas de personagens por campanhas para facilitar a adição, consulta e edição delas pelo dono ou membros da campanha. 

### Funcionalidades

- Login e criação de contas;
- Criação e gerenciamento de campanhas;
- Adição de fichas em campanhas já existentes;
- Criação e modificação de fichas;

## Como usar?
Utilizando a página web do projeto será possível realizar login para ter acesso a todas as funcionalidades dele, mantendo salva suas criações na nuvem para poder editá-las de qualquer lugar.

Para executar tanto o servidor quanto o cliente web basta utilizar o seguinte comando na pasta raiz do projeto:

```bash
make
```

Os servidores levam um tempo para iniciar, para verificar seu estado é possível usar os seguintes comandos:

```bash
docker logs sheet-book-app
docker logs sheet-book-cli
```

Para finalizar o servidor (apaga a sessão dos usuários conectados) utilize:

```bash
make down
```

## Testes

Os testes podem ser executados com:

```bash
make tests
```

## Desenvolvimento
[UML base das Classes do projeto](../../wiki/Arquitetura-de-Classes-do-Projeto)
