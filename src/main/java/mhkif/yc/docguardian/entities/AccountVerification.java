package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class AccountVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(unique = true, nullable = false)
    private String token;
    @Column(columnDefinition = "boolean default false")
    private boolean isVerified;
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime verifiedAt;

    public AccountVerification(){
        this.createdAt = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.isVerified = false;
    }
}
