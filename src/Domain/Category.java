package Domain;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryId;
    private String categoryName;

    @OneToMany
    private List<Product> productList;

    public Category(){}

    public Category(String categoryName){
        productList = new ArrayList<>();
        this.categoryName = categoryName;
    }

    public void addProducts(Product p){
        productList.add(p);
        p.setCategoryId(categoryId);
    }

    public int getCategoryId() {
        return this.categoryId;
    }
}
