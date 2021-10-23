package core.api.response.Posts;

import java.util.ArrayList;
import java.util.List;

public class PostResponse {
    private int count = 0;
    private List<PostsForResponse> posts = new ArrayList<>();

    public void addPost(PostsForResponse post){
        posts.add(post);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PostsForResponse> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsForResponse> posts) {
        this.posts = posts;
    }
    public void addCount(){
        count++;
    }
}
