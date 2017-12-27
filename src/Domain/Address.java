package Domain;

import javax.persistence.*;

@Embeddable
//@Entity
//@Table(name = "ADDRESS_TBL")
public class Address {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int addressId;
    private String street;
    private String city;


    public Address() {}

    public Address(String street, String city){
        this.street = street;
        this.city = city;
    }

//    public int getAddressId() {
//        return addressId;
//    }
//
//    public void setAddressId(String addressId) {
//        this.addressId = addressId;
//    }
}
