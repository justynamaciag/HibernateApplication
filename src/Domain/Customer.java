package Domain;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends Company {
    private double discount;
    private String username;
    private String password;

    @OneToMany
    private Set<Transactions> transactionsSet = new HashSet<>();
    public Customer(){}
    public Customer(int discount){
        this.discount = discount;
    }

    public Customer(String companyName, String street, String city, String zipCode, double discount){
        super(companyName, street, city, zipCode);
        this.discount = discount;
    }
    public Customer(String username, String password){
        this.username = username;
        this.password =  password;
    }

    public void addToTransactionSet(Transactions transaction){
        transactionsSet.add(transaction);
    }

    public Set<Transactions> getTransactionsSet(){
        return this.transactionsSet;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
}
