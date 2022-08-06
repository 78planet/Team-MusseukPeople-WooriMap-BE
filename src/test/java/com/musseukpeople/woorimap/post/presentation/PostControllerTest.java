package com.musseukpeople.woorimap.post.presentation;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import com.musseukpeople.woorimap.post.application.request.EditPostRequest;
import com.musseukpeople.woorimap.tag.application.dto.TagRequest;
import com.musseukpeople.woorimap.member.application.dto.request.SignupRequest;
import com.musseukpeople.woorimap.post.application.request.CreatePostRequest;
import com.musseukpeople.woorimap.util.AcceptanceTest;

public class PostControllerTest extends AcceptanceTest {

    private String coupleAccessToken;

    @BeforeEach
    void login() throws Exception {
        String email = "test@gmail.com";
        String password = "!Test1234";
        String nickName = "test";
        SignupRequest user = new SignupRequest(email, password, nickName);
        String accessToken = 회원가입_토큰(user);
        coupleAccessToken = 커플_맺기_토큰(accessToken);
    }

    @DisplayName("post 생성 완료")
    @Transactional
    @Test
    void create_post_success() throws Exception {
        // given
        List<String> sampleImagePaths = List.of("http://wooriemap.aws.com/1.jpg", "http://wooriemap.aws.com/2.jpg");
        List<TagRequest> sampleTags = List.of(
            new TagRequest("seoul", "F000000"),
            new TagRequest("cafe", "F000000")
        );
        CreatePostRequest createPostRequest = getCreatePostRequest(sampleImagePaths, sampleTags);

        // when
        MockHttpServletResponse response = createPostApi(createPostRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("중복된 태그로 post 생성 완료")
    @Transactional
    @Test
    void create_post_duplicated_tag_success() throws Exception {
        // given
        List<String> sampleImagePaths = List.of("http://wooriemap.aws.com/1.jpg", "http://wooriemap.aws.com/2.jpg");
        List<TagRequest> sampleTags = List.of(
            new TagRequest("seoul", "F000000"),
            new TagRequest("jeju", "F000000"),
            new TagRequest("cafe", "F000000")
        );
        CreatePostRequest createPostRequest = getCreatePostRequest(sampleImagePaths, sampleTags);

        // when
        MockHttpServletResponse response = createPostApi(createPostRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("post 수정 완료")
    @Transactional
    @Test
    void modify_post_success() throws Exception {
        // given
        List<String> sampleImageUrls = List.of("http://wooriemap.aws.com/1.jpg", "http://wooriemap.aws.com/2.jpg");
        List<TagRequest> sampleTags = List.of(
            new TagRequest("seoul", "F000000"),
            new TagRequest("cafe", "F000000")
        );
        CreatePostRequest createPostRequest = getCreatePostRequest(sampleImageUrls, sampleTags);
        createPostApi(createPostRequest);

        List<String> imageUrlsToModify = List.of("http://wooriemap.aws.com/2.jpg");
        List<TagRequest> sampleTagsToModify = List.of(
            new TagRequest("seoul", "#F000000"),
            new TagRequest("jeju", "F000000"),
            new TagRequest("good", "F000000")
        );
        EditPostRequest editPostRequest = getEditPostRequest(1L, imageUrlsToModify, sampleTagsToModify);

        // when
        MockHttpServletResponse response = modifyPostApi(editPostRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    private MockHttpServletResponse createPostApi(CreatePostRequest createPostRequest) throws Exception {
        return mockMvc.perform(post("/api/couples/posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, coupleAccessToken)
                .content(objectMapper.writeValueAsString(createPostRequest)))
            .andDo(print())
            .andReturn().getResponse();
    }

    private MockHttpServletResponse modifyPostApi(EditPostRequest editPostRequest) throws Exception {
        return mockMvc.perform(put("/api/couples/posts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, coupleAccessToken)
                .content(objectMapper.writeValueAsString(editPostRequest)))
            .andDo(print())
            .andReturn().getResponse();
    }

    private CreatePostRequest getCreatePostRequest(List<String> sampleImageUrls, List<TagRequest> sampleTags) {
        return CreatePostRequest.builder()
            .title("첫 이야기")
            .content("<h1>첫 이야기.... </h1>")
            .imageUrls(sampleImageUrls)
            .tags(sampleTags)
            .latitude(new BigDecimal("12.12312321"))
            .longitude(new BigDecimal("122.3123121"))
            .build();
    }

    private EditPostRequest getEditPostRequest(Long postId, List<String> sampleImageUrls, List<TagRequest> sampleTags) {
        return EditPostRequest.builder()
            .id(postId)
            .title("첫 이야기-2")
            .content("<h1>22첫 이야기.... </h1>")
            .imageUrls(sampleImageUrls)
            .tags(sampleTags)
            .latitude(new BigDecimal("33.12312321"))
            .longitude(new BigDecimal("444.3123121"))
            .build();
    }
}
