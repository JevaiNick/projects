package core.service;

import core.api.request.Post.*;
import core.api.response.*;
import core.api.response.Posts.NewPostResponse;
import core.api.response.Posts.PostResponse;
import core.api.response.Posts.PostsForResponse;
import core.api.response.Posts.SinglePostResponse;
import core.api.response.Users.UserPostResponse;
import core.model.*;
import core.model.ModerationStatus;
import core.api.request.Post.OutputMode;
import core.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostService {
    private TagRepository tagRepository;
    private TagToPostRepository tagToPostRepository;
    private PostRepository postRepository;
    private PostVoteRepository postVoteRepository;
    private List<PostsForResponse> postsForResponseList = new ArrayList<>();
    private UserRepository userRepository;
    private GlobalSettingRepository globalSettingRepository;
//    private List<PostsForResponse> postsForResponseSearchList = new ArrayList<>();

    @Autowired
    public PostService(TagRepository tagRepository, TagToPostRepository tagToPostRepository, PostRepository postRepository, PostVoteRepository postVoteRepository, UserRepository userRepository, GlobalSettingRepository globalSettingRepository) {
        this.tagRepository = tagRepository;
        this.tagToPostRepository = tagToPostRepository;
        this.postRepository = postRepository;
        this.postVoteRepository = postVoteRepository;
        this.userRepository = userRepository;
        this.globalSettingRepository = globalSettingRepository;
    }

    private void fillPostsForResponseList(OutputMode mode, int limit, int offset) {
        postsForResponseList.clear();
        Iterable<Post> postIterable = postRepository.findAll();

        if (mode == OutputMode.recent) {
            postIterable = StreamSupport.stream(
                    postIterable.spliterator(), false)
                    .sorted(Comparator.comparing(Post::getTime).reversed())
                    .collect(Collectors.toList());
        }
        if (mode == OutputMode.early) {
            postIterable = StreamSupport.stream(
                    postIterable.spliterator(), false)
                    .sorted(Comparator.comparing(Post::getTime))
                    .collect(Collectors.toList());
        }
        if (mode == OutputMode.best) {
            postIterable = StreamSupport.stream(
                    postIterable.spliterator(), false)
                    .sorted(Comparator.comparing(Post::getLikeCount).reversed())
                    .collect(Collectors.toList());

        }
        if (mode == OutputMode.popular) {
            postIterable = StreamSupport.stream(
                    postIterable.spliterator(), false)
                    .sorted(Comparator.comparing(Post::getCommentCount).reversed())
                    .collect(Collectors.toList());
        }
        int i = 0;
        for (Post post : postIterable) {

            if (limit > 0) {

                if (post.isActive()
                        && post.getModerationStatus() == ModerationStatus.ACCEPTED
                        && post.getTime().getTime() <= new Date().getTime()) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                    limit--;
                }

            } else {
                break;
            }
        }
    }


    public PostResponse getPostResponse(PostRequest postRequest) {
        int offset = Integer.parseInt(postRequest.getOffset());
        int limit = Integer.parseInt(postRequest.getLimit());
        OutputMode mode = setMode(postRequest.getMode());
        fillPostsForResponseList(mode, limit, offset);

        PostResponse postResponse = new PostResponse();
        postResponse.setCount(postsForResponseList.size());

        postResponse.setPosts(postsForResponseList);
        return postResponse;
    }

    private OutputMode setMode(String mode) {
        if (mode.equals("popular")) {
            return OutputMode.popular;
        }
        if (mode.equals("best")) {
            return OutputMode.best;
        }
        if (mode.equals("early")) {
            return OutputMode.early;
        }
        return OutputMode.recent;
    }


    public PostResponse getPostResponseSearch(PostRequestSearch postRequestSearch) {
        int offset = Integer.parseInt(postRequestSearch.getOffset());
        int limit = Integer.parseInt(postRequestSearch.getLimit());
        if (!postRequestSearch.getQuery().trim().equals("")) {
            fillPostsForResponseSearchList(postRequestSearch.getQuery(), limit, offset);
            PostResponse postResponse = new PostResponse();
            postResponse.setCount(postsForResponseList.size());
            postResponse.setPosts(postsForResponseList);
            return postResponse;
        } else {
            PostRequest postRequest = new PostRequest(postRequestSearch.getOffset(), postRequestSearch.getLimit(), "recent");
            return getPostResponse(postRequest);
        }
    }

    private void fillPostsForResponseSearchList(String query, int limit, int offset) {
        postsForResponseList.clear();
        Iterable<Post> postIterable = postRepository.findAll();
        int i = 0;
        for (Post post : postIterable) {


            if (limit > 0) {
                if (post.getTitle().contains(query)
                        && post.isActive()
                        && post.getModerationStatus() == ModerationStatus.ACCEPTED
                        && post.getTime().getTime() <= new Date().getTime()) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                }
            } else {
                break;
            }
        }
    }

    public CalendarResponse getCalendar(String year) {
        Calendar inputYear = Calendar.getInstance();
        SimpleDateFormat formmat = new SimpleDateFormat("yyyy-MM-dd");
        inputYear.set(Calendar.YEAR, Integer.parseInt(year));

        Map<String, Integer> posts = new HashMap<>();
        HashSet<Integer> years = new HashSet<>();
        Calendar c = Calendar.getInstance();
        Iterable<Post> postIterable = postRepository.findAll();
        for (Post post : postIterable) {
            if (post.isActive()
                    && post.getModerationStatus() == ModerationStatus.ACCEPTED
                    && post.getTime().getTime() <= new Date().getTime()) {
                c.setTimeInMillis(post.getTime().getTime());
                years.add(c.get(Calendar.YEAR));
                if (inputYear.get(Calendar.YEAR) == c.get(Calendar.YEAR)) {
                    if (posts.containsKey(formmat.format(c.getTime()))) {
                        posts.put(formmat.format(c.getTime()), posts.get(formmat.format(c.getTime())) + 1);
                    } else {
                        posts.put(formmat.format(c.getTime()), 1);
                    }
                }
            }
        }
        CalendarResponse calendarResponse = new CalendarResponse();
        calendarResponse.setPosts(posts);
        calendarResponse.getYears().addAll(years);
        return calendarResponse;
    }

    public PostResponse getPostResponseByDate(PostRequestDate postRequestDate) throws ParseException {
        int limit = Integer.parseInt(postRequestDate.getLimit());
        int offset = Integer.parseInt(postRequestDate.getOffset());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(postRequestDate.getDate());
        fillPostsForResponseDateList(date, limit, offset);
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(postsForResponseList.size());
        postResponse.setPosts(postsForResponseList);
        return postResponse;
    }

    private void fillPostsForResponseDateList(Date date, int limit, int offset) {
        postsForResponseList.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Iterable<Post> postIterable = postRepository.findAll();
        int i = 0;
        for (Post post : postIterable) {

            if (limit > 0) {
                if (sdf.format(post.getTime()).equals(sdf.format(date))
                        && post.isActive()
                        && post.getModerationStatus() == ModerationStatus.ACCEPTED
                        && post.getTime().getTime() <= new Date().getTime()) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                }
            } else {
                break;
            }
        }
    }

    public PostResponse getPostResponseByTag(PostRequestTag postRequestTag) {
        int limit = Integer.parseInt(postRequestTag.getLimit());
        int offset = Integer.parseInt(postRequestTag.getOffset());
        fillPostsForResponseTagList(postRequestTag.getTag(), limit, offset);
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(postsForResponseList.size());
        postResponse.setPosts(postsForResponseList);
        return postResponse;
    }

    private void fillPostsForResponseTagList(String tag, int limit, int offset) {
        postsForResponseList.clear();
        Iterable<Post> postIterable = postRepository.findAll();
        int i = 0;
        for (Post post : postIterable) {


            if (limit > 0) {
                if (post.getTagsString().contains(tag)
                        && post.isActive()
                        && post.getModerationStatus() == ModerationStatus.ACCEPTED
                        && post.getTime().getTime() <= new Date().getTime()) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                }
            } else {
                break;
            }
        }
    }


    public SinglePostResponse getPostById(int id) {
        Iterable<Post> postIterable = postRepository.findAll();
        SinglePostResponse postResponse = new SinglePostResponse();
        for (Post post : postIterable) {
            if (post.getId() == id) {
                postResponse = new SinglePostResponse(post.getId(),
                        post.getTime().getTime() / 1000L,
                        new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                        post.getTitle(),
                        post.getText(),
                        post.getLikeCount(),
                        post.getDislikeCount(),
                        post.getCommentCount(),
                        post.getViewCount(),
                        post.getTagsString());
                for (PostComment postComment : post.getPostComments()) {
                    postResponse.addComment(postComment);
                }

                if (!(AuthService.getAuthName().equals(post.getUser().getEmail()) || isAuthModerator())) {
                    post.setViewCount(post.getViewCount() + 1);
                    postRepository.save(post);
                }

                return postResponse;
            }
        }
        return null;
    }

    private boolean isAuthModerator() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(AuthService.getAuthName())
                    && user.isModerator()) {
                return true;
            }
        }
        return false;
    }

    public PostResponse getModerationPosts(PostModerationRequest postModerationRequest) {
        int offset = Integer.parseInt(postModerationRequest.getOffset());
        int limit = Integer.parseInt(postModerationRequest.getLimit());
        ModerationStatus status = setStatus(postModerationRequest.getStatus());
        fillModerationPostsForResponseList(status, limit, offset);
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(postsForResponseList.size());
        postResponse.setPosts(postsForResponseList);
        return postResponse;
    }

    private void fillModerationPostsForResponseList(ModerationStatus status, int limit, int offset) {
        postsForResponseList.clear();
        Iterable<Post> postIterable = postRepository.findAll();
        int i = 0;
        for (Post post : postIterable) {

            if (limit > 0) {

                if (post.isActive()) {
                    if (status == ModerationStatus.NEW && post.getModerationStatus() == ModerationStatus.NEW) {
                        if (offset > i) {
                            i++;
                            continue;
                        }
                        postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                                new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                                post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                                post.getCommentCount(), post.getViewCount()));
                        limit--;
                    } else {
                        if (status == ModerationStatus.ACCEPTED && post.getModerationStatus() == ModerationStatus.ACCEPTED
                                && post.getModeration().getEmail().equals(AuthService.getAuthName())) {
                            if (offset > i) {
                                i++;
                                continue;
                            }
                            postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                                    new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                                    post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                                    post.getCommentCount(), post.getViewCount()));
                            limit--;
                        }

                        if (status == ModerationStatus.DECLINED && post.getModerationStatus() == ModerationStatus.DECLINED
                                && post.getModeration().getEmail().equals(AuthService.getAuthName())) {
                            if (offset > i) {
                                i++;
                                continue;
                            }
                            postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                                    new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                                    post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                                    post.getCommentCount(), post.getViewCount()));
                            limit--;
                        }
                    }
                }


            } else {
                break;
            }
        }
    }


    private ModerationStatus setStatus(String status) {
        if (status.equals("new")) {
            return ModerationStatus.NEW;
        }
        if (status.equals("declined")) {
            return ModerationStatus.DECLINED;
        }
        return ModerationStatus.ACCEPTED;
    }

    public PostResponse getMyPosts(PostModerationRequest postModerationRequest) {
        int offset = Integer.parseInt(postModerationRequest.getOffset());
        int limit = Integer.parseInt(postModerationRequest.getLimit());
        fillMyPostsForResponseList(postModerationRequest.getStatus(), limit, offset);
        PostResponse postResponse = new PostResponse();
        postResponse.setCount(postsForResponseList.size());
        postResponse.setPosts(postsForResponseList);
        return postResponse;
    }

    private void fillMyPostsForResponseList(String status, int limit, int offset) {
        postsForResponseList.clear();
        Iterable<Post> postIterable = postRepository.findAll();
        int i = 0;
        for (Post post : postIterable) {

            if (limit > 0) {

                if (status.equals("inactive") && !post.isActive()
                        && post.getUser().getEmail().equals(AuthService.getAuthName())) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                    limit--;
                }

                if (status.equals("pending") && post.isActive() && post.getModerationStatus() == ModerationStatus.NEW
                        && post.getUser().getEmail().equals(AuthService.getAuthName())) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                    limit--;
                }

                if (status.equals("declined") && post.isActive() && post.getModerationStatus() == ModerationStatus.DECLINED
                        && post.getUser().getEmail().equals(AuthService.getAuthName())) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                    limit--;
                }

                if (status.equals("published") && post.isActive() && post.getModerationStatus() == ModerationStatus.ACCEPTED
                        && post.getUser().getEmail().equals(AuthService.getAuthName())) {
                    if (offset > i) {
                        i++;
                        continue;
                    }
                    postsForResponseList.add(new PostsForResponse(post.getId(), post.getTime().getTime() / 1000L,
                            new UserPostResponse(post.getUser().getId(), post.getUser().getName()),
                            post.getTitle(), post.getText(), post.getLikeCount(), post.getDislikeCount(),
                            post.getCommentCount(), post.getViewCount()));
                    limit--;
                }


            } else {
                break;
            }
        }
    }

    private boolean isPremoderation(){
        Iterable<GlobalSetting> globalSettingIterable = globalSettingRepository.findAll();
        for (GlobalSetting gs : globalSettingIterable){
            if (gs.getCode().equals("POST_PREMODERATION")){
                if (gs.getValue().equalsIgnoreCase("yes")){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public NewPostResponse createNewPost(NewPostRequest postRequest){
        NewPostResponse newPostResponse = new NewPostResponse("true");
        if (postRequest.getTitle().length() < 3){
            newPostResponse.setResult("false");
            newPostResponse.getNewPostErrors().setTitle("Заголовок слишком короткий");
        }
        if (postRequest.getText().length() < 50){
            newPostResponse.setResult("false");
            newPostResponse.getNewPostErrors().setText("Текст публикации слишком короткий");
        }
        if (newPostResponse.getResult().equals("false")){
            return newPostResponse;
        }

        Post newPost = new Post();
        if (postRequest.getTimestamp() < System.currentTimeMillis()){
            newPost.setTime(new Timestamp(System.currentTimeMillis()));
        }
        else {
            newPost.setTime(new Timestamp(postRequest.getTimestamp()));
        }
        if (postRequest.getActive() == 1) {
            newPost.setActive(true);
        } else {
            newPost.setActive(false);
        }
        newPost.setTitle(postRequest.getTitle());
        newPost.setText(postRequest.getText());
        if (isPremoderation()) {
            newPost.setModerationStatus(ModerationStatus.NEW);
        }else {
            newPost.setModerationStatus(ModerationStatus.ACCEPTED);
        }
        newPost.setViewCount(0);
        newPost.setUser(getAuthUser());
        postRepository.save(newPost);
        for (String tag : postRequest.getTags()){
            tagToPostRepository.save(new TagToPost(newPost, getTag(tag)));
        }
        return newPostResponse;
    }


    private Tag getTag(String tagName){
        Iterable<Tag> tagIterable = tagRepository.findAll();
        for (Tag tag : tagIterable){
            if (tag.getName().equals(tagName)){
                return tag;
            }
        }
        Tag tag = new Tag(tagName);
        tagRepository.save(tag);
        return tag;
    }
    private User getAuthUser(){
        Iterable<User> users = userRepository.findAll();
        for (User user : users){
            if (user.getEmail().equals(AuthService.getAuthName())){
                return user;
            }
        }
        return null;
    }

    public NewPostResponse editPost(NewPostRequest postRequest, String id){
        NewPostResponse newPostResponse = new NewPostResponse("true");
        if (postRequest.getTitle().length() < 3){
            newPostResponse.setResult("false");
            newPostResponse.getNewPostErrors().setTitle("Заголовок слишком короткий");
        }
        if (postRequest.getText().length() < 50){
            newPostResponse.setResult("false");
            newPostResponse.getNewPostErrors().setText("Текст публикации слишком короткий");
        }
        if (newPostResponse.getResult().equals("false")){
            return newPostResponse;
        }
        Post newPost = new Post();
        Iterable<Post> postIterable = postRepository.findAll();
        for (Post post : postIterable){
            if (post.getId() == Integer.parseInt(id)){
                newPost = post;
                break;
            }
        }
        if (postRequest.getTimestamp() < System.currentTimeMillis()){
            newPost.setTime(new Timestamp(System.currentTimeMillis()));
        }
        else {
            newPost.setTime(new Timestamp(postRequest.getTimestamp()));
        }
        if (postRequest.getActive() == 1) {
            newPost.setActive(true);
        } else {
            newPost.setActive(false);
        }
        newPost.setTitle(postRequest.getTitle());
        newPost.setText(postRequest.getText());
        if (!isAuthModerator()) {
            newPost.setModerationStatus(ModerationStatus.NEW);
        }
        newPost.setViewCount(0);
        newPost.setUser(getAuthUser());
        postRepository.save(newPost);
        for (String tag : postRequest.getTags()){
            tagToPostRepository.save(new TagToPost(newPost, getTag(tag)));
        }
        return newPostResponse;
    }

    public ResultResponse postLike(String id){
        Post post = getPostById(id);
        User user = getAuthUser();
        Iterable<PostVote> postVoteIterable = postVoteRepository.findAll();
        for (PostVote postVote : postVoteIterable){
            if (postVote.getUsers() == user && postVote.getPosts() == post) {
                if (postVote.getValue() == (-1)) {
                    postVote.setValue(1);
                    postVoteRepository.save(postVote);
                    return new ResultResponse("true");
                }
                if (postVote.getValue() == (1)) {
                    return new ResultResponse("false");
                }

            }
        }
        PostVote postVote = new PostVote();
        postVote.setPosts(post);
        postVote.setUsers(user);
        postVote.setValue(1);
        postVote.setTime(new Timestamp(System.currentTimeMillis()));
        postVoteRepository.save(postVote);
        return new ResultResponse("true");

    }
    public ResultResponse postDislike(String id){
        Post post = getPostById(id);
        User user = getAuthUser();
        Iterable<PostVote> postVoteIterable = postVoteRepository.findAll();
        for (PostVote postVote : postVoteIterable){
            if (postVote.getUsers() == user && postVote.getPosts() == post) {
                if (postVote.getValue() == (1)) {
                    postVote.setValue(-1);
                    postVoteRepository.save(postVote);
                    return new ResultResponse("true");
                }
                if (postVote.getValue() == (-1)) {
                    return new ResultResponse("false");
                }

            }
        }
        PostVote postVote = new PostVote();
        postVote.setPosts(post);
        postVote.setUsers(user);
        postVote.setValue(-1);
        postVote.setTime(new Timestamp(System.currentTimeMillis()));
        postVoteRepository.save(postVote);
        return new ResultResponse("true");

    }

    private Post getPostById(String id){
        Iterable<Post> posts = postRepository.findAll();
        for (Post post : posts){
            if (post.getId() == Integer.parseInt(id)){
                return post;
            }
        }
        return null;
    }
}
