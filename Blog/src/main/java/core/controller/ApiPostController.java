package core.controller;


import core.api.request.VoteRequest;
import core.api.request.Post.*;
import core.api.response.Posts.NewPostResponse;
import core.api.response.Posts.PostResponse;
import core.api.response.Posts.SinglePostResponse;
import core.service.AuthService;
import core.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api")
public class ApiPostController {
    PostService postService;

    @Autowired
    public ApiPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> post(@RequestParam(defaultValue = "0") String offset,
                                             @RequestParam(defaultValue = "10") String limit,
                                             @RequestParam(defaultValue = "recent") String mode) {

        PostRequest postRequest = new PostRequest(offset, limit, mode);
        PostResponse postResponse = postService.getPostResponse(postRequest);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/post/search")
    public ResponseEntity<PostResponse> search(@RequestParam(defaultValue = "0") String offset,
                                               @RequestParam(defaultValue = "10") String limit,
                                               @RequestParam(defaultValue = "") String query) {
        PostRequestSearch postRequestSearch = new PostRequestSearch(offset, limit, query);
        PostResponse postResponse = postService.getPostResponseSearch(postRequestSearch);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/post/byDate")
    public ResponseEntity<PostResponse> searchByDate(@RequestParam(defaultValue = "0") String offset,
                                                     @RequestParam(defaultValue = "10") String limit,
                                                     @RequestParam String date) throws ParseException {
        PostRequestDate postRequestDate = new PostRequestDate(offset, limit, date);
        PostResponse postResponse = postService.getPostResponseByDate(postRequestDate);
        return ResponseEntity.ok(postResponse);

    }

    @GetMapping("/post/byTag")
    public ResponseEntity<PostResponse> searchByTag(@RequestParam(defaultValue = "0") String offset,
                                                    @RequestParam(defaultValue = "10") String limit,
                                                    @RequestParam String tag) {
        PostRequestTag postRequestTag = new PostRequestTag(offset, limit, tag);
        PostResponse postResponse = postService.getPostResponseByTag(postRequestTag);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("post/{id}")
    public ResponseEntity<SinglePostResponse> postById(@PathVariable String id) {
        SinglePostResponse singlePostResponse = postService.getPostById(Integer.parseInt(id));
        return (!(singlePostResponse == null)) ? ResponseEntity.ok(singlePostResponse)
                : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/post/moderation")
    public ResponseEntity<PostResponse> postModeration(@RequestParam(defaultValue = "0") String offset,
                                                       @RequestParam(defaultValue = "10") String limit,
                                                       @RequestParam(defaultValue = "new") String status) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        PostModerationRequest postModerationRequest = new PostModerationRequest(offset, limit, status);
        PostResponse postResponse = postService.getModerationPosts(postModerationRequest);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/post/my")
    public ResponseEntity<PostResponse> postMy(@RequestParam(defaultValue = "0") String offset,
                                               @RequestParam(defaultValue = "10") String limit,
                                               @RequestParam(defaultValue = "new") String status) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        PostModerationRequest postModerationRequest = new PostModerationRequest(offset, limit, status);
        PostResponse postResponse = postService.getMyPosts(postModerationRequest);
        return ResponseEntity.ok(postResponse);
    }


    @PostMapping("/post")
    public ResponseEntity<NewPostResponse> createPost(@RequestBody NewPostRequest newPostRequest) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        NewPostResponse newPostResponse = postService.createNewPost(newPostRequest);
        return ResponseEntity.ok(newPostResponse);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<NewPostResponse> editPost(@PathVariable String id,
                                                    @RequestBody NewPostRequest newPostRequest) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        NewPostResponse newPostResponse = postService.editPost(newPostRequest, id);
        return ResponseEntity.ok(newPostResponse);
    }

    @PostMapping("/post/like")
    public ResponseEntity postLike(@RequestBody VoteRequest voteRequest) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(postService.postLike(voteRequest.getPostId()), HttpStatus.OK);
    }

    @PostMapping("/post/dislike")
    public ResponseEntity postDislike(@RequestBody VoteRequest voteRequest) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(postService.postDislike(voteRequest.getPostId()), HttpStatus.OK);
    }
}
