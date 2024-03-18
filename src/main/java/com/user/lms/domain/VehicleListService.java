package com.user.lms.domain;

import com.user.lms.entity.Photo;
import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.*;
import com.user.lms.repository.PhotoRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.VehicleListRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleListService {

    @Autowired
    private VehicleListRepository vehicleListRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void deleteVehicle(Long userId) {
        this.photoRepository.deleteByVehicleId(userId);
        this.vehicleListRepository.deleteById(userId);
    }

    public List<VehicleDetailsModel> getAllVehicles() {
        // Fetch the list of vehicles from the database
        List<VehicleList> vehicleList = vehicleListRepository.findAll();
        List<VehicleDetailsModel> detailsModels = new ArrayList<>();
        vehicleList.forEach(vehicle -> {
            VehicleDetailsModel vehicleDetailsModel = new VehicleDetailsModel();

            List<Photo> photos = this.photoRepository.getPhotoByVehicle(String.valueOf(vehicle.getId()));

            List<PhotoModel> photoModels = photos.stream().map(photo -> {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setPhotoUrl(photo.getPath());
                photoModel.setId(photo.getId());
                return photoModel;
            }).toList();

            vehicleDetailsModel.setId(vehicle.getId());
            vehicleDetailsModel.setCapacity(vehicle.getCapacity());
            vehicleDetailsModel.setPhoto(photoModels);

            UserDetailsModel userDetailsModel = new UserDetailsModel();
            userDetailsModel.setEmail(vehicle.getDriver().getEmail());
            userDetailsModel.setFirstName(vehicle.getDriver().getFirstName());
            userDetailsModel.setLastName(vehicle.getDriver().getLastName());
            userDetailsModel.setMobileNo(vehicle.getDriver().getMobileNo());

            vehicleDetailsModel.setTruckProvider(userDetailsModel);

            vehicleDetailsModel.setLicensePlate(vehicle.getLicensePlate());
            //vehicleDetailsModel.setTruckProvider(vehicle.getDriver());
            vehicleDetailsModel.setManufacturer(vehicle.getManufacturer());
            vehicleDetailsModel.setModel(vehicle.getModel());
            vehicleDetailsModel.setWheel(vehicle.getWheel());
            vehicleDetailsModel.setCurrentMileage(vehicle.getCurrentMileage());
            vehicleDetailsModel.setFuelType(vehicle.getFuelType());
            detailsModels.add(vehicleDetailsModel);
        });
        return detailsModels;
    }

    @Transactional
    public void deletePhoto(Long photoId) {
        this.photoRepository.deleteById(photoId);

    }

    public long countVehicle() {
        // TODO Auto-generated method stub
        return vehicleListRepository.countVehicles();
    }

    @Transactional
    public String saveVehicle(VehicleListModel vehicleListModel,
                              List<MultipartFile> photoFiles, Principal principal, Boolean isUpdate, String vehicleId) throws IOException {

        System.out.println("This is service class for adding vehicle details in db");

        try {
            if (!isUpdate) {
                if (vehicleListModel == null || photoFiles == null || photoFiles.isEmpty()) {
                    throw new IllegalArgumentException("Invalid input parameters");
                }
            } else {
                if (vehicleListModel == null) {
                    throw new IllegalArgumentException("Invalid input parameters");
                }
            }
            // Validate files

            User user = this.userRepository.findByEmail(principal.getName(), true);
            VehicleList vehicleList = new VehicleList();
            vehicleList.setFuelType(vehicleListModel.getFuelType());
            vehicleList.setManufacturer(vehicleListModel.getManufacturer());
            vehicleList.setCapacity(vehicleListModel.getCapacity());
            vehicleList.setLicensePlate(vehicleListModel.getLicensePlate());
            vehicleList.setWheel(vehicleListModel.getWheel());
            vehicleList.setCurrentMileage(vehicleListModel.getCurrentMileage());
            vehicleList.setModel(vehicleListModel.getModel());
            vehicleList.setDriver(user);
            if (isUpdate) {
                vehicleList.setId(Long.parseLong(vehicleId));
            }
            VehicleList savedVehicle = vehicleListRepository.saveAndFlush(vehicleList);

            String uploadDir = "./src/main/resources/static/uploaded-files/";
            // Save photos with timestamp-based identifiers
            for (MultipartFile file : photoFiles) {

                if (file == null || file.isEmpty()) {
                    throw new IllegalArgumentException("Invalid file");
                }
                Photo photo = new Photo();
                Path uploadPath = Paths.get(uploadDir);
                System.out.println(uploadPath.toAbsolutePath());

                // Create the upload directory if it doesn't exist
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                System.out.println(file.getOriginalFilename());
                String[] fileNameAndType = file.getOriginalFilename().split("\\.");
                String name = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date()) + "." + fileNameAndType[1];

                System.out.println(name);
                String fileName = StringUtils.cleanPath(name);
                String dbPath = uploadDir + fileName;

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                photo.setPath(fileName);
                photo.setVehicle(savedVehicle);
                photo.setTruckProvider(user);
                photoRepository.save(photo);

            }
            return "/addVehicle";
        } catch (Exception e) {
            e.printStackTrace();
            return "/addVehicle";
        }
    }


    public String saveVehicle(VehicleAddEditRequestModel requestModel, Principal principal) {
        User user = this.userRepository.findExistingUser(principal.getName());
        if (user != null) {
            VehicleList vehicle = VehicleList.fromModel(requestModel);
            vehicle.setDriver(user);
            vehicle = this.vehicleListRepository.saveAndFlush(vehicle);
            VehicleList finalVehicle = vehicle;
            List<Photo> photos = requestModel.getPhotos().stream().map(name -> {
                Photo photo = new Photo();
                photo.setPath(name);
                photo.setVehicle(finalVehicle);
                photo.setTruckProvider(user);
                return photo;
            }).toList();
            this.photoRepository.saveAllAndFlush(photos);
        }
        return "Done";
    }

    public String editVehicle(String vehicleId, VehicleAddEditRequestModel requestModel, Principal principal) {
        User user = this.userRepository.findExistingUser(principal.getName());
        VehicleList existingVehicle = this.vehicleListRepository.getReferenceById(Long.parseLong(vehicleId));
        if (user != null && existingVehicle != null) {
            VehicleList vehicle = VehicleList.fromModel(requestModel);
            vehicle.setDriver(user);
            vehicle.setId(existingVehicle.getId());
            vehicle = this.vehicleListRepository.saveAndFlush(vehicle);
            VehicleList finalVehicle = vehicle;

            if(!requestModel.getPhotos().isEmpty()){
                List<Photo> photos = requestModel.getPhotos().stream().map(name -> {
                    Photo photo = new Photo();
                    photo.setPath(name);
                    photo.setVehicle(finalVehicle);
                    photo.setTruckProvider(user);
                    return photo;
                }).toList();
                this.photoRepository.saveAllAndFlush(photos);
            }
        }
        return "Done";
    }

    public VehicleDetailsModel getVehicleById(String id) {
        Optional<VehicleList> optionalVehicle = this.vehicleListRepository.findById(Long.parseLong(id));

        List<Photo> photos = this.photoRepository.getPhotoByVehicle(id);

        if (optionalVehicle.isPresent()) {
            List<PhotoModel> photoModels = photos.stream().map(photo -> {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setPhotoUrl(photo.getPath());
                photoModel.setId(photo.getId());
                return photoModel;
            }).toList();
            VehicleList vehicle = optionalVehicle.get();

            VehicleDetailsModel vehicleDetailsModel = new VehicleDetailsModel();
            vehicleDetailsModel.setId(vehicle.getId());
            vehicleDetailsModel.setCapacity(vehicle.getCapacity());
            vehicleDetailsModel.setPhoto(photoModels);


            UserDetailsModel userDetailsModel = new UserDetailsModel();
            userDetailsModel.setEmail(vehicle.getDriver().getEmail());
            userDetailsModel.setFirstName(vehicle.getDriver().getFirstName());
            userDetailsModel.setLastName(vehicle.getDriver().getLastName());
            userDetailsModel.setMobileNo(vehicle.getDriver().getMobileNo());


            vehicleDetailsModel.setTruckProvider(userDetailsModel);


            vehicleDetailsModel.setLicensePlate(vehicle.getLicensePlate());
            //vehicleDetailsModel.setTruckProvider(vehicle.getDriver());
            vehicleDetailsModel.setManufacturer(vehicle.getManufacturer());
            vehicleDetailsModel.setModel(vehicle.getModel());
            vehicleDetailsModel.setWheel(vehicle.getWheel());
            vehicleDetailsModel.setCurrentMileage(vehicle.getCurrentMileage());
            vehicleDetailsModel.setFuelType(vehicle.getFuelType());

            return vehicleDetailsModel;
        }
        return null;
    }

    public List<VehicleDetailsModel> loadVehiclesByTp(Principal principal) {
        User user = this.userRepository.findExistingUser(principal.getName());
        if (user != null) {
            return this.vehicleListRepository.getVehicleListByTP(user.getId()).stream()
                    .map(VehicleDetailsModel::fromEntity).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
