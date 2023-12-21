package safetybackend.sefetybackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "billings")
@Entity
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_id_gen")
    @SequenceGenerator(name = "billing_id_gen", sequenceName = "billing_id_seq", allocationSize = 1)
    private Long id;
    private Double billingAmount;
    @OneToOne(cascade = {REFRESH,MERGE,PERSIST,DETACH})
    private Balance balance;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;




    @PrePersist
    protected void onCreate() {
        modifiedAt = LocalDateTime.now();
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
