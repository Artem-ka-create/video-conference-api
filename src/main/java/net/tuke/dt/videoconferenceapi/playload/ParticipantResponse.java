package net.tuke.dt.videoconferenceapi.playload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.tuke.dt.videoconferenceapi.dto.ParticipantDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResponse {

    private List<ParticipantDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
