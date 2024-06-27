---
marp: true
class: invert
backgroundColor: #0d1017
# theme: uncover
color: #e8e9eb
style: |
    h1 {
        font-size: 55px;
    }
    section {
        font-size: 25px;
    }
    .columns2 {
        display: grid;
        grid-template-columns: repeat(2, minmax(0, 1fr));
        gap: 0.25rem;
    }
    .columns3 {
        display: grid;
        grid-template-columns: repeat(3, minmax(0, 1fr));
        gap: 0.25rem;
    }
math: mathjax
---

# <!---fit---> Sheet Book ![w:65 h:65](sheet-book-outline-blue.png)
## Projeto de MAC0350
Diogo Ribeiro <span style="color:grey">n° 12717033</span>
Luiz Fernando <span style="color:grey">n° 13671678</span>

---

# Visão Inicial do Projeto :mag_right:
- <span style="color:#ffcc4d">Criação</span> e <span style="color:#ffcc4d">edição</span> de fichas de personagens;
- <span style="color:#ffcc4d">Gerenciamento</span> de campanhas e seus participantes;
- Interface para a <span style="color:#ffcc4d">interação</span> entre jogadores e mestres;
- <span style="color:#ffcc4d">Simulação</span> de dados de jogo;
- <span style="color:#ffcc4d">Compartilhamento</span> de fichas.

---

# Primeiros Ciclos ![w:70](./exposed.png) ![w:70](./ktor.png) ![w:70](./h2.svg)

Planejamento inicial das classes e funcionalidades do projeto.
Maior foco no <span style="color:#ffcc4d">back-end</span> com <span style="color:#55acee">**Ktor**</span>, principalmente na implementação do banco de dados, utilizando <span style="color:#55acee">**Exposed**</span> e <span style="color:#55acee">**H2**</span>.
Desenvolvimento dos <span style="color:#ffcc4d">testes automatizados</span> iniciais para o back-end.

## Dificuldades :warning:
- Aprendizado de <span style="color:#55acee">**Kotlin**</span>, do <span style="color:#55acee">**Ktor**</span> e <span style="color:#55acee">**Exposed**</span>;
- <span style="color:#ff005e">Documentação  escassa</span> do **Exposed**;
- <span style="color:#ff005e">Falta de guias</span> para o desenvolvimento de testes com banco de dados **H2**.

---
# Ciclos Mais Recentes ![w:70](./react.png) ![w:70](./vite.svg)

Planejamento das páginas e funcionalidades do site.
Foco no <span style="color:#ffcc4d">front-end</span>, implementando mais funcionalidades, trabalhando na integração e melhorando o visual do site (implementado usando <span style="color:#55acee">**Vit**</span> e <span style="color:#55acee">**React**</span>).
Desenvolvimento de mais <span style="color:#ffcc4d">testes automatizados</span> para as novas funcionalidades.

## Dificuldades :warning:
- Aprendizado de tecnologias para o front-end;
- Integração entre <span style="color:#55acee">**Ktor**</span> e o <span style="color:#55acee">**React**</span>;
- Problemas com <span style="color:#ff005e">autenticação</span> do CORS;
- Planejamento das <span style="color:#ff005e">rotas</span> no servidor.

---

# Docker Conteiners ![w:70](./docker.png)
Encapsulamento dos servidores do back e front-end em <span style="color:#55acee">containers</span> <span style="color:#55acee">**Docker**</span>.
Facilita a <span style="color:#ffcc4d">execução</span> e <span style="color:#ffcc4d">distribuição</span> do projeto.

## Dificuldades :warning:
- Configuração dos <span style="color:#55acee">containers</span> para a <span style="color:#ffcc4d">comunicação</span> entre os servidores;
- Execução do <span style="color:#55acee">**Gradle**</span> no container do <span style="color:#55acee">**Ktor**</span>.

---

# Página Inicial (não logado)
![w:1000](./1_home_page_sign_out.png)

---

# Menu de Login
![w:1000](./2_home_page_login.png)

---

# Página de Cadastro
![w:1000](./3_register_page.png)

---

# Página Inicial (logado)

![w:1000](./4_home_page_sign_in.png)

---

# Ficha de D&D 5
![w:900](./5_dnd5_sheet.png)

---

# Página de Criação de Ficha
![w:1000](./6_create_sheet_page.png)

---

# Página de Fichas
![w:1000](./7_my_sheets_page.png)

---

# Página de Criar Campanha
![w:1000](./8_create_campaign_page.png)

---

# Página de Campanhas
![w:1000](./9_my_campaigns_page.png)

---

# Página de Gerenciar Campanha
![w:1000](./10_view_campaign_page.png)

---

# Próximos Passos :rocket:

- Implementar a interface de <span style="color:#ffcc4d">interação</span> entre mestre e jogadores;
- Implementar a <span style="color:#ffcc4d">simulação</span> de dados do jogo;
- Implementar o <span style="color:#ffcc4d">compartilhamento</span> de fichas;
- Melhorar o <span style="color:#ffcc4d">aspecto visual</span> do site.