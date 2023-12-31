package net.maraichage.maraichagebackend.service;

import net.maraichage.maraichagebackend.entity.AccountsPayable;
import net.maraichage.maraichagebackend.repository.AccountsPayableRepository;
import net.maraichage.maraichagebackend.com.example.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor

public class AccountsPayableService {
    private final AccountsPayableRepository imageRepository;

    public String uploadImage(String glvoucherno, Date gldate, String glexpensetype,
                              String glpaidto, double glpaidamount,
                              MultipartFile imageFile) throws IOException {
        var imageToSave = AccountsPayable.builder()
                .glvoucherno(glvoucherno)
                .gldate(gldate)
                .glexpensetype(glexpensetype)
                .glpaidto(glpaidto)
                .glpaidamount(glpaidamount)
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .glimage(ImageUtils.compressImage(imageFile.getBytes()))
                .build();

        imageRepository.save(imageToSave);
        return "file uploaded successfully : " + imageFile.getOriginalFilename() + "  " + imageToSave;
    }
    public byte[] downloadImage(String glvoucherno) {
        Optional<AccountsPayable> dbImage = imageRepository.findById(glvoucherno);
        return dbImage.map(accountspayable -> {

            try {
/*
                System.out.println("from service id:" + accountspayable.getGlvoucherno());
                System.out.println("==============================");
                System.out.println("Date:" + accountspayable.getGldate());
                System.out.println("Name:" + accountspayable.getName());
                System.out.println("Amount:" + accountspayable.getGlpaidamount());
                System.out.println("Type:" + accountspayable.getType());

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println("Time"+ dateFormat.format(date));

                System.out.println("Imagedata:" + accountspayable.getGlimage());
*/              return ImageUtils.decompressImage(accountspayable.getGlimage());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception);
            }
        }).orElse(null);
    }
}
