package dev.ahmed.blog.mappers;

import dev.ahmed.blog.domain.CreatePostRequest;
import dev.ahmed.blog.domain.UpdatePostRequest;
import dev.ahmed.blog.domain.dtos.CreatePostRequestDto;
import dev.ahmed.blog.domain.dtos.PostDto;
import dev.ahmed.blog.domain.dtos.UpdatePostRequestDto;
import dev.ahmed.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto createPostRequestDto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
