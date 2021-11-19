package aplicacao;

import dominio.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Programa {

    public static  void main(String[] args) {
//        Pessoa p1 = new Pessoa(null, "Carlos da Silva", "blablabla@email.com");
//        Pessoa p2 = new Pessoa(null, "João da Silva", "blablabla@email.com");
//        Pessoa p3 = new Pessoa(null, "Ana Maria", "blablabla@email.com");

//          Valor encontrado no persistence.xml linha 7
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
        EntityManager em = emf.createEntityManager();

//        Quando o JPA faz uma operção que não é só uma leitura de dados ele precisa de uma transação
//        em.getTransaction().begin();
//        Salvando no DB
//        em.persist(p1);
//        em.persist(p2);
//        em.persist(p3);
//        Corfirmando as alterações
//        em.getTransaction().commit();

//        Buscando pessoa
        Pessoa p = em.find(Pessoa.class, 2);
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        System.out.println("pronto");

        em.close();
        emf.close();
    }

}
