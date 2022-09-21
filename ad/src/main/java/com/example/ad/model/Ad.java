package com.example.ad.model;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ad")
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    private String type;
    private String title;
    private String picturePath;
    private String description;






}
