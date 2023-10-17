package net.tuke.dt.videoconferenceapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conferences")
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String conferenceName;

    @Column(nullable = false, unique = false)
    private Date createdDate;

    @Column(nullable = true, unique = false)
    private Date completedDate;

//    @Column(nullable = true, unique = false)
    @ManyToMany(mappedBy = "conferences")
    private List<Participant> participants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public List<Participant> addParticipant(Participant participant){
        participants.add(participant);
        return participants;
    }

}
