package safetybackend.sefetybackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_gen")
    @SequenceGenerator(name = "file_id_gen", sequenceName = "file_id_gen", allocationSize = 1)
    private Long id;
    private String fileUrl;
    @OneToOne(cascade = {REFRESH,PERSIST,MERGE,DETACH})
    private User user;
    @OneToOne(cascade = {REFRESH,PERSIST,MERGE,DETACH})
    private Company company;

}
