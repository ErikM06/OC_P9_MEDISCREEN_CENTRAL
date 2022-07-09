package com.mediscreen.central.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class PatientNotes {


    private String id;

    @JsonProperty ("patId")
    private long patId;

    @JsonProperty ("recommendations")
    private String content;


    @JsonProperty("Patient")
    private String family;



    public PatientNotes() {
    }

    public PatientNotes(String family, String content) {
        this.content = content;
        this.family=family;

    }

    public PatientNotes(String id, long patId, String content, String family) {
        this.id = id;
        this.patId = patId;
        this.content = content;
        this.family = family;
    }

    public PatientNotes(String id, long patId, String family) {
        this.id = id;
        this.patId = patId;
        this.family = family;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }


}
