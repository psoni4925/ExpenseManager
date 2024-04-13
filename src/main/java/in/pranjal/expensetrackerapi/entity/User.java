package in.pranjal.expensetrackerapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.util.Date;

@Entity
@Table(name = "tbl_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name ="password")
    private String password;

    @Column(name = "email",unique = true)
    private String email;

    private Long age;

    @CreationTimestamp
    @Column(name = "created_date",updatable = false,nullable = false)
    private Date created_date;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updated_date;


}
