package core.api.request;

import org.springframework.web.multipart.MultipartFile;

public class AlterProfileJsonRequest {
    String photo;
    String removePhoto;
    String name;
    String email;
    String password;

    public AlterProfileJsonRequest(String photo, String removePhoto, String name, String email, String password) {
        this.photo = photo;
        this.removePhoto = removePhoto;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRemovePhoto() {
        return removePhoto;
    }

    public void setRemovePhoto(String removePhoto) {
        this.removePhoto = removePhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
