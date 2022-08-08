package com.musseukpeople.woorimap.post.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.musseukpeople.woorimap.post.domain.Post;
import com.musseukpeople.woorimap.post.domain.tag.PostTag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "위치")
    private LocationResponse place;

    @Schema(description = "데이트 날짜")
    private LocalDate datingDate;

    @Schema(description = "게시글 생성 날짜")
    private LocalDateTime createdDate;

    @Schema(description = "이미지 리스트")
    private List<String> imageUrls;

    @Schema(description = "태그 리스트")
    private List<TagResponse> tags;

    @Builder
    public PostResponse(String title, String content, LocationResponse place, LocalDate datingDate, LocalDateTime createdDate,
                        List<String> imageUrls, List<TagResponse> tags) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.datingDate = datingDate;
        this.createdDate = createdDate;
        this.imageUrls = imageUrls;
        this.tags = tags;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .title(post.getTitle())
            .content(post.getContent())
            .place(new LocationResponse(post.getLocation().getLatitude(), post.getLocation().getLongitude()))
            .datingDate(post.getDatingDate())
            .createdDate(post.getCreatedDateTime())
            .imageUrls(post.getImageUrls())
            .tags(convertToTagResponses(post.getPostTags().getPostTags()))
            .build();
    }

    private static List<TagResponse> convertToTagResponses(List<PostTag> postTags) {
        return postTags.stream().map(
            postTag -> new TagResponse(postTag.getTag().getName(), postTag.getTag().getColor())
        ).collect(Collectors.toList());
    }
}
