package org.example.gallery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "UUID")
    private String uuid;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "IMAGE_TAGS",
        joinColumns = @JoinColumn(name = "IMAGE_ID"),
        inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private Set<Tag> tags = new HashSet<>();


}
