package core.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, columnDefinition = "tinyint")
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModerationStatus moderationStatus;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private User moderation;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private User user = new User();

    @Column(nullable = false, columnDefinition = "datetime")
    private Timestamp time;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Column(nullable = false)
    private int viewCount;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posts")
    private List<PostVote> postVotes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posts")
    private List<PostComment> postComments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "posts")
    private List<TagToPost> tag2Post;

    public List<String> getTagsString(){
        List<String> tags = new ArrayList<>();
        tag2Post.forEach(t ->{
            tags.add(t.getTag().getName());
        });
        return tags;
    }

    public List<PostVote> getPostVotes() {
        return postVotes;
    }

    public int getLikeCount(){
        final int[] count = {0};
        postVotes.forEach(v -> {
            if (v.getValue() == 1){
                count[0]++;
            }
        });
        return count[0];
    }
    public int getDislikeCount(){
        final int[] count = {0};
        postVotes.forEach(v -> {
            if (v.getValue() == (-1)){
                count[0]++;
            }
        });
        return count[0];
    }

    public int getCommentCount(){
        return postComments.size();
    }

    public void setPostVotes(List<PostVote> postVotes) {
        this.postVotes = postVotes;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }

    public List<TagToPost> getTag2Post() {
        return tag2Post;
    }

    public void setTag2Post(List<TagToPost> tag2Post) {
        this.tag2Post = tag2Post;
    }
    public void  addTag(TagToPost tag){
        tag2Post.add(tag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getModeration() {
        return moderation;
    }

    public void setModeration(User moderation) {
        this.moderation = moderation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
