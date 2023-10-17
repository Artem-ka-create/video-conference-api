package net.tuke.dt.videoconferenceapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = false,nullable = false)
    private String username;
    private Date createdDate;

    @OneToOne(mappedBy = "participant")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "participants_conferences", joinColumns = @JoinColumn(name = "participant_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "conference_id", referencedColumnName = "id"))
    private List<Conference> conferences;

    public List<Conference> addConference(Conference conf){
        conferences.add(conf);
        return conferences;
    }
    public List<Conference> deleteConference(Conference conf){
        conferences.remove(conf);
        return conferences;
    }
}
