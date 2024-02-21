package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {

    public void getDetails(StringBuilder s, Profile profile) {
        String[] arr = s.toString().split(System.lineSeparator());
        String[] name = arr[0].split(": ");
        //name[1] = name[1].substring(0, name[1].length() - 1);
        profile.setName(name[1]);
        String[] age = arr[1].split(": ");
        //age[1] = age[1].substring(0, age[1].length() - 1);
        try {
            int x = Integer.parseInt(age[1]);
            profile.setAge(x);
        } catch ( NumberFormatException e) {
            e.printStackTrace();
        }
        String[] email = arr[2].split(": ");
        //email[1] = email[1].substring(0, email[1].length() - 1);
        profile.setEmail(email[1]);

        String[] phone = arr[3].split(": ");
        try {
          //  phone[1] = phone[1].substring(0, phone[1].length() - 1);
            profile.setPhone(Long.parseLong(phone[1]));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        try(RandomAccessFile aFile = new RandomAccessFile(file, "r");
            FileChannel inChannel = aFile.getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder sb = new StringBuilder();
            while(inChannel.read(buffer) > 0) {
                buffer.flip();
                for(int i = 0; i < buffer.limit(); i++) {
                    sb.append((char)buffer.get());
                }
            }
            getDetails(sb, profile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profile;

    }

//    public static void main(String[] args) {
//        File file = new File("Profile.txt");
//        FileReader fr = new FileReader();
//        Profile profile = fr.getDataFromFile(file);
//        System.out.println(profile);
//    }
}
