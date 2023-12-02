package by.home.fullRestApp.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 3, max = 150)
    @Column(name = "title")
    private String title;

    @Size(min = 3, max = 2000)
    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_edit_date")
    private LocalDateTime lastEditDate;


    @Column(name = "inserted_by_id")
    // SpringSecurity добавить
    private int insertedById;

    @Column(name = "updated_by_id")
    // SpringSecurity добавить
    private int updatedById;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "news")
    private List<Comment> comments;

}
