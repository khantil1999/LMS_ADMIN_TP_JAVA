package com.user.lms.models;

import com.user.lms.entity.Photo;
import lombok.Data;

@Data
public class PhotoModel {

    private Long id;
    private String photoUrl;

    public static PhotoModel fromEntity(Photo photo) {
        PhotoModel photoModel = new PhotoModel();
        photoModel.setId(photo.getId());
        photoModel.setPhotoUrl(photo.getPath());
        return photoModel;
    }
}
