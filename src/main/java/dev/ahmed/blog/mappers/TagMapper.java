package dev.ahmed.blog.mappers;

import dev.ahmed.blog.domain.PostStatus;
import dev.ahmed.blog.domain.dtos.TagDto;
import dev.ahmed.blog.domain.entities.Post;
import dev.ahmed.blog.domain.entities.Tag;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagDto toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null) {
            return 0;
        }
        return (int) posts.stream()
                .filter(post ->
                    PostStatus.PUBLISHED.equals(post.getStatus())
                )
                .count();
    }
}
