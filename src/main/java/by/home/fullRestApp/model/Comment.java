package by.home.fullRestApp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "last_edit_date")
    @LastModifiedDate
    private LocalDateTime lastEditDate;

    @Column(name = "inserted_by_id")
    private int insertedById; // SpringSecurity добавить

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY
            // , cascade = CascadeType.ALL
    )
    @JoinColumn(name = "id_news")
    private News news;


}
