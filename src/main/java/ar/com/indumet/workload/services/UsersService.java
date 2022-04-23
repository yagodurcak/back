package ar.com.indumet.workload.services;

import ar.com.indumet.workload.models.vos.UserDataVO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {

    @Autowired
    private SecretsService secretsService;

    @Value("${auth.secret.name}")
    private String secretName;

    @Value("${auth.secret.region}")
    private String secretRegion;

    @PostConstruct
    public void init() throws IOException {
        String secret = secretsService.getSecret(secretName, secretRegion);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(Charset.forName("UTF-16").encode(secret).array())))
                .setDatabaseUrl("https://indumet-sso.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public List<UserDataVO> findAll(){
        List<UserDataVO> users = new ArrayList<>();
        ListUsersPage page = null;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            return users;
        }
        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                users.add(new UserDataVO(user));
            }
            page = page.getNextPage();
        }

        return users;
    }

    public UserDataVO find(String uid) throws FirebaseAuthException {
        return new UserDataVO(FirebaseAuth.getInstance().getUser(uid));
    }

    public UserDataVO setClaims(String uid, Map<String, Object> claims) throws FirebaseAuthException {
        FirebaseAuth.getInstance().setCustomUserClaims(uid, claims);
        return new UserDataVO(FirebaseAuth.getInstance().getUser(uid));
    }
}