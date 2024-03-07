package com.user.lms.controller;

import com.user.lms.domain.AuthService;
import com.user.lms.domain.TruckProviderService;
import com.user.lms.entity.*;
import com.user.lms.models.*;
import com.user.lms.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TruckProviderController {

    @Autowired
    private TruckProviderService truckProviderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private TalukaRepository talukaRepository;


    @Autowired
    private TruckProviderAreaRepository truckProviderAreaRepository;

    @PostMapping("/updateApprovalStatus")
    public String updateApprovalStatus(@RequestParam Long userId, @RequestParam boolean isChecked) {
        try {
            this.truckProviderService.updateApprovalStatus(userId, isChecked);
            return "Update successful";
        } catch (Exception e) {
            return "Update failed: " + e.getMessage();
        }
    }

    @GetMapping("me")
    public UserDetailsModel getProfile(Principal principal){
        User user = this.userRepository.findByEmail(principal.getName(),true);
        UserDetailsModel userModel = new UserDetailsModel();
        userModel.setEmail(user.getEmail());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setId(user.getId());
        userModel.setMobileNo(user.getMobileNo());
        return userModel;
    }

    @PutMapping("/updateProfile")
    public String UpdateProfile(Principal principal,@RequestBody UserDetailsModel userDetailsModel){
        User user = this.userRepository.findByEmail(principal.getName(),true);
        user.setFirstName(userDetailsModel.getFirstName());
        user.setLastName(userDetailsModel.getLastName());
        user.setMobileNo(userDetailsModel.getMobileNo());
        this.userRepository.saveAndFlush(user);
        return "Updated";
    }

    @GetMapping("/states")
    public List<State> getAllState(Principal principal){
        return this.stateRepository.findAll();
    }

    @GetMapping("/districts")
    public  List<DistrictModel> getAllDistricts(){
        return this.districtRepository.findAll().stream().map(district -> {
            DistrictModel districtModel= new DistrictModel();
            districtModel.setId(district.getId());
            districtModel.setDistrictName(district.getName());
            return districtModel;
        }).collect(Collectors.toList());
    }
    @GetMapping("/talukas" )
    public  List<TalukaModel> getAllDistricts(@RequestParam(value = "districts",required = false)String districts){
        String[] ids =districts.split(",");
        return this.talukaRepository.loadTalukaByDistrict(ids).stream().map(taluka -> {
            TalukaModel talukaModel= new TalukaModel();
            talukaModel.setId(taluka.getId());
            String name = taluka.getPostOfficeName()+ "_" + taluka.getName() + "_"+ taluka.getPinCode();
            talukaModel.setTalukatName(name);
            return talukaModel;
        }).collect(Collectors.toList());
    }

    @PostMapping("/updateProviderArea")
    @Transactional
    public String updateLocations(@RequestBody List<String> talukaIds, Principal principal){
        User user = this.userRepository.findByEmail(principal.getName(),true);
        if(user != null){
            List<TruckProviderArea> areaList = this.truckProviderAreaRepository.getTruckProviderAreaById(user.getId());
            List<Long> existingIds = areaList.stream().map(truckProviderArea -> truckProviderArea.getTaluka().getId()).collect(Collectors.toList());
            List<Long> talukaIdLongs = talukaIds.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<Long> missingIds = talukaIdLongs.stream()
                    .filter(id -> !existingIds.contains(id))
                    .collect(Collectors.toList());
            List<Long> nonUseIds = existingIds.stream().filter(id -> !talukaIdLongs.contains(id))
                    .collect(Collectors.toList());
            List<Taluka> talukas = this.talukaRepository.findAllById(missingIds);
            List<TruckProviderArea> truckProviderAreas = talukas.stream()
                    .map(taluka -> {
                        TruckProviderArea truckProviderArea = new TruckProviderArea();
                        truckProviderArea.setTruckProvider(user);
                        truckProviderArea.setTaluka(taluka);
                        return truckProviderArea;
                    })
                    .collect(Collectors.toList());
            this.truckProviderAreaRepository.saveAll(truckProviderAreas);
            this.truckProviderAreaRepository.deleteTruckProviderAreaByTalukaAndTruckProvider(user.getId(), nonUseIds);
        }
        return "Done";
    }

    @GetMapping("/providerArea")
    TruckProviderAreaDetailsModel getProviderAreaList(Principal principal){
        User user = this.userRepository.findByEmail(principal.getName(),true);
        if(user != null) {
            List<TruckProviderArea> areaList = this.truckProviderAreaRepository.getTruckProviderAreaById(user.getId());
            List<TalukaModel> talukaModels = new ArrayList<>();
            List<DistrictModel> districtModels = new ArrayList<>();
            areaList.forEach(truckProviderArea -> {
                TalukaModel talukaModel = new TalukaModel();
                DistrictModel districtModel = new DistrictModel();
                talukaModel.setId(truckProviderArea.getTaluka().getId());
                talukaModel.setTalukatName(truckProviderArea.getTaluka().getName());
                districtModel.setId(truckProviderArea.getTaluka().getDistrict().getId());
                districtModel.setDistrictName(truckProviderArea.getTaluka().getDistrict().getName());
                talukaModels.add(talukaModel);
                districtModels.add(districtModel);
            });
            List<DistrictModel> finalList = districtModels.stream().distinct().collect(Collectors.toList());
            return new TruckProviderAreaDetailsModel(talukaModels,finalList);
        }
        return null;
    }

}
