import Domain.Customer;
import Domain.Product;
import Domain.Transactions;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import javax.persistence.*;
import javax.servlet.http.HttpServlet;
import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.get;

public class MainJPA extends HttpServlet {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("myDatabaseConfig");
    static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        TestClassJPA testClassJPA = new TestClassJPA(em);
        testClassJPA.test2();
        testClassJPA.test5();
        testClassJPA.test6();

        get("/add", (req, res) -> {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Map<String, Object> model = new HashMap<>();
            List<Product> products;

            TypedQuery<Product> q = em.createQuery("from Product", Product.class);
            products = q.getResultList();
            model.put("products", products);

            System.out.println(req.queryParams("goback"));
            if (req.queryParams("goback") != null) {

                TypedQuery<Customer> query = em.createQuery("from Customer as customer where customer.username=:username", Customer.class);
                query.setParameter("username", req.session().attribute("currentUser"));
                List<Customer> customers = query.getResultList();
                Customer c = customers.get(0);
                c.addToTransactionSet(req.session().attribute("transaction"));
                em.persist(c);

                req.session().removeAttribute("transaction");
                res.redirect("/orders");
            }

            Transactions transactions;
            if (req.session().attribute("transaction") == null) {
                transactions = new Transactions();
                req.session().attribute("transaction", transactions);
            }
            transactions = req.session().attribute("transaction");

            String result = req.queryParams("hdnbt");
            if (result != null) {
                List<Product> p = products.stream().filter(d -> d.getProductName().equals(result)).collect(Collectors.toList());
                transactions.addToProdcuSet(p.get(0));
                em.persist(transactions);
            }
            tx.commit();
            return new ModelAndView(model, "/views/add.jsp");

        }, new VelocityTemplateEngine());

        get("/logout", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            req.session().removeAttribute("currentUser");
            res.redirect("/login");
            return new ModelAndView(model, "/views/logout.jsp");
        }, new VelocityTemplateEngine());

        get("/orders", (req, res) -> {

            Map<String, Object> model = new HashMap<>();
            Set<Transactions> transactions;

            if (req.session().attribute("currentUser") == null) {
                model.put("authenticationFailed", true);
            }
            Map<Transactions, Set<Product>> transactionsProduct = new HashMap<>();

            TypedQuery<Customer> q = em.createQuery("from Customer as customer where customer.username=:username", Customer.class);
            q.setParameter("username", req.session().attribute("currentUser"));
            List<Customer> customers = q.getResultList();

            Customer c = customers.get(0);
            transactions = c.getTransactionsSet();
            transactions.stream().forEach(t -> transactionsProduct.put(t, t.getProductSet()));
            model.put("orders", transactionsProduct);

            return new ModelAndView(model, "/views/orders.jsp");

        }, new VelocityTemplateEngine());

        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String gotUsername = req.queryParams("username");
            String gotPassword = req.queryParams("password");
            if (gotPassword != null && gotUsername != null) {
                if (!checkUserCorrectness(req.queryParams("username"), req.queryParams("password"))) {
                    req.session().removeAttribute("currentUser");
                    model.put("authenticationFailed", true);
                    return new ModelAndView(model, "/views/login.jsp");
                } else {
                    req.session().attribute("currentUser", gotUsername);
                    model.put("authenticationSucceeded", true);
                    res.redirect("/orders");
                }
            }

            if (req.queryParams("registerInput") != null) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                Customer c = new Customer(req.queryParams("companyname"), req.queryParams("street"), req.queryParams("city"), req.queryParams("zipcode"), 0.0);
                c.setUsername(req.queryParams("usernameRegister"));
                c.setPassword(req.queryParams("passwordRegister"));
                em.persist(c);
                tx.commit();
                req.session().attribute("currentUser", req.queryParams("usernameRegister"));
                model.put("registerSuccess", true);
            }

            return new ModelAndView(model, "/views/login.jsp");
        }, new VelocityTemplateEngine());

    }

    private static boolean checkUserCorrectness(String username, String password) {
        TypedQuery<Customer> query = em.createQuery("from Customer as customer where customer.username=:username", Customer.class);
        query.setParameter("username", username);
        List<Customer> customers = query.getResultList();
        Customer c = customers.get(0);
        String foundPassword = c.getPassword();
        return foundPassword.equals(password);
    }


}
