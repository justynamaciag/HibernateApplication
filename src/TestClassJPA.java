import Domain.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;

public class TestClassJPA {

    EntityManager entityManager;

    public TestClassJPA(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void test1(){

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category accessoriesCategory = new Category("Accessories");
        Category booksMagazinesCategory = new Category("BooksAndMagazines");
        entityManager.persist(accessoriesCategory);
        entityManager.persist(booksMagazinesCategory);

        Product product2 = new Product("Newspaper", 100);
        Product product1 = new Product("Book", 10);
        Product product3 = new Product("Magazine", 30);
        Product product4 = new Product("Pen", 1000);
        Product product5 = new Product("Pencil", 100);
        Product product6 = new Product("Rubber", 500);

        accessoriesCategory.addProducts(product4);
        accessoriesCategory.addProducts(product5);
        accessoriesCategory.addProducts(product6);
        booksMagazinesCategory.addProducts(product1);
        booksMagazinesCategory.addProducts(product2);
        booksMagazinesCategory.addProducts(product3);

        Supplier magazinesSuppler = new Supplier("MagazineCompany", "Poznanska", "Krakow");
        Supplier booksSupplier = new Supplier("BooksCompany", "Krolewska", "Krakow");
        Supplier accessoriesSupplier = new Supplier("AccessoriesSupplier", "Kawiory", "Krakow");
        entityManager.persist(magazinesSuppler);
        entityManager.persist(booksSupplier);
        entityManager.persist(accessoriesSupplier);

        magazinesSuppler.addProduct(product2);
        magazinesSuppler.addProduct(product3);
        booksSupplier.addProduct(product1);
        accessoriesSupplier.addProduct(product4);
        accessoriesSupplier.addProduct(product5);
        accessoriesSupplier.addProduct(product6);


        entityManager.persist(product1);
        entityManager.persist(product2);
        entityManager.persist(product3);
        entityManager.persist(product4);
        entityManager.persist(product5);
        entityManager.persist(product6);

        entityTransaction.commit();

        System.out.println("Dostawca produktu " + product2.getProductName() + ": " + getSupplierByProduct(product2).getCompanyName());
        System.out.println("Dostawca produktu " + product3.getProductName() + ": " + getSupplierByProduct(product3).getCompanyName());

        System.out.println("Dostawca produktu " + product1.getProductName() + ": " + getSupplierByProduct(product1).getCompanyName());
        System.out.println("Dostawca produktu " + product5.getProductName() + ": " + getSupplierByProduct(product5).getCompanyName());
        System.out.println("Dostawca produktu " + product6.getProductName() + ": " + getSupplierByProduct(product6).getCompanyName());

        System.out.println("Produkty dla dostawcy " + magazinesSuppler.getCompanyName());
        getProductsBySupplier(magazinesSuppler).forEach(d -> {System.out.println(d.getProductName());});

        System.out.println("Produkty dla dostawcy " + accessoriesSupplier.getCompanyName());
        getProductsBySupplier(accessoriesSupplier).forEach(d -> {System.out.println(d.getProductName());});

        System.out.println("Produkty dla dostawcy " + booksSupplier.getCompanyName());
        getProductsBySupplier(booksSupplier).forEach(d -> {System.out.println(d.getProductName());});



    }

    private Supplier getSupplierByProduct(Product p){
        int supplerId = entityManager.find(Product.class, p.getProductId()).getSupplier().getSupplierId();
        return entityManager.find(Supplier.class,supplerId);

    }

    private List<Product> getProductsBySupplier(Supplier supplier){
        return supplier.getProductSet();
    }

    public void test2(){
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        Category booksMagazinesCategory = new Category("BooksAndMagazines");
        entityManager.persist(booksMagazinesCategory);

        Supplier magazinesSuppler = new Supplier("MagazineCompany", "Poznanska", "Krakow");

        Product product = new Product("Notebook ", 10, magazinesSuppler, booksMagazinesCategory.getCategoryId());
        Product product1 = new Product("Calendar", 20, magazinesSuppler, booksMagazinesCategory.getCategoryId());
        Product product2 = new Product("Book", 10, magazinesSuppler, booksMagazinesCategory.getCategoryId());
        Product product3 = new Product("Magazine", 30, magazinesSuppler, booksMagazinesCategory.getCategoryId());
        Product product4 = new Product("Pen", 1000, magazinesSuppler, booksMagazinesCategory.getCategoryId());
        Product product5 = new Product("Pencil", 100, magazinesSuppler, booksMagazinesCategory.getCategoryId());
        Product product6 = new Product("Rubber", 500, magazinesSuppler, booksMagazinesCategory.getCategoryId());

        Transactions t = new Transactions();
        t.addToProdcuSet(product);
        t.addToProdcuSet(product1);

        Transactions t2 = new Transactions();
        t2.addToProdcuSet(product2);
        t2.addToProdcuSet(product3);
        t2.addToProdcuSet(product4);
        t2.addToProdcuSet(product5);
        t2.addToProdcuSet(product6);

        entityManager.persist(magazinesSuppler);

//        entityManager.persist(product2);
//        entityManager.persist(product1);
//        entityManager.persist(product3);
//        entityManager.persist(product4);
//        entityManager.persist(product5);
//        entityManager.persist(product6);
//        entityManager.persist(product);

        entityManager.persist(t);
        entityManager.persist(t2);

        tx.commit();

//        getProductsByTransaction(t);
//        getProductsByTransaction(t2);
//
//        System.out.println("Transakcje dla produktu " + product.getProductName());
//        getTransactionsByProduct(product).forEach(d -> {System.out.println(d.getTransactionNumber());});
//        System.out.println("Produkty dla transakcji " + t.getTransactionNumber());
//        getProductsByTransaction(t).forEach(d -> {System.out.println(d.getProductName());});
//        System.out.println("Produkty dla transakcji " + t2.getTransactionNumber());
//        getProductsByTransaction(t2).forEach(d -> {System.out.println(d.getProductName());});


    }

    private Set<Product> getProductsByTransaction(Transactions t) {
        return t.getProductSet();
    }

    private Set<Transactions> getTransactionsByProduct(Product p){
        return p.getTransactionsDone();
    }

    public void test4() {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Supplier s = new Supplier("UltraCompany", "Wroclawska", "Warsaw");
        entityManager.persist(s);
        tx.commit();
    }

//    public void test3(){
//        EntityTransaction tx = entityManager.getTransaction();
//        tx.begin();
//        Supplier s = new Supplier("SuperCompany", new Address("Mazowiecka", "Karkow"));
//        entityManager.persist(s);
//        tx.commit();
//    }
    public void test5(){
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        CompanySupplier companySupplier = new CompanySupplier("BooksSupplier", "Wroclawska", "Krakow", "12-123", "123456789");
        CompanySupplier companySupplier2 = new CompanySupplier("MagazinesSupplier", "Slowackiego", "Krakow", "12-123", "123456789");
        CompanySupplier companySupplier3 = new CompanySupplier("AccessoriesSupplier", "Poznanska", "Krakow", "12-123", "123456789");
        entityManager.persist(companySupplier);
        entityManager.persist(companySupplier2);
        entityManager.persist(companySupplier3);

        Customer customer = new Customer("Customer1", "Mickiewicza", "Krakow", "12-123", 0.5);
        Customer customer2 = new Customer("Customer2", "Kujawska", "Krakow", "12-123", 0.25);
        Customer customer3 = new Customer("Customer3", "Wielicka", "Krakow", "12-123", 0.75);

        Company company1 = new Company("SuperCompany", "Kazimierza Wielkiego", "Krakow", "12-123");
        Company company2 = new Company("UltaSuperCompany", "Czarnowiejska", "Krakow", "12-123");

        entityManager.persist(customer);
        entityManager.persist(customer2);
        entityManager.persist(customer3);
        entityManager.persist(company1);
        entityManager.persist(company2);

        tx.commit();
    }

    public void test6(){

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        Customer customer = new Customer("Customer1", "Mickiewicza", "Krakow", "12-123", 0.5);
        customer.setUsername("cus1");
        customer.setPassword("pass1");
        entityManager.persist(customer);
        Transactions t = new Transactions();
        t.addToProdcuSet(entityManager.find(Product.class, 4));
        t.addToProdcuSet(entityManager.find(Product.class, 5));
        t.addToProdcuSet(entityManager.find(Product.class, 11));
        customer.addToTransactionSet(t);
        Transactions t2 = new Transactions();
        t2.addToProdcuSet(entityManager.find(Product.class, 7));
        t2.addToProdcuSet(entityManager.find(Product.class, 8));
        t2.addToProdcuSet(entityManager.find(Product.class, 10));
        customer.addToTransactionSet(t2);
        entityManager.persist(t);
        entityManager.persist(t2);
        tx.commit();

    }

}
