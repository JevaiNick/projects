package core.controller;

import core.api.request.*;
import core.api.response.*;
import core.api.response.Comments.ErrorsPostCommentResponse;
import core.api.response.Comments.IdCommentResponse;
import core.api.response.Comments.PostCommentResponse;
import core.api.response.Tags.TagsResponse;
import core.service.AuthService;
import core.service.GeneralService;
import core.service.PostService;
import core.service.SettingService;
import org.hibernate.annotations.Type;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    private final SettingService settingService;
    private final GeneralService generalService;
    private final PostService postService;
    private InitResponse initResponse;

    public ApiGeneralController(SettingService settingService, GeneralService generalService, PostService postService, InitResponse initResponse) {
        this.settingService = settingService;
        this.generalService = generalService;
        this.postService = postService;
        this.initResponse = initResponse;
        initResponse.setTitle("Dev Blog");
        initResponse.setCopyright("Nick Jevai");
        initResponse.setCopyrightFrom("2021");
        initResponse.setPhone("+373 779 31 670");
        initResponse.setEmail("nikita.r200019@gmail.com");
    }


    @GetMapping("/init")
    private ResponseEntity<InitResponse> init() {
        return new ResponseEntity<>(initResponse, HttpStatus.OK);
    }

    @GetMapping("/settings")
    private ResponseEntity<SettingResponse> settings() {
        return new ResponseEntity<>(settingService.getGlobalSettings(), HttpStatus.OK);
    }

    @GetMapping("/tag")
    private ResponseEntity<TagsResponse> tag(@RequestParam(defaultValue = "") String tag) {
        TagsResponse tagsResponse = generalService.getTagResponse(tag);
        return new ResponseEntity<>(tagsResponse, HttpStatus.OK);
    }

    @GetMapping("/calendar")
    private ResponseEntity<CalendarResponse> getCalendar(@RequestParam String year) {
        return new ResponseEntity<>(postService.getCalendar(year), HttpStatus.OK);
    }

    @PostMapping(value = "/image")
    public ResponseEntity postImage(@RequestParam("image") MultipartFile image) throws IOException {
        String path = generalService.postImage(image);
        if (path.equals("")) {
            ImageResponse imageResponse = new ImageResponse("false", new ImageErrors("Размер файла превышает допустимый размер"));
            return new ResponseEntity(imageResponse, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(path);
    }

    @PostMapping("/comment")
    private ResponseEntity postComment(@RequestBody CommentRequest commentRequest) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        IdCommentResponse idCommentResponse = generalService.postComment(commentRequest.getParentId(), commentRequest.getPostId(), commentRequest.getText());
        if (idCommentResponse.equals("-1")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (commentRequest.getText().length() < 3) {
            return new ResponseEntity(new PostCommentResponse("false", new ErrorsPostCommentResponse("Текст комментария не задан или слишком короткий")), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(idCommentResponse, HttpStatus.OK);
    }

    @PostMapping("/moderation")
    private ResponseEntity postModeration(@RequestBody ModerationRequest moderationRequest) {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(new ResultResponse(generalService.postModeration(moderationRequest.getPostId(), moderationRequest.getDecision())),
                HttpStatus.OK);
    }

    @PostMapping(value = "/profile/my", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    private ResponseEntity alterMyProfileWithPhoto(@ModelAttribute AlterProfileRequest alterProfileRequest) throws IOException {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(generalService.alterProfile(alterProfileRequest), HttpStatus.OK);
    }
    @PostMapping(value = "/profile/my", consumes = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity alterMyProfile(@RequestBody AlterProfileJsonRequest alterProfileRequest) throws IOException {
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(generalService.alterProfile(alterProfileRequest), HttpStatus.OK);
    }

    @GetMapping("/statistics/my")
    public ResponseEntity getStatistics(){
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(generalService.getStatisticMy(), HttpStatus.OK);
    }

    @GetMapping("/statistics/all")
    public ResponseEntity getStatisticsAll(){
        StatisticsResponse statisticsResponse = generalService.getStatisticAll();
        if (statisticsResponse == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(generalService.getStatisticAll(), HttpStatus.OK);
    }

    @PutMapping("/settings")
    public ResponseEntity settings(@RequestBody SettingsRequest settingsRequest){
        if (AuthService.getAuthName().equals("anonymousUser")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        generalService.setGlobalSettings(settingsRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

}
