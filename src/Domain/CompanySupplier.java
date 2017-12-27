package Domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class CompanySupplier extends Company {

    private String bankAccountNumber;

    public CompanySupplier(){}
    public CompanySupplier(String bankAccountNumber){
        this.bankAccountNumber = bankAccountNumber;
    }
    public CompanySupplier(String companyName, String street, String city, String zipCode, String bankAccountNumber){
        super(companyName, street, city, zipCode);
        this.bankAccountNumber = bankAccountNumber;
    }
}
