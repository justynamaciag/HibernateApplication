package Domain;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int companyId;
    private String companyName;
    private String street;
    private String city;
    private String zipCode;

    public Company(){}

    public Company(String companyName, String street, String city, String zipCode){
        this.companyName = companyName;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }


}
