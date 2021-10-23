package core.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import core.api.request.AlterProfileJsonRequest;
import core.api.request.AlterProfileRequest;
import core.api.request.SettingsRequest;
import core.api.response.AlterProfileResponse;
import core.api.response.Comments.IdCommentResponse;
import core.api.response.StatisticsResponse;
import core.api.response.Tags.TagsForResponse;
import core.api.response.Tags.TagsResponse;
import core.model.*;
import core.model.repository.*;
import net.bytebuddy.utility.RandomString;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneralService {
    private TagToPostRepository tagToPostRepository;
    private PostRepository postRepository;
    private TagRepository tagRepository;
    private UserRepository userRepository;
    private PostCommentRepository commentRepository;
    private GlobalSettingRepository globalSettingRepository;


    @Autowired
    public GeneralService(TagToPostRepository tagToPostRepository, PostRepository postRepository, TagRepository tagRepository, UserRepository userRepository, PostCommentRepository commentRepository, GlobalSettingRepository globalSettingRepository) {
        this.tagToPostRepository = tagToPostRepository;
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.globalSettingRepository = globalSettingRepository;
    }

    public TagsResponse getTagResponse(String tag) {
        TagsResponse tagsResponse = new TagsResponse();
        Map<String, TagsForResponse> tagsMap = new HashMap<>();
        if (tag.trim().equals("")) {
            Iterable<TagToPost> tagIterable = tagToPostRepository.findAll();
            for (TagToPost tag1 : tagIterable) {
                float f = (float) Math.round(weightOfTag(tag1.getTag().getName()) * 100.0) / (float) 100.0;
                tagsMap.put(tag1.getTag().getName(), new TagsForResponse(tag1.getTag().getName(), String.valueOf(f)));
            }
        } else {
            tagsMap.put(tag, new TagsForResponse(tag, String.valueOf(weightOfTag(tag))));
        }
        tagsResponse.setTagsForResponses(new ArrayList<>(tagsMap.values()));
        return tagsResponse;
    }

    private long getTotalCountOfPosts() {
        long count = 0;
        Iterable<Post> postIterable = postRepository.findAll();
        for (Post post :
                postIterable) {
            count++;
        }
        return count;
    }

    private long getCountOfTag(String tag) {
        long count = 0;
        Iterable<TagToPost> tagToPostRepositoryIterable = tagToPostRepository.findAll();
        for (TagToPost tag2Post : tagToPostRepositoryIterable) {
            if (tag2Post.getTag().getName().equals(tag)) {
                count++;
            }
        }
        return count;
    }

    private float getK() {
        Iterable<TagToPost> tagToPostRepositoryIterable = tagToPostRepository.findAll();
        List<Tag> tags = new ArrayList<>();
        for (TagToPost tag2Post : tagToPostRepositoryIterable) {
            tags.add(tag2Post.getTag());
        }
        tags.stream().sorted(Comparator.comparing(Tag::getName)).collect(Collectors.toList());
        long countDuplicates = 1;
        long maxCountDuplicates = 1;
        Tag mostPopTag = new Tag();
        for (int i = 1; i < tags.size(); i++) {
            if (tags.get(i) == tags.get(i - 1)) {
                countDuplicates++;
            } else {
                if (countDuplicates > maxCountDuplicates) {
                    maxCountDuplicates = countDuplicates;
                    mostPopTag = tags.get(i - 1);
                }
                countDuplicates = 1;
            }
        }
        float f = (float) getCountOfTag(mostPopTag.getName()) / getTotalCountOfPosts();
        return (float) 1 / f;
    }

    private float weightOfTag(String tag) {
        float f = (float) getCountOfTag(tag) / getTotalCountOfPosts();
        return f * getK();
    }

    public String postImage(MultipartFile image) throws IOException {
        if (image.getSize() > 5_000_000) {
            return "";
        }

        String[] s = RandomString.make(6).split("");

        FileUtils.writeByteArrayToFile(new File("C:\\Users\\Jevai\\IdeaProjects\\java_basics\\17_GraduateProject\\Blog\\src\\main\\resources\\upload\\"
                        + s[0] + s[1] + "\\" + s[2] + s[3] + "\\" + s[4] + s[5] + "\\" + image.getResource().getFilename()),
                image.getBytes());
        return ("\\upload\\"
                + s[0] + s[1] + "\\" + s[2] + s[3] + "\\" + s[4] + s[5] + "\\" + image.getResource().getFilename());
    }


    private User getAuthUser() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(AuthService.getAuthName())) {
                return user;
            }
        }
        return null;
    }

    public boolean isExistEmail(String email) {
        Iterable<User> userIterable = userRepository.findAll();
        for (User user : userIterable) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCommentsExist(int id) {
        Iterable<PostComment> commentIterable = commentRepository.findAll();
        for (PostComment comment : commentIterable) {
            if (comment.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private PostComment getComment(int id) {
        Iterable<PostComment> commentIterable = commentRepository.findAll();
        for (PostComment comment : commentIterable) {
            if (comment.getId() == id) {
                return comment;
            }
        }
        return null;
    }


    public IdCommentResponse postComment(String parentId, String postId, String text) {
        Iterable<Post> postIterable = postRepository.findAll();
        PostComment postComment = new PostComment();
        for (Post post : postIterable) {
            if (post.getId() == Integer.parseInt(postId)) {
                if (!(parentId == null)) {
                    if (!isCommentsExist(Integer.parseInt(parentId))) {
                        return new IdCommentResponse("-1");
                    }
                    postComment.setParent(getComment(Integer.parseInt(parentId)));
                }
                postComment.setUsers(getAuthUser());
                postComment.setPosts(post);
                postComment.setTime(new Timestamp(System.currentTimeMillis()));
                postComment.setText(text);
                return new IdCommentResponse(String.valueOf(commentRepository.save(postComment)));
            }
        }
        return new IdCommentResponse("-1");
    }

    public String postModeration(String postId, String decision) {
        ModerationStatus moderationStatus;
        if (decision.equals("accept")) {
            moderationStatus = ModerationStatus.ACCEPTED;
        } else {
            moderationStatus = ModerationStatus.DECLINED;
        }
        Iterable<Post> postIterable = postRepository.findAll();
        for (Post post : postIterable) {
            if (post.getId() == Integer.parseInt(postId)) {
                post.setModerationStatus(moderationStatus);
                post.setModeration(getAuthUser());
                postRepository.save(post);
                return "true";
            }
        }
        return "false";
    }

    public AlterProfileResponse alterProfile(AlterProfileRequest request) throws IOException {
        AlterProfileResponse alterProfileResponse = new AlterProfileResponse("true");
        User user = getAuthUser();
        if (request.getName() != null) {
            if (request.getName().trim().length() < 3) {
                alterProfileResponse.setResult("false");
                alterProfileResponse.getAlterProfileErrors().setName("Имя указано неверно");
            } else {
                user.setName(request.getName());
            }
        }

        if (request.getEmail() != null) {
            if (isExistEmail(request.getEmail()) && !(request.getEmail().equals(getAuthUser().getEmail()))) {
                alterProfileResponse.setResult("false");
                alterProfileResponse.getAlterProfileErrors().setEmail("Этот e-mail уже зарегистрирован");
            } else {
                user.setEmail(request.getEmail());
            }
        }
        if (request.getPassword() != null) {
            if (request.getPassword().length() < 6) {
                alterProfileResponse.setResult("false");
                alterProfileResponse.getAlterProfileErrors().setPassword("Пароль короче 6-ти символов");
            } else {
                user.setPassword(AuthService.bcrypt(request.getPassword()));
            }
        }

        if (request.getRemovePhoto() != null) {

            if (request.getRemovePhoto().equals("0")) {

                if (request.getPhoto().getSize() > 5_000_000) {
                    alterProfileResponse.setResult("false");
                    alterProfileResponse.getAlterProfileErrors().setPhoto("Фото слишком большое, нужно не более 5 Мб");
                } else {
                    String[] s = RandomString.make(6).split("");
                    File file = new File("file.jpg");
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    Thumbnails.of(new ByteArrayInputStream(request.getPhoto().getBytes()))
                            .crop(Positions.CENTER)
                            .size(36, 36)
                            .outputFormat("jpg")
                            .toFile(file);
                    user.setPhoto(savePhotoInCloud(file));
                }
            } else {
                user.setPhoto(null);
            }
        }
        if (alterProfileResponse.getResult().equals("true")) {
            userRepository.save(user);
        }
        return alterProfileResponse;
    }

    private String savePhotoInCloud(File f) throws FileNotFoundException {
        String s = RandomString.make(6);
        Regions clientRegion = Regions.EU_CENTRAL_1;
        String bucketName = "jevaibucket/publicprefix";
        String fileObjKeyName = s + ".jpg";
        String fileName = s;

        AWSCredentials awsCredentials =
                new BasicAWSCredentials("AKIAVAR2I7GKLP66SIHL", "W3dXfLlwvfj+E8ucH62wwgalYZufOXLwFx2yxWu+");
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(clientRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        // Upload a file as a new object with ContentType and title specified.
        PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, f);
        s3Client.putObject(request);
        return "https://jevaibucket.s3.eu-central-1.amazonaws.com/publicprefix/" + fileObjKeyName;
    }

    public AlterProfileResponse alterProfile(AlterProfileJsonRequest request) throws IOException {
        AlterProfileResponse alterProfileResponse = new AlterProfileResponse("true");
        User user = getAuthUser();
        if (request.getName() != null) {
            if (request.getName().trim().length() < 3) {
                alterProfileResponse.setResult("false");
                alterProfileResponse.getAlterProfileErrors().setName("Имя указано неверно");
            } else {
                user.setName(request.getName());
            }
        }

        if (request.getEmail() != null) {
            if (isExistEmail(request.getEmail()) && !(request.getEmail().equals(getAuthUser().getEmail()))) {
                alterProfileResponse.setResult("false");
                alterProfileResponse.getAlterProfileErrors().setEmail("Этот e-mail уже зарегистрирован");
            } else {
                user.setEmail(request.getEmail());
            }
        }
        if (request.getPassword() != null) {
            if (request.getPassword().length() < 6) {
                alterProfileResponse.setResult("false");
                alterProfileResponse.getAlterProfileErrors().setPassword("Пароль короче 6-ти символов");
            } else {
                user.setPassword(AuthService.bcrypt(request.getPassword()));
            }
        }

        if (request.getRemovePhoto() != null) {
            if (request.getRemovePhoto().equals("1")) {
                user.setPhoto(null);
            }
            if (alterProfileResponse.getResult().equals("true")) {
                userRepository.save(user);
            }
        }
        return alterProfileResponse;
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    public StatisticsResponse getStatisticMy() {
        int postsCount = 0;
        int likesCount = 0;
        int dislikesCount = 0;
        int viewsCount = 0;
        Timestamp firstPublication = new Timestamp(System.currentTimeMillis());
        Iterable<Post> postIterable = postRepository.findAll();
        User user = getAuthUser();
        for (Post post : postIterable) {
            if (post.getUser() == user && post.getModerationStatus() == ModerationStatus.ACCEPTED) {
                if (firstPublication == null) {
                    firstPublication = post.getTime();
                }
                if (firstPublication.compareTo(post.getTime()) > 0) {
                    firstPublication = post.getTime();
                }
                postsCount++;
                likesCount += post.getLikeCount();
                dislikesCount += post.getDislikeCount();
                viewsCount += post.getViewCount();
            }
        }
        return new StatisticsResponse(String.valueOf(postsCount),
                String.valueOf(likesCount),
                String.valueOf(dislikesCount),
                String.valueOf(viewsCount),
                String.valueOf(firstPublication.getTime() / 1000L));
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

    public StatisticsResponse getStatisticAll() {
        Iterable<GlobalSetting> globalSettingIterable = globalSettingRepository.findAll();
        for (GlobalSetting gs : globalSettingIterable) {
            if (gs.getCode().equalsIgnoreCase("STATISTICS_IS_PUBLIC")) {
                if (gs.getValue().equalsIgnoreCase("no") && !isAuthModerator()) {
                    return null;
                }
            }
        }

        int postsCount = 0;
        int likesCount = 0;
        int dislikesCount = 0;
        int viewsCount = 0;
        Timestamp firstPublication = null;
        Iterable<Post> postIterable = postRepository.findAll();
        for (Post post : postIterable) {

            if (post.getModerationStatus() == ModerationStatus.ACCEPTED) {
                if (firstPublication == null) {
                    firstPublication = post.getTime();
                }
                if (firstPublication.compareTo(post.getTime()) > 0) {
                    firstPublication = post.getTime();
                }
                postsCount++;
                likesCount += post.getLikeCount();
                dislikesCount += post.getDislikeCount();
                viewsCount += post.getViewCount();
            }
        }


        return new StatisticsResponse(String.valueOf(postsCount),
                String.valueOf(likesCount),
                String.valueOf(dislikesCount),
                String.valueOf(viewsCount),
                String.valueOf(firstPublication.getTime() / 1000L));
    }

    public void setGlobalSettings(SettingsRequest settingsRequest) {
        Iterable<GlobalSetting> globalSettingIterable = globalSettingRepository.findAll();
        for (GlobalSetting gs : globalSettingIterable) {
            if (gs.getCode().equals("MULTIUSER_MODE")) {
                if ((settingsRequest.getMultiUserMode().equals("true"))) {
                    gs.setValue("yes");
                } else {
                    gs.setValue("no");
                }
            }
            if (gs.getCode().equals("POST_PREMODERATION")) {
                if ((settingsRequest.getPostPremoderation().equals("true"))) {
                    gs.setValue("yes");
                } else {
                    gs.setValue("no");
                }
            }
            if (gs.getCode().equals("STATISTICS_IS_PUBLIC")) {
                if ((settingsRequest.getStaticIsPublic().equals("true"))) {
                    gs.setValue("yes");
                } else {
                    gs.setValue("no");
                }
            }
            globalSettingRepository.save(gs);
        }
    }

}
