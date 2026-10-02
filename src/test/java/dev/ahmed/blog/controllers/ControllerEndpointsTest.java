package dev.ahmed.blog.controllers;

import dev.ahmed.blog.config.SecurityConfig;
import dev.ahmed.blog.domain.dtos.CategoryDto;
import dev.ahmed.blog.domain.dtos.TagDto;
import dev.ahmed.blog.domain.entities.Category;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.Tag;
import dev.ahmed.blog.domain.entities.User;
import dev.ahmed.blog.mappers.CategoryMapper;
import dev.ahmed.blog.mappers.PostMapper;
import dev.ahmed.blog.mappers.TagMapper;
import dev.ahmed.blog.repositories.UserRepository;
import dev.ahmed.blog.services.AuthenticationService;
import dev.ahmed.blog.services.CategoryServices;
import dev.ahmed.blog.services.PostService;
import dev.ahmed.blog.services.TagService;
import dev.ahmed.blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class ControllerEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationService;

    @MockitoBean
    private CategoryServices categoryServices;

    @MockitoBean
    private CategoryMapper categoryMapper;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private PostMapper postMapper;

    @MockitoBean
    private TagService tagService;

    @MockitoBean
    private TagMapper tagMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void loginReturnsTokenForValidCredentials() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("user@test.com")
                .password("password")
                .roles("USER")
                .build();
        when(authenticationService.authenticate("user@test.com", "password"))
                .thenReturn(userDetails);
        when(authenticationService.generateToken(userDetails)).thenReturn("jwt-token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("""
                                {
                                  "email": "user@test.com",
                                  "password": "password"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.expiresIn").value(86400));

        verify(authenticationService).authenticate("user@test.com", "password");
        verify(authenticationService).generateToken(userDetails);
    }

    @Test
    void listCategoriesIsPublic() throws Exception {
        UUID categoryId = UUID.randomUUID();
        Category category = Category.builder().id(categoryId).name("Java").build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(categoryId)
                .name("Java")
                .postCount(0)
                .build();
        when(categoryServices.listCategories()).thenReturn(List.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(categoryId.toString()))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[0].postCount").value(0));

        verify(categoryServices).listCategories();
    }

    @Test
    void createCategoryRequiresAuthentication() throws Exception {
        mockMvc.perform(post("/api/v1/categories")
                        .contentType("application/json")
                        .content("{\"name\":\"Java\"}"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(categoryServices, categoryMapper);
    }

    @Test
    void createCategoryReturnsCreatedCategory() throws Exception {
        UUID categoryId = UUID.randomUUID();
        Category category = Category.builder().id(categoryId).name("Java").build();
        CategoryDto categoryDto = CategoryDto.builder()
                .id(categoryId)
                .name("Java")
                .postCount(0)
                .build();
        when(categoryMapper.toEntity(any())).thenReturn(category);
        when(categoryServices.createCategory(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        mockMvc.perform(post("/api/v1/categories")
                        .with(user("user@test.com"))
                        .contentType("application/json")
                        .content("{\"name\":\"Java\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(categoryId.toString()))
                .andExpect(jsonPath("$.name").value("Java"));

        verify(categoryServices).createCategory(category);
    }

    @Test
    void deleteCategoryRequiresAuthentication() throws Exception {
        UUID categoryId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId))
                .andExpect(status().isForbidden());

        verifyNoInteractions(categoryServices);
    }

    @Test
    void deleteCategoryReturnsNoContent() throws Exception {
        UUID categoryId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/categories/{id}", categoryId)
                        .with(user("user@test.com")))
                .andExpect(status().isNoContent());

        verify(categoryServices).deleteCategory(categoryId);
    }

    @Test
    void listPostsIsPublicAndPassesFiltersToService() throws Exception {
        UUID categoryId = UUID.randomUUID();
        UUID tagId = UUID.randomUUID();
        Post post = Post.builder().build();
        when(postService.getAllPosts(categoryId, tagId)).thenReturn(List.of(post));

        var postDto = dev.ahmed.blog.domain.dtos.PostDto.builder()
                .title("A post")
                .build();
        when(postMapper.toDto(post)).thenReturn(postDto);

        mockMvc.perform(get("/api/v1/posts")
                        .param("categoryId", categoryId.toString())
                        .param("tagId", tagId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A post"));

        verify(postService).getAllPosts(categoryId, tagId);
    }

    @Test
    void draftPostsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/posts/drafts"))
                .andExpect(status().isForbidden());

        verifyNoInteractions(postService, userService);
    }

    @Test
    void draftPostsUseAuthenticatedUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User userEntity = User.builder().id(userId).email("user@test.com").name("Test User").build();
        Post draft = Post.builder().title("Draft").build();
        var draftDto = dev.ahmed.blog.domain.dtos.PostDto.builder()
                .title("Draft")
                .build();
        when(userService.getUserById(userId)).thenReturn(userEntity);
        when(postService.getDraftPosts(userEntity)).thenReturn(List.of(draft));
        when(postMapper.toDto(draft)).thenReturn(draftDto);

        mockMvc.perform(get("/api/v1/posts/drafts")
                        .with(user("user@test.com"))
                        .requestAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Draft"));

        verify(userService).getUserById(userId);
        verify(postService).getDraftPosts(userEntity);
    }

    @Test
    void listTagsIsPublic() throws Exception {
        UUID tagId = UUID.randomUUID();
        Tag tag = Tag.builder().id(tagId).name("Spring").build();
        TagDto tagDto = TagDto.builder().id(tagId).name("Spring").postCount(0).build();
        when(tagService.getTags()).thenReturn(List.of(tag));
        when(tagMapper.toTagResponse(tag)).thenReturn(tagDto);

        mockMvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(tagId.toString()))
                .andExpect(jsonPath("$[0].name").value("Spring"));

        verify(tagService).getTags();
    }

    @Test
    void createTagsReturnsCreatedTags() throws Exception {
        Tag tag = Tag.builder().name("Spring").build();
        TagDto tagDto = TagDto.builder().name("Spring").postCount(0).build();
        when(tagService.createTags(Set.of("Spring"))).thenReturn(List.of(tag));
        when(tagMapper.toTagResponse(tag)).thenReturn(tagDto);

        mockMvc.perform(post("/api/v1/tags")
                        .with(user("user@test.com"))
                        .contentType("application/json")
                        .content("{\"names\":[\"Spring\"]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].name").value("Spring"));

        verify(tagService).createTags(Set.of("Spring"));
    }

    @Test
    void deleteTagReturnsNoContent() throws Exception {
        UUID tagId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/tags/{id}", tagId)
                        .with(user("user@test.com")))
                .andExpect(status().isNoContent());

        verify(tagService).deleteTag(tagId);
    }
}
