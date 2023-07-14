package com.chewedmole.probation.service;

import com.chewedmole.probation.dto.RqChangeName;
import com.chewedmole.probation.dto.RqCreateUser;
import com.chewedmole.probation.entity.UserEntity;
import com.chewedmole.probation.repository.UserRepository;
import com.chewedmole.probation.util.PasswordEncoderConfig;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;

    public ResponseEntity<?> createUser(RqCreateUser rq){
        if(userRepository.findByEmail(rq.getEmail()).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Fail! Please, don't use this email!");
        }

        Pattern ptrnEmail = Pattern.compile("^(.+)@(\\S+).(\\S)$");
        Matcher emailMatch = ptrnEmail.matcher(rq.getEmail());
        if(!emailMatch.find()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Your email is incorrect!");
        }

        Pattern ptrnPhone = Pattern.compile("\\+(\\d){6,}");
        Matcher phoneMatch = ptrnPhone.matcher(rq.getPhoneNumber());
        if(!phoneMatch.find()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This isn't a real number!");
        }

        UserEntity newUser = new UserEntity()
                .setName(rq.getName())
                .setSurname(rq.getSurname())
                .setPatronymic(rq.getPatronymic())
                .setEmail(rq.getEmail())
                .setPassword(passwordEncoder.passwordEncoder().encode(rq.getPassword()))
                .setPhoneNumber(rq.getPhoneNumber());

        userRepository.save(newUser);

        String finalResponse = String.format("Hello there, %s %s %s! \n" +
                        "Here is your contact information: \n" +
                        "%s\n" +
                        "%s",
                rq.getSurname(), rq.getName(), rq.getPatronymic(), rq.getEmail(), rq.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(finalResponse);
    }

    public ResponseEntity<?> findUserById(Long id){
        if(!userRepository.findById(id).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This user does not exist!");
        }

        UserEntity user = userRepository.findById(id).get();
        String finalResponse = String.format("ID: %s\n" +
                                             "%s %s $s\n" +
                                             "Password: %s\n" +
                                             "Contact info:\n" +
                                             "%s\n" +
                                             "%s\n",
        user.getId(), user.getSurname(), user.getName(), user.getPatronymic(), user.getEmail(), user.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(finalResponse);
    }

    public ResponseEntity<?> getAllUsers(){
        List<UserEntity> allUsers = userRepository.findAll();

        if(allUsers.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("We have no users!");
        }
        String finalResponse = "";

        for(UserEntity user : allUsers){
            finalResponse += String.format("-----------------------------\n" +
                                           "ID: %s\n" +
                                           "%s %s $s\n" +
                                           "Password: %s\n" +
                                           "Contact info:\n" +
                                           "%s\n" +
                                           "%s\n" +
                                           "-----------------------------\n",
            user.getId(), user.getSurname(), user.getName(), user.getPatronymic(), user.getEmail(), user.getPhoneNumber());
        }

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(finalResponse);
    }

    public ResponseEntity<?> deleteUser(String email, String password){
        if(!userRepository.findByEmail(email).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This user does not exist!");
        }
        if(!passwordEncoder.passwordEncoder().matches(password, userRepository.findByEmail(email).get().getPassword())){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Something is wrong! Try to check the entered information.");
        }
        String surname = userRepository.findByEmail(email).get().getSurname();
        String name = userRepository.findByEmail(email).get().getName();
        String patronymic = userRepository.findByEmail(email).get().getPatronymic();

        userRepository.delete(userRepository.findByEmail(email).get());

        String finalResponse = String.format("%s %s %s successfully deleted!",
        surname, name, patronymic);

        return ResponseEntity
                .status(HttpStatus.IM_USED)
                .body(finalResponse);
    }

    public ResponseEntity<?> changeName(String email, String password, RqChangeName rq){
        if(!userRepository.findByEmail(email).isPresent()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("This user does not exist!");
        }
        if(!passwordEncoder.passwordEncoder().matches(password, userRepository.findByEmail(email).get().getPassword())){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Something is wrong! Try to check the entered information.");
        }

        UserEntity user = userRepository.findByEmail(email).get()
                .setName(rq.getName())
                .setSurname(rq.getSurname())
                .setPatronymic(rq.getPatronymic());

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("User name info changed!");
    }
}
