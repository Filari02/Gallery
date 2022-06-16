package org.example.gallery.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "UPLOAD_DATE")
    private Date uploadDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "THUMBNAIL_FILE_PATH")
    private String thumbnailFilePath;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "TAG_IMAGES",
        joinColumns = @JoinColumn(name = "IMAGE_ID"),
        inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private Set<Tag> tags = new HashSet<>();


}
