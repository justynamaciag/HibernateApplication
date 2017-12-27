package Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String productName;
    private int unitsInstock;

    private int categoryId;

    @ManyToOne
    private Supplier supplier;

    @ManyToMany(cascade = CascadeType.PERSIST)
    Set<Transactions> transactionsDone = new HashSet<>();

    public Product(){}

    public Product(String productName, int unitsInstock){
        this.productName = productName;
        this.unitsInstock = unitsInstock;
    }

    public Product(String productName, int unitsInstock, Supplier supplier){
        this.productName = productName;
        this.unitsInstock = unitsInstock;
        this.supplier = supplier;
    }

    public Product(String productName, int unitsInstock, Supplier supplier, int categoryId){
        this.productName = productName;
        this.unitsInstock = unitsInstock;
        this.supplier = supplier;
        this.categoryId = categoryId;
    }

    public Product(String productName, int unitsInstock, int categoryId){
        this.productName = productName;
        this.unitsInstock = unitsInstock;
        this.categoryId = categoryId;
    }

    public int getProductId(){
        return productId;
    }

    public String getProductName(){
        return productName;
    }

    public void setSupplier(Supplier supplier){
        this.supplier = supplier;
        //supplier.addProduct(this);
    }

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }

    public Supplier getSupplier(){
        return this.supplier;
    }

    public Set<Transactions> getTransactionsDone(){
        return transactionsDone;
    }

}
