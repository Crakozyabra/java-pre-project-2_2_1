package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public Optional<User> getByCarsModelAndSeries(String model, int series) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM User u LEFT JOIN FETCH u.car WHERE" +
                        "((u.car.model IS NULL) OR (u.car.model = :model)) AND" +
                        " u.car.series = :series", User.class)
                .setParameter("model", model)
                .setParameter("series", series).stream().findFirst();
    }

}
