package mhkif.yc.docguardian.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TwoFactorVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;
    @Column(unique = true, nullable = false, length = 10)
    private String code;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime verifiedAt;
}
