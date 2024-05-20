package bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

import entidades.Jogo;
import dao.JogoDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class JogoBean {
    
    private Jogo jogo;
    private List<Jogo> jogos;
    private Jogo jogoSelecionado;
    private JogoDAO jogoDAO;

    @PostConstruct
    public void init() {
        jogo = new Jogo();
        jogoDAO = new JogoDAO();
        listar();
    }

    public void salvar() {
        try {
            jogo.setDataCadastro(new Date());
            jogo.setNumeroSorteado(new Random().nextInt(10) + 1);
            jogoDAO.salvar(jogo);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Jogo salvo com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao salvar o jogo!"));
            e.printStackTrace();
        }
    }

    public void atualizar() {
        try {
            jogoDAO.editar(jogo);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Jogo atualizado com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao atualizar o jogo!"));
            e.printStackTrace();
        }
    }

    public void listar() {
        try {
            jogos = jogoDAO.listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao listar os jogos!"));
            e.printStackTrace();
        }
    }

    public void editar(Jogo jogo) {
        this.jogo = jogo;
    }

    public void excluir(Jogo jogo) {
        try {
            jogoDAO.excluir(jogo);
            listar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Jogo excluído com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao excluir o jogo!"));
            e.printStackTrace();
        }
    }

    public String maiorValor(Jogo jogo) {
        int max = Math.max(jogo.getV1(), Math.max(jogo.getV2(), Math.max(jogo.getV3(), Math.max(jogo.getV4(), jogo.getV5()))));
        return "Maior valor entre v1 a v5: " + max;
    }

    public String verificarNumeroSorteado(Jogo jogo) {
        int num = jogo.getNumeroSorteado();
        if (num == jogo.getV1() || num == jogo.getV2() || num == jogo.getV3() || num == jogo.getV4() || num == jogo.getV5()) {
            return "O número sorteado está entre os valores.";
        } else {
            return "O número sorteado não está entre os valores.";
        }
    }

    // Getters e Setters

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public Jogo getJogoSelecionado() {
        return jogoSelecionado;
    }

    public void setJogoSelecionado(Jogo jogoSelecionado) {
        this.jogoSelecionado = jogoSelecionado;
    }
}
