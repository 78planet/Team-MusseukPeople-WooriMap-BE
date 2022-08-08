package com.musseukpeople.woorimap.post.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagResponse {

    @Schema(description = "태그 이름")
    private String name;

    @Schema(description = "태그 색상")
    private String color;

    public TagResponse(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static TagResponse from(String name, String color) {
        return new TagResponse(name, color);
    }
}
