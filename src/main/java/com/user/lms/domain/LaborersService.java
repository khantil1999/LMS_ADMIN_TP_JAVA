package com.user.lms.domain;

import com.user.lms.entity.Labour;
import com.user.lms.entity.User;
import com.user.lms.models.LaborerModel;
import com.user.lms.models.LabourDetailsModel;
import com.user.lms.models.VehicleDetailsModel;
import com.user.lms.repository.LabourRepository;
import com.user.lms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LaborersService {

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void deleteUser(Long id) {
        this.labourRepository.deleteById(id);
    }


    @Transactional
    public LabourDetailsModel addLabour(LabourDetailsModel detailsModel, Principal principal) {
        User user = this.userRepository.findExistingUser(principal.getName());
        Labour labour = Labour.toEntity(detailsModel);
        labour.setUser(user);
        labour = this.labourRepository.saveAndFlush(labour);
        return LabourDetailsModel.fromEntity(labour);
    }

    @Transactional
    public LabourDetailsModel editLabour(String id, LabourDetailsModel detailsModel, Principal principal) {
        User user = this.userRepository.findExistingUser(principal.getName());
        Labour existingLabour = this.labourRepository.getReferenceById(Long.parseLong(id));

        Labour updateLabour = Labour.toEntity(detailsModel);
        updateLabour.setId(existingLabour.getId());
        updateLabour.setUser(user);
        updateLabour = this.labourRepository.saveAndFlush(updateLabour);
        return LabourDetailsModel.fromEntity(updateLabour);
    }

    public LabourDetailsModel getLabourById(String id) {
        Labour labour = this.labourRepository.getReferenceById(Long.parseLong(id));
        return LabourDetailsModel.fromEntity(labour);
    }
public List<LabourDetailsModel> getAllLabours(){
    return this.labourRepository.findAll().stream().map(LabourDetailsModel::fromEntity).collect(Collectors.toList());
}
    public List<LabourDetailsModel> getAllLabours(String truckProviderId){
        if(truckProviderId != null && !truckProviderId.isEmpty() && !truckProviderId.equals("0")){
           return this.labourRepository.getAllByTruckProvider(Long.parseLong(truckProviderId)).stream().map(LabourDetailsModel::fromEntity).collect(Collectors.toList());
        }
        return this.labourRepository.findAll().stream().map(LabourDetailsModel::fromEntity).collect(Collectors.toList());
    }

    /*public List<LabourDetailsModel> loadLabourersForAdmin(String truckProviderId, Principal principal) {
        if (truckProviderId != null && !truckProviderId.isEmpty() && !truckProviderId.equals("0")) {
            return this.labourRepository.getLabourersByTP(Long.parseLong(truckProviderId)).stream()
                    .map(LabourDetailsModel::fromEntity).collect(Collectors.toList());
        }
        return this.labourRepository.findAll().stream()
                .map(LabourDetailsModel::fromEntity).collect(Collectors.toList());


    }*/
}
