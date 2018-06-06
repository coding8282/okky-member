package org.okky.member.util;

import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProfileImageUtil {
    public static File generageRandomProfileImage() {
        File dir = new File("image");
        File[] files = dir.listFiles();
        return files[new Random().nextInt(files.length)];
    }
}
