package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.entity.enums.AttacheFileStatueType;
import com.scw.springtodomanagement.domain.entity.enums.ImageExtensionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachedFile extends BaseTimeCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_id")
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageExtensionType imageExtensionType;

    @Column(nullable = false)
    private Integer fileSize;

    @Lob
    @Column(nullable = false, columnDefinition = "blob")
    private byte[] attachedContent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    AttacheFileStatueType attacheFileStatueType;

    @Builder
    public AttachedFile(
            String filename, ImageExtensionType imageExtensionType,
            Integer fileSize, byte[] attachedContent, Post post,
            AttacheFileStatueType attacheFileStatueType
    ) {
        this.filename = filename;
        this.imageExtensionType = imageExtensionType;
        this.fileSize = fileSize;
        this.attachedContent = attachedContent;
        this.post = post;
        this.attacheFileStatueType = attacheFileStatueType;
    }

    public void updateAttachedFile(AttachedFile updateAttachedFileData) {
        this.filename = updateAttachedFileData.getFilename();
        this.imageExtensionType = updateAttachedFileData.getImageExtensionType();
        this.fileSize = updateAttachedFileData.fileSize;
        this.attachedContent = updateAttachedFileData.attachedContent;
    }

    public void deleteAttachedFile(AttacheFileStatueType attacheFileStatueType) {
        this.attacheFileStatueType = attacheFileStatueType;
    }
}
