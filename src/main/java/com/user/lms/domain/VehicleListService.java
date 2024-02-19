package com.user.lms.domain;

import com.user.lms.entity.Photo;
import com.user.lms.entity.User;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.LaborerModel;
import com.user.lms.models.UserModel;
import com.user.lms.models.VehicleListModel;
import com.user.lms.repository.PhotoRepository;
import com.user.lms.repository.UserRepository;
import com.user.lms.repository.VehicleListRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VehicleListService {

    @Autowired
    private VehicleListRepository vehicleListRepository;

    @Autowired
    private  PhotoRepository photoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void deleteVehicle(Long userId) {
        this.vehicleListRepository.deleteById(userId);
    }

    public String saveVehicle(@Valid @ModelAttribute("vehicles") VehicleListModel vehicleListModel,
                            List<MultipartFile> photoFiles, Principal principal, BindingResult bindingResult, Model model) throws IOException {
       if(bindingResult.hasErrors()){
           System.out.println("Validation errors: " + bindingResult.getAllErrors());
           model.addAttribute("vehicles", vehicleListModel);
           return "addVehicle";
       }
        // Validate files
        for (MultipartFile file : photoFiles) {
            if (file.isEmpty()) {
                bindingResult.rejectValue("photoFiles", "error.photo", "One or more files are empty");
                return "addVehicle";
            }
        }

        User user = this.userRepository.findByEmail(principal.getName(),true);
        VehicleList vehicleList = new VehicleList();
        vehicleList.setFuelType(vehicleListModel.getFuelType());
        vehicleList.setManufacturer(vehicleListModel.getManufacturer());
        vehicleList.setCapacity(vehicleListModel.getCapacity());
        vehicleList.setLicensePlate(vehicleListModel.getLicensePlate());
        vehicleList.setWheel(vehicleListModel.getWheel());
        vehicleList.setCurrentMileage(vehicleListModel.getCurrentMileage());
        vehicleList.setModel(vehicleListModel.getModel());
        vehicleList.setDriver(user);

        VehicleList savedVehicle = vehicleListRepository.saveAndFlush(vehicleList);

        String uploadDir = "src/main/resources/static/uploaded-files/";
        // Save photos with timestamp-based identifiers
        for (MultipartFile file : photoFiles) {
            Photo photo = new Photo();
            Path uploadPath = Paths.get(uploadDir);

            // Create the upload directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            System.out.println(file.getOriginalFilename());
            String[] fileNameAndType = file.getOriginalFilename().split("\\.");
            String name =  new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date()) + "." + fileNameAndType[1];

            System.out.println(name);
            String fileName = StringUtils.cleanPath(name);
            String dbPath = uploadDir + fileName;


            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            photo.setPath(dbPath);
            photo.setVehicle(savedVehicle);
            photo.setTruckProvider(user);
            photoRepository.save(photo);


        }

        Map<String, Object> data = new HashMap<>();
        data.put("vehicles", new VehicleListModel());
        data.put("successMessage", "Vehicle Added SuccessFully");

        model.addAllAttributes(data);
        return "addVehicles";
    }

    public List<VehicleList> getAllVehicles() {
        // Fetch the list of vehicles from the database
        return vehicleListRepository.findAll();
    }

    public Optional<VehicleList> findById(Long id) {
        return vehicleListRepository.findById(id);
    }

    public long count() {
        // TODO Auto-generated method stub
        return vehicleListRepository.countVehicles();
    }

    @Transactional
    public String editVehicle(String vehicleId, @ModelAttribute("vehicle") VehicleListModel vehicle){
        Optional<VehicleList> optionalVehicle =  this.vehicleListRepository.findById(Long.valueOf(vehicleId));
        if(optionalVehicle.isPresent()){
            VehicleList vehicle1 = optionalVehicle.get();
            vehicle1.setCapacity(vehicle.getCapacity());
           /* vehicle1.setPhotos(vehicle.getPhotos());*/
            vehicle1.setLicensePlate(vehicle.getLicensePlate());
            vehicle1.setDriver(vehicle.getDriver());
            vehicle1.setManufacturer(vehicle.getManufacturer());
            vehicle1.setModel(vehicle.getModel());
            vehicle1.setWheel(vehicle.getWheel());
            vehicle1.setCurrentMileage(vehicle.getCurrentMileage());
            vehicle1.setFuelType(vehicle.getFuelType());
            this.vehicleListRepository.saveAndFlush(vehicle1);
        }
        return "editVehicles";
    }

    public VehicleListModel getVehicleById(String id){
        Optional<VehicleList> optionalVehicle =  this.vehicleListRepository.findById(Long.parseLong(id));
        if(optionalVehicle.isPresent()){
            VehicleList vehicle =   optionalVehicle.get();
            VehicleListModel vehicleListModel = new VehicleListModel();
            vehicleListModel.setId(vehicle.getId());
            vehicleListModel.setCapacity(vehicle.getCapacity());
            /*vehicleListModel.setPhotos(vehicle.getPhotos());*/
            vehicleListModel.setLicensePlate(vehicle.getLicensePlate());
            vehicleListModel.setDriver(vehicle.getDriver());
            vehicleListModel.setManufacturer(vehicle.getManufacturer());
            vehicleListModel.setModel(vehicle.getModel());
            vehicleListModel.setWheel(vehicle.getWheel());
            vehicleListModel.setCurrentMileage(vehicle.getCurrentMileage());
            vehicleListModel.setFuelType(vehicle.getFuelType());
            return  vehicleListModel;
        }
        return null;
    }
}
