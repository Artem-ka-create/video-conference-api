package net.tuke.dt.videoconferenceapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String colorTag;

    private Date createdDate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conference> conferences;

    @ManyToMany(mappedBy = "rooms")
    private List<User> users;

    public List<User> addUser(User User){
        if (users==null){
            users = new ArrayList<>();
        }
        users.add(User);
        return users;
    }
    public List<User> deleteUser(User user){
        users = users.stream().filter( usr ->
                        usr.getId() != user.getId())
                .collect(Collectors.toList());

        return users;
    }

}
