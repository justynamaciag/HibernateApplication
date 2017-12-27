package Domain;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@SecondaryTable(name="ADDRESS_TBL")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int supplierId;
    private String companyName;

    //@Embedded
    //private Address address;
    @Column(table = "ADDRESS_TBL")
    private String street;
    @Column(table = "ADDRESS_TBL")
    private String city;

    @OneToMany
    private List<Product> productSet;

    public Supplier(){}
    public Supplier(String companyName, String street, String city){
        this.companyName = companyName;
        this.street = street;
        this.city = city;
        productSet = new ArrayList<>();
    }

//    public Supplier(String companyName, Address address){
//        this.companyName = companyName;
//        this.address = address;
//    }

    public void addProduct(Product product){
        productSet.add(product);
         product.setSupplier(this);
    }

    public int getSupplierId(){
        return supplierId;
    }

    public List<Product> getProductSet(){
        return productSet;
    }
    public String getCompanyName(){
        return this.companyName;
    }


}
