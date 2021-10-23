package core.api.response;

public class StatisticsResponse {
    String postsCount;
    String likesCount;
    String dislikesCount;
    String viewsCount;
    String firstPublication;

    public StatisticsResponse(String postsCount, String likesCount, String dislikesCount, String viewsCount, String firstPublication) {
        this.postsCount = postsCount;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.viewsCount = viewsCount;
        this.firstPublication = firstPublication;
    }


    public String getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(String postsCount) {
        this.postsCount = postsCount;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public String getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(String dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public String getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(String viewsCount) {
        this.viewsCount = viewsCount;
    }

    public String getFirstPublication() {
        return firstPublication;
    }

    public void setFirstPublication(String firstPublication) {
        this.firstPublication = firstPublication;
    }
}
