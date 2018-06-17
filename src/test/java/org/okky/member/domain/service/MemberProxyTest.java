package org.okky.member.domain.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.s3.AmazonS3;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.member.TestMother;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PRIVATE)
public class MemberProxyTest extends TestMother {
    @InjectMocks
    MemberProxy proxy;
    @Mock
    AWSCognitoIdentityProvider cognitoClient;
    @Mock
    AmazonS3 s3Client;

    @Test
    public void signup() {
        SignUpRequest request = mock(SignUpRequest.class);
        SignUpResult result = mock(SignUpResult.class);
        when(result.getUserSub()).thenReturn("m-12345");
        when(cognitoClient.signUp(any())).thenReturn(result);

        String id = proxy.signup("coding8282@gmail.com", "password1234");

        assertEquals("id는 m-12345여야 한다.", id, "m-12345");

        InOrder o = inOrder(cognitoClient, result);
        o.verify(cognitoClient).signUp(any());
        o.verify(result).getUserSub();
    }

    @Test
    public void disable() {
        proxy.disable("coding8282@gmail.com");

        InOrder o = inOrder(cognitoClient);
        o.verify(cognitoClient).adminDisableUser(any());
    }

    @Test
    public void generateProfileImage() {
        proxy.generateProfileImage("m-1");

        InOrder o = inOrder(s3Client);
        o.verify(s3Client).copyObject(isNull(), startsWith("sample/"), isNull(), eq("m-1.jpg"));
    }

    @Test
    public void deleteProfile() {
        proxy.deleteProfile("m-1");

        InOrder o = inOrder(s3Client);
        o.verify(s3Client).deleteObject(any());
    }
}