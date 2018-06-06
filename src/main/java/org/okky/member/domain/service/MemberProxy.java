package org.okky.member.domain.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminDisableUserRequest;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MemberProxy {
    @Value("${app.aws-user-pool-id}")
    private String userPoolId;
    @Value("${app.aws-cognito-client-id}")
    private String clientId;
    @Value("${app.aws-s3-bucket-name}")
    private String bucketName;
    private AWSCognitoIdentityProvider cognitoClient;
    private AmazonS3 s3Client;

    public MemberProxy(AWSCognitoIdentityProvider cognitoClient, AmazonS3 s3Client) {
        this.cognitoClient = cognitoClient;
        this.s3Client = s3Client;
    }

    /**
     * @return 가입한 회원의 아이디
     */
    public String signup(String email, String password) {
        SignUpRequest request = new SignUpRequest()
                .withClientId(clientId)
                .withUsername(email)
                .withPassword(password);
        SignUpResult result = cognitoClient.signUp(request);
        return result.getUserSub();
    }

    public void disable(String email) {
        AdminDisableUserRequest request = new AdminDisableUserRequest()
                .withUsername(email)
                .withUserPoolId(userPoolId);
        cognitoClient.adminDisableUser(request);
    }

    /**
     * 랜덤 이미지를 쁘사로 업로드한다.
     */
    public void uploadProfileImage(String memberId, File imageFile) {
        s3Client.putObject(bucketName, memberId + ".jpg", imageFile);
    }

    public void deleteProfile(String memberId) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, memberId + ".jpg");
        s3Client.deleteObject(request);
    }
}
