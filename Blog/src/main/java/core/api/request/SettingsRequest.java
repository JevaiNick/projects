package core.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsRequest {
    @JsonProperty("MULTIUSER_MODE")
    String multiUserMode;
    @JsonProperty("POST_PREMODERATION")
    String postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    String staticIsPublic;

    public String getMultiUserMode() {
        return multiUserMode;
    }

    public void setMultiUserMode(String multiUserMode) {
        this.multiUserMode = multiUserMode;
    }

    public String getPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(String postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public String getStaticIsPublic() {
        return staticIsPublic;
    }

    public void setStaticIsPublic(String staticIsPublic) {
        this.staticIsPublic = staticIsPublic;
    }
}
