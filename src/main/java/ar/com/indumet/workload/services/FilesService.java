package ar.com.indumet.workload.services;

import ar.com.indumet.workload.exceptions.DocumentFormatException;
import ar.com.indumet.workload.models.vos.PictureVO;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class FilesService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.buffer-path}")
    private String bufferPath;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.s3.base-url}")
    private String s3BaseUrl;

    public String uploadDocument(String source, List<String> validFileFormats) throws IOException, DocumentFormatException {

        String []pieces = source.split("[:;,]");
        String applicationType = pieces[1];

        if(pieces.length != 4 || !pieces[0].equals("data") || !pieces[2].equals("base64"))
            return null;

        if(validFileFormats.indexOf(pieces[1]) == -1)
            return null;

        String extension = "";

        if(applicationType.equals("application/pdf"))
            extension = ".pdf";
        else
            throw new DocumentFormatException("Document format forbidden. Only PDF is available");


        byte[] bytes = Base64.decodeBase64( pieces[3] );

        String docName = String.valueOf(System.currentTimeMillis()) + extension;

        String filePathName = this.bufferPath + "/documents/" + docName;

        Files.write(Paths.get(filePathName), bytes);

        this.s3Client.putObject(
                bucketName,
                "documents/" + docName,
                new File(filePathName)
        );

        this.s3Client.putObject(
                new PutObjectRequest(bucketName, "documents/" + docName, new File(filePathName))
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return s3BaseUrl + bucketName + "/documents/" + docName;

    }

    public String uploadPlane(String source, List<String> validFileFormats) throws IOException {

        String []pieces = source.split("[:;,]");
        String applicationType = pieces[1];

        if(pieces.length != 4 || !pieces[0].equals("data") || !pieces[2].equals("base64"))
            return null;

        if(validFileFormats.indexOf(pieces[1]) == -1)
            return null;

        String extension = "";

        if(applicationType.equals("application/pdf"))
            extension = ".pdf";
        else
            extension = ".dwg";


        byte[] bytes = Base64.decodeBase64( pieces[3] );

        String docName = String.valueOf(System.currentTimeMillis()) + extension;

        String filePathName = this.bufferPath + "/documents/" + docName;

        Files.write(Paths.get(filePathName), bytes);

        this.s3Client.putObject(
                bucketName,
                "documents/" + docName,
                new File(filePathName)
        );

        this.s3Client.putObject(
                new PutObjectRequest(bucketName, "documents/" + docName, new File(filePathName))
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return s3BaseUrl + bucketName + "/documents/" + docName;

    }

    public PictureVO uploadPicture(String source, List<String> validFileFormats) throws IOException {
        String []pieces = source.split("[:;,]");
        String applicationType = pieces[1];

        if(pieces.length != 4 || !pieces[0].equals("data") || !pieces[2].equals("base64"))
            return null;

        if(validFileFormats.indexOf(pieces[1]) == -1)
            return null;

        String extension = "";

        if(applicationType.equals("image/jpeg") || applicationType.equals("image/jpg") || applicationType.equals("image/pjpg"))
            extension = ".jpg";
        else
            extension = ".png";


        byte[] bytes = Base64.decodeBase64( pieces[3]);

        String docName = String.valueOf(System.currentTimeMillis()) + extension;

        String filePathName = this.bufferPath + "/pictures/" + docName;

        Files.write(Paths.get(filePathName), bytes);

        this.s3Client.putObject(
                new PutObjectRequest(bucketName, "pictures/" + docName, new File(filePathName))
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        PictureVO pictureVO = new PictureVO();
        pictureVO.setRegular(s3BaseUrl + "/" + bucketName + "/pictures/" + docName);
        pictureVO.setHd(s3BaseUrl  + "/" + bucketName + "/pictures/" + docName);
        pictureVO.setThumbnail( s3BaseUrl  + "/" + bucketName + "/pictures/" + docName);
        pictureVO.setMain(false);

        return pictureVO;
    }
}