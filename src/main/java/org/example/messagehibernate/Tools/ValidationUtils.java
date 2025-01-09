package org.example.messagehibernate.Tools;


import org.example.messagehibernate.Boundries.Name;
import org.example.messagehibernate.Boundries.PeopleBoundary;
import org.example.messagehibernate.Entity.PeopleEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ValidationUtils {


    public static boolean isEmailFormat(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String e = email.replace(" ", "");
        String numeric = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return e.matches(numeric);
    }

    public static String toStringdateFormat(Date date) {
        DateFormat formatter =new  SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(date);
        return (formattedDate);

    }

    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(dateString.replace(" ", ""));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
public static  boolean NullOrEmptystr(String str)
{
    return str==null || str.length()==0||str.isEmpty();
}
    public static String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.parse(dateStr);
    }

    public static boolean stringToDateSecured(String dateStr, PeopleEntity pe)  {
       if(isValidDateFormat(dateStr))
       {
           try {
               pe.setBirth(stringToDate(dateStr));
               return  true;
           }catch (ParseException p)
           {
               return false;
           }
       }
       return false;
    }

    public static LocalDate fromStringToLocalDatte(String dateString) {
        LocalDate parsedDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the String into LocalDate object
        //when we call this function we guess string at format

        parsedDate = LocalDate.parse(dateString, formatter);

        return parsedDate;
    }

    public static boolean hasTwoUppercaseLetters(String input) {
        int uppercaseCount = 0;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isUpperCase(ch)) {
                uppercaseCount++;
                if (uppercaseCount > 2) {
                    return false;
                }
            }
        }

        return uppercaseCount == 2;
    }

    public static PeopleBoundary createRandomPerson() {
        Random random = new Random();

        String randomId = "ID" + random.nextInt(1000);

        // יצירת תאריך לידה רנדומלי (בתוך גבולות מסוימים)
        String randomBirth = "199" + random.nextInt(10) + "-" + (random.nextInt(12) + 1) + "-" + (random.nextInt(28) + 1);

        String[] firstNames = {"John", "Jane", "Max", "Alice", "Bob"};
        String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Brown"};
        Name randomName = new Name(firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);

        // יצירת אימייל רנדומלי
        String randomEmail = randomName.getFirst() + "." + randomName.getLast() + "@example.com";

        // יצירת אובייקט PeopleBoundary עם הערכים הרנדומליים
        return new PeopleBoundary(randomId, randomBirth, randomName, randomEmail);
    }
}