package core.api.response.Tags;

import java.util.ArrayList;
import java.util.List;

public class TagsResponse {
    List<TagsForResponse> tags = new ArrayList<>();

    public List<TagsForResponse> getTagsForResponses() {
        return tags;
    }

    public void setTagsForResponses(List<TagsForResponse> tags) {
        this.tags = tags;
    }

}
