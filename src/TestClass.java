import Domain.Category;
import Domain.Product;
import Domain.Supplier;
import Domain.Transactions;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.nio.channels.SeekableByteChannel;
import java.util.*;

public class TestClass {

    private Session session;

    public TestClass(Session session){
        this.session = session;
    }

//    public void test1(){
//
//        Transactions tx = session.beginTransaction();
//        Scanner reader = new Scanner(System.in);
//        System.out.println("Give product name");
//        String inputProductName = reader.next();
//        System.out.println("Give units in stock");
//        int inputUnitsStock = reader.nextInt();
//        Product product = new Product(inputProductName, inputUnitsStock);
//        session.save(product);
//        reader.close();
//
//        Supplier supplier1 = new Supplier("SuperCompany", "Wroclawska","Krakow");
//        session.save(supplier1);
//
//        int productId = product.getProductId();
//        Product foundProduct = session.get(Product.class, productId);
//        foundProduct.setSupplier(supplier1);
//        session.save(foundProduct);
//        tx.commit();
//
//    }

    public void test2(){

        Transaction tx = session.beginTransaction();

        Product product1 = new Product("Book", 4);
        Product product2 = new Product("Car", 6);
        session.save(product1);
        session.save(product2);

        Supplier supplier1 = new Supplier("SuperCompany", "Wroclawska","Krakow");
        supplier1.addProduct(product1);
        supplier1.addProduct(product2);
        session.save(supplier1);
        //product1.setSupplier(supplier1);

        Supplier supplier = session.get(Supplier.class,supplier1.getSupplierId());

        List<Product> products = supplier.getProductSet();
        for (Product p: products) {
            System.out.println(p.getProductName());
        }
        tx.commit();

    }

    public void test3(){
        Transaction tx = session.beginTransaction();

        Category accessoriesCategory = new Category("Accessories");
        Category booksMagazinesCategory = new Category("BooksAndMagazines");
        session.save(accessoriesCategory);
        session.save(booksMagazinesCategory);

        Product product2 = new Product("Newspaper", 100);
        Product product1 = new Product("Book", 10);
        Product product3 = new Product("Magazine", 30);
        Product product4 = new Product("Pen", 1000);
        Product product5 = new Product("Pencil", 100);
        Product product6 = new Product("Rubber", 500);
        session.save(product1);
        session.save(product2);
        session.save(product3);
        session.save(product4);
        session.save(product5);
        session.save(product6);

        accessoriesCategory.addProducts(product4);
        accessoriesCategory.addProducts(product5);
        accessoriesCategory.addProducts(product6);
        booksMagazinesCategory.addProducts(product1);
        booksMagazinesCategory.addProducts(product2);
        booksMagazinesCategory.addProducts(product3);

        Supplier magazinesSuppler = new Supplier("MagazineCompany", "Poznanska", "Krakow");
        Supplier booksSupplier = new Supplier("BooksCompany", "Krolewska", "Krakow");
        Supplier accessoriesSupplier = new Supplier("AccessoriesSupplier", "Kawiory", "Krakow");

        magazinesSuppler.addProduct(product2);
        magazinesSuppler.addProduct(product3);
        booksSupplier.addProduct(product1);
        accessoriesSupplier.addProduct(product4);
        accessoriesSupplier.addProduct(product5);
        accessoriesSupplier.addProduct(product6);

        session.save(magazinesSuppler);
        session.save(booksSupplier);
        session.save(accessoriesSupplier);

        tx.commit();

        System.out.println("Dostawca produktu " + product4.getProductName() + ": " + getSupplierByProduct(product4).getCompanyName());
        System.out.println("Produkty dla dostawcy " + accessoriesSupplier.getCompanyName());
        getProductsBySupplier(accessoriesSupplier).forEach(d -> {System.out.println(d.getProductName());});

    }

    private Supplier getSupplierByProduct(Product p){
        int supplerId = session.get(Product.class, p.getProductId()).getSupplier().getSupplierId();
        return session.get(Supplier.class,supplerId);

    }

    private List<Product> getProductsBySupplier(Supplier supplier){
        return supplier.getProductSet();
    }

    public void test4(){

        Transaction tx = session.beginTransaction();

        Category booksMagazinesCategory = new Category("BooksAndMagazines");
        session.save(booksMagazinesCategory);

        Supplier magazinesSuppler = new Supplier("MagazineCompany", "Poznanska", "Krakow");
        session.save(magazinesSuppler);

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

        session.save(product2);
        session.save(product1);
        session.save(product3);
        session.save(product4);
        session.save(product5);
        session.save(product6);
        session.save(product);

        session.save(t);
        session.save(t2);

        tx.commit();

        getProductsByTransaction(t);
        getProductsByTransaction(t2);

        System.out.println("Transakcje dla produktu " + product.getProductName());
        getTransactionsByProduct(product).forEach(d -> {System.out.println(d.getTransactionNumber());});
        System.out.println("Produkty dla transakcji " + t.getTransactionNumber());
        getProductsByTransaction(t).forEach(d -> {System.out.println(d.getProductName());});
        System.out.println("Produkty dla transakcji " + t2.getTransactionNumber());
        getProductsByTransaction(t2).forEach(d -> {System.out.println(d.getProductName());});

    }

    private Set<Product> getProductsByTransaction(Transactions t) {
        return t.getProductSet();
    }

    private Set<Transactions> getTransactionsByProduct(Product p){
        return p.getTransactionsDone();
    }

}
