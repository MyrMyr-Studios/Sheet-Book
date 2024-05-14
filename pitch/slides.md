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

# <!---fit---> Sheet Book :books:
## Projeto de MAC0350
Diogo Ribeiro <span style="color:grey">n° 12717033</span>
Luiz Fernando <span style="color:grey">n° 13671678</span>

---

# Introdução :mag_right:

Com o objetivo de facilitar a organização e a criação de conteúdo para mestres e jogadores, <span style="color:#ee9755">**Sheet Book**</span> oferece serviços de
- <span style="color:#55acee">Criação</span> e <span style="color:#55acee">edição</span> de fichas de personagens;
- <span style="color:#55acee">Gerenciamento</span> de campanhas e seus participantes;
- Interface para a <span style="color:#55acee">interação</span> entre jogadores e mestres;
- <span style="color:#55acee">Simulação</span> de dados de jogo;
- <span style="color:#55acee">Compartilhamento</span> de personagens.

A aplicação web conta com um <span style="color:#55acee">sistema de autenticação</span>, permitindo que os usuários criem e acessem suas fichas e campanhas de qualquer lugar.

---

# Progresso :chart_with_upwards_trend:

<div class=columns2>
    <section>
        <h2>Implementado</h2>
        <ul>
            <li><span style="color:#55acee">Autenticação</span> de usuários;</li>
            <li><span style="color:#55acee">Criação</span> e <span style="color:#55acee">listagem</span> de contas;</li>
            <li><span style="color:#55acee">Criação</span> e <span style="color:#55acee">listagem</span> de fichas de personagens;</li>
            <li><span style="color:#55acee">Criação</span> e <span style="color:#55acee">listagem</span> de campanhas.</li>
        </ul>
    </section>
    <section>
        <h2>Em Desenvolvimento</h2>
        <ul>
            <li><span style="color:#ee9755">Edição</span> de fichas de personagens;</li>
            <li><span style="color:#ee9755">Gerenciamento</span> de campanhas e seus participantes;</li>
            <li><span style="color:#ee9755">Simulação</span> de dados de jogo;</li>
            <li><span style="color:#ee9755">Compartilhamento</span> de personagens.</li>
        </ul>
    </section>
</div>

---

# Teste da Autenticação :key:
Com o html de teste acessado através de um <span style="color:#55acee">servidor http</span> (*apache*).

Quando tentamos acessar a lista de usuários <span style="color:#ee555f">sem fazer login</span>:
<center>

![width:750px](tentativaUserListSemLogin.png)
</center>

Quando tentamos fazer login <span style="color:#ee555f">sem um usuário cadastrado</span>:
<center>

![width:750px](tentativaLoginSemUsuario.png)
</center>


---

# Teste do Banco de Dados :floppy_disk:

Quando criamos um <span style="color:#55acee">novo usuário</span>:
<center>

![width:750px](signUp.png)
</center>

Quando fazemos login com um <span style="color:#55acee">usuário cadastrado</span>:
<center>

![width:750px](login.png)
</center>

---

# Teste do Banco de Dados :floppy_disk:

Quando acessamos a lista de usuários <span style="color:#55acee">após fazer login</span>:
<center>

![width:850px](userList.png)
</center>