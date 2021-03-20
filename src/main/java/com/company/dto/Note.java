package com.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)

public class Note {
    @JsonProperty("note_id")
    private Integer noteId;
    @JsonProperty("note_name")
    private String noteName;
    @JsonProperty("note_description")
    private String noteDescription;
    private Timestamp noteCreationTime;


}
