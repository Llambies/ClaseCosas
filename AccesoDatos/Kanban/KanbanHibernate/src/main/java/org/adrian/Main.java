package org.adrian;

import org.adrian.controladores.App;
import org.adrian.implementacion.KanbanRepositoryImplHibernate;
import org.adrian.repository.KanbanRepository;
import org.adrian.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        
        KanbanRepository kanbanRepository = new KanbanRepositoryImplHibernate(sessionFactory);
        App app = new App(kanbanRepository);

        try {
            app.iniciar();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
