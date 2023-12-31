package net.maraichage.maraichagebackend.controller;

import lombok.RequiredArgsConstructor;
import net.maraichage.maraichagebackend.entity.AccountsPayable;
import net.maraichage.maraichagebackend.repository.AccountsPayableRepository;
import net.maraichage.maraichagebackend.service.AccountsPayableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;


@CrossOrigin(origins = "*")

@RestController
    @RequestMapping("/AccountsPayable")
@RequiredArgsConstructor
public class AccountsPayableController {

    private final AccountsPayableService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam ("glvoucherno") String glvoucherno,
                                         @RequestParam ("gldate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date gldate,
                                         @RequestParam ("glexpensetype") String glexpensetype,
                                         @RequestParam ("glpaidto") String glpaidto,
                                         @RequestParam ("glpaidamount") double glpaidamount,
                                         @RequestParam("glimage") MultipartFile glimage) throws IOException {
        String uploadImage = imageService.uploadImage(glvoucherno, gldate, glexpensetype,
                glpaidto, glpaidamount, glimage);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }
    @Autowired
    private AccountsPayableRepository accountsPayableRepository;

    @GetMapping("/data")
    public List<AccountsPayable> getDataByDateRange(
            @RequestParam (name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam (name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
//        System.out.println("startDate: " + startDate);
//        System.out.println("endDate: " + endDate);
        return accountsPayableRepository.findBygldateBetween(startDate, endDate);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable String id, String name, String type, String imageData ) {
        byte[] image_Data = imageService.downloadImage(id);
/*        System.out.println("From Controller");
        System.out.println("===============");
        System.out.println("id:" + id);
        System.out.println("Name:" + name);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Time"+ dateFormat.format(date));
*/
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_JPEG_VALUE))
                .body(image_Data);
    }

}   
