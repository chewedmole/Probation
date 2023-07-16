package com.chewedmole.probation.pet.api;

import com.chewedmole.probation.pet.dto.RqCreatePet;
import com.chewedmole.probation.pet.dto.RqEditPetInfo;
import com.chewedmole.probation.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pet/")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PutMapping("create-pet")
    public ResponseEntity<?> createPet(@RequestBody RqCreatePet rq){
        return petService.createPet(rq);
    }

    @PostMapping("edit-pet-info")
    public ResponseEntity<?> editPet(@RequestParam Long id, @RequestBody RqEditPetInfo rq){
        return petService.editPetInfo(id, rq);
    }
}
