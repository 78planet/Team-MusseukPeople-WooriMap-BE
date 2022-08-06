package com.musseukpeople.woorimap.post.application.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.musseukpeople.woorimap.couple.domain.Couple;
import com.musseukpeople.woorimap.post.domain.Post;
import com.musseukpeople.woorimap.post.domain.PostImage;
import com.musseukpeople.woorimap.post.domain.PostTag;
import com.musseukpeople.woorimap.post.domain.vo.GPSCoordinates;
import com.musseukpeople.woorimap.tag.application.dto.TagRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditPostRequest {

    @Schema(description = "게시글 아이디")
    @NotNull
    private Long id;

    @Schema(description = "제목")
    @NotBlank
    private String title;

    @Schema(description = "내용")
    @NotNull
    private String content;

    @Schema(description = "이미지 저장 경로 리스트")
    @NotNull
    private final List<String> imageUrls = new ArrayList<>();

    @Schema(description = "태그 리스트")
    @NotNull
    private final List<TagRequest> tags = new ArrayList<>();

    @Schema(description = "위도")
    @NotNull
    private BigDecimal latitude;

    @Schema(description = "경도")
    @NotNull
    private BigDecimal longitude;

    @Builder
    public EditPostRequest(Long id, String title, String content, List<String> imageUrls, List<TagRequest> tags, BigDecimal latitude,
                           BigDecimal longitude) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        addTagRequests(tags);
        addImageUrls(imageUrls);
    }

    public Post toPost(Couple coupleId, List<PostTag> postTagIdList) {
        return Post.builder()
            .couple(coupleId)
            .title(title)
            .content(content)
            .gpsCoordinates(new GPSCoordinates(latitude, longitude))
            .postImages(toPostImages())
            .postTags(postTagIdList)
            .build();
    }

    public List<PostImage> toPostImages() {
        return imageUrls.stream().map(PostImage::new).collect(Collectors.toList());
    }

    public void addTagRequests(List<TagRequest> tagRequests) {
        tagRequests.forEach(this::addTagRequest);
    }

    public void addTagRequest(TagRequest tagRequest) {
        this.getTags().add(tagRequest);
    }

    public void addImageUrls(List<String> imageUrls) {
        imageUrls.forEach(this::addImageUrl);
    }

    public void addImageUrl(String imageUrl) {
        this.getImageUrls().add(imageUrl);
    }
}
