package com.chewedmole.probation.pet.service;

import com.chewedmole.probation.pet.dto.RqCreatePet;
import com.chewedmole.probation.pet.dto.RqEditPetInfo;
import com.chewedmole.probation.pet.entity.PetEntity;
import com.chewedmole.probation.pet.repository.PetRepository;
import com.chewedmole.probation.pet.util.PasswordEncoderConfig;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Data
@Service
public class PetService {
    private final PetRepository petRepository;
    private final PasswordEncoderConfig passwordEncoder;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url1 = "http://localhost:8081/api/usr/validate-user-by-id/?id=";
    private final String url2 = "http://localhost:8081/api/usr/get-user-info/?id=";
    private final String url3 = "http://localhost:8081/api/usr/get-user-password/?id=";

    public ResponseEntity<?> createPet(RqCreatePet rq) {
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url1 + rq.getOwnerId(), Boolean.class);
        if (!response.getBody()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("This user does not exist!");
        }

        ResponseEntity<String> rsPassword = restTemplate.getForEntity(url3 + rq.getOwnerId(), String.class);
        if(!passwordEncoder.passwordEncoder().matches(rq.getPassword(), rsPassword.getBody())){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Id or password are incorrect!");
        }

        ResponseEntity<String> info = restTemplate.getForEntity(url2 + rq.getOwnerId(), String.class);

        PetEntity newPet = new PetEntity()
                .setName(rq.getName())
                .setType(rq.getType())
                .setOwnerId(rq.getOwnerId());

        petRepository.save(newPet);

        String finalResponse = String.format("%s named %s successfully created!\n" +
                                             "Now %s has a new pet.",
        rq.getType(), rq.getName(), info.getBody());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(finalResponse);
    }

    public ResponseEntity<?> editPetInfo(Long id, RqEditPetInfo rq){
        if(!petRepository.findById(id).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("There is no pet like this! Try to create some.");
        }

        ResponseEntity<Boolean> response = restTemplate.getForEntity(url1 + rq.getOwnerId(), Boolean.class);
        if (!response.getBody()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("This user does not exist!");
        }

        PetEntity pet = petRepository.findById(id).get()
                .setType(rq.getType())
                .setName(rq.getName())
                .setOwnerId(rq.getOwnerId());

        petRepository.save(pet);

        String finalResponse = String.format("Now %s is named %s!", rq.getType(), rq.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(finalResponse);
    }
}
