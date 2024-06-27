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
# Planejamento

<div class=columns2>
    <section>
        <h2>Plano inicial</h2>
        <ul>
            <li>Criação e edição de fichas de personagens</li>
            <li>Gerenciamento de campanhas e seus participantes</li>
            <li>Interface de interação entre mestre e jogadores</li>
            <li>Simulação de dados do jogo</li>
        </ul>
    </section>
    <section>
        <h2>O que foi feito</h2>
        <ul>
            <li>Criação e edição de fichas de personagens</li>
            <li>Gerenciamento de campanhas e seus participantes</li>
        </ul>
    </section>
</div>

---

# Desenvolvimento (emoji aqui)
## Primeiros Ciclos 

Maior foco no back end, principalmente na implementação do banco de dados.
- Implementado usando Exposed e H2

---
## Ciclos Mais Recentes
Foco no front end, implementando mais funcionalidades e deixando o site mais bonito
- Implementado usando Vit e React (colocar imagem de vit e react)

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