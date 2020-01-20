package guru.springframework.webmvcrecipes.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"recipes"})
@ToString(exclude = {"recipes"})
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    //Means that this is mapped by categories on the Recipe class
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
