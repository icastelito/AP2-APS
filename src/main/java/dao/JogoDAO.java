package dao;

import entidades.Jogo;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class JogoDAO {

    public void salvar(Jogo jogo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            if (jogo.getId() == null) {
                em.persist(jogo);
            } else {
                em.merge(jogo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void editar(Jogo jogo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Jogo> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        TypedQuery<Jogo> query = em.createQuery("SELECT j FROM Jogo j", Jogo.class);
        List<Jogo> jogos = null;

        try {
            jogos = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return jogos;
    }

    public void excluir(Jogo jogo) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            jogo = em.find(Jogo.class, jogo.getId());
            if (jogo != null) {
                em.remove(jogo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Jogo buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        Jogo jogo = null;

        try {
            jogo = em.find(Jogo.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return jogo;
    }

    public Integer obterMaiorNumeroSorteado() {
        EntityManager em = JPAUtil.getEntityManager();
        Integer maiorNumeroSorteado = null;

        try {
            TypedQuery<Integer> query = em.createQuery("SELECT MAX(j.numeroSorteado) FROM Jogo j", Integer.class);
            maiorNumeroSorteado = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return maiorNumeroSorteado;
    }
}
