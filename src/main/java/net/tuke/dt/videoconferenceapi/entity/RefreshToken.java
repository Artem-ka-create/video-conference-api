package net.tuke.dt.videoconferenceapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@EqualsAndHashCode(of = "uuid")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid= UUID.randomUUID().toString();;

    private String token;

    private ZonedDateTime expiration;

    @OneToOne
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

}
