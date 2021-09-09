package helpers;

import com.github.javafaker.Faker;
import models.Category;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class TestDataGenerator {
    public static String getRandomString(int length) {

        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static int getRandomNumber(){
        int min = 60;
        int max = 601;
        Random rand = new Random();
        int randomNumber = rand.nextInt(max) + min;
        return randomNumber;
    }
}