package com.user.lms.domain;

import com.user.lms.entity.Photo;
import com.user.lms.models.PhotoModel;
import com.user.lms.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    /*@Transactional
    public Photo addPhoto(PhotoModel photoModel){
        Photo photo=new Photo();
        photo.setPhotoUrl(photoModel.getPhotoUrl());
        photo.setDriver(photoModel.getDriver());
        photo.setVehicle(photoModel.getVehicle());

        photo = this.photoRepository.saveAndFlush(photo);

        return  photo;
    }

    @Transactional
    public void deletePhoto(Long photoId) {
        this.photoRepository.deleteById(photoId);
    }

    public PhotoModel getPhotoById(String id){
        Optional<Photo> optionalPhoto =  this.photoRepository.findById(Long.parseLong(id));
        if(optionalPhoto.isPresent()){
            Photo photo =   optionalPhoto.get();
            PhotoModel photoModel = new PhotoModel();
            photoModel.setId(photo.getId());
            photoModel.setPhotoUrl(photo.getPhotoUrl());
            photoModel.setDriver(photo.getDriver());
            photoModel.setVehicle(photo.getVehicle());

            return  photoModel;
        }
        return null;
    }*/
}
