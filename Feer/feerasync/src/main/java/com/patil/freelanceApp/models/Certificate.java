package com.patil.freelanceApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Certificate {
    private int userId;
    private int eventId;
    private int position;
}
