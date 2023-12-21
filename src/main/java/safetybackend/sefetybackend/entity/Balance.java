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
@Table(name = "balances")
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "balance_id_gen")
    @SequenceGenerator(name = "balance_id_gen", sequenceName = "balance_id_seq", allocationSize = 1)
    private Long id;
    @OneToOne(cascade = {REFRESH,PERSIST,MERGE,DETACH})
    private User user;
    private Double amount;
    @OneToOne(cascade = {PERSIST,MERGE,DETACH,REFRESH},mappedBy = "balance")
    private Billing billing;
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
