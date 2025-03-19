package com.example.backendapp.order.model;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriorityCountDTO {
        private Timestamp insertedTimestamp;
        private PRIORITY priority;
        private Long count;
    }
