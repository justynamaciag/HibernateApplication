package Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionNumber;
    private int quantity;

    @ManyToMany(mappedBy = "transactionsDone", cascade = CascadeType.PERSIST)
    private Set<Product> productSet = new HashSet<>();

    public Transactions(){
        quantity = 0;
    }
//    public Transactions(int quantity){
//        this.quantity = quantity;
//    }


    public int getTransactionNumber(){
        return transactionNumber;
    }

    public void addToProdcuSet(Product product){
        productSet.add(product);
        quantity++;
        product.transactionsDone.add(this);
    }

    public int getQuantity(){
        return this.quantity;
    }

    public Set<Product> getProductSet(){
        return this.productSet;
    }

}
