package com.musseukpeople.woorimap.post.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.musseukpeople.woorimap.common.model.BaseEntity;
import com.musseukpeople.woorimap.couple.domain.Couple;
import com.musseukpeople.woorimap.post.domain.vo.GPSCoordinates;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    private String title;

    @Lob
    private String content;

    @Embedded
    private GPSCoordinates gpsCoordinates;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    @Builder
    public Post(Long id, Couple couple, String title, String content,
                GPSCoordinates gpsCoordinates,
                List<PostImage> postImages, List<PostTag> postTags) {
        this.id = id;
        this.couple = couple;
        this.title = title;
        this.content = content;
        this.gpsCoordinates = gpsCoordinates;
        addPostImages(postImages);
        addPostTags(postTags);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeGpsCoordinates(BigDecimal latitude, BigDecimal longitude) {
        this.gpsCoordinates = new GPSCoordinates(latitude, longitude);
    }

    public void changePostImages(List<String> imageUrls) {
        this.postImages.clear();
        addPostImages(toPostImages(imageUrls));
    }

    public void changePostTags(List<PostTag> postTags) {
        this.postTags.clear();
        addPostTags(postTags);
    }

    public void addPostImages(List<PostImage> postImages) {
        postImages.forEach(this::addPostImage);
    }

    public void addPostImage(PostImage postImage) {
        postImage.changePost(this);
        this.getPostImages().add(postImage);
    }

    public void addPostTags(List<PostTag> postTags) {
        postTags.forEach(this::addPostTag);
    }

    public void addPostTag(PostTag postTags) {
        postTags.setPost(this);
        this.getPostTags().add(postTags);
    }

    public List<PostImage> toPostImages(List<String> imageUrls) {
        return imageUrls.stream().map(PostImage::new).collect(Collectors.toList());
    }
}
