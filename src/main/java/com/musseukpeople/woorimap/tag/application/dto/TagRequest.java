package com.musseukpeople.woorimap.tag.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagRequest {

    @NotBlank
    @Size(max = 10, message = "태그 이름은 최대 10자입니다.")
    private String name;

    @Size(max = 7, message = "태그 컬러 길이는 최대 7자입니다.")
    private String color;

    public TagRequest(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
