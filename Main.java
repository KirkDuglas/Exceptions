package certif_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        try {
            makeRecord();
            System.out.println("Запись успешно выполнена");
        } catch (FileSystemException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + "Запись отменена");
        }

    }

    public static void makeRecord() throws Exception{
        System.out.println("Введите через пробел: фамилия имя отчество дата рождения (dd.mm.yyyy) номер телефона (5 цифр без разделителей)  пол(f или m)");

        String text;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in, "Cp866"))) {
            text = bf.readLine();
        }catch (IOException e){
            throw new Exception("Произошла ошибка при работе с консолью");
        }

        String[] array = text.split(" ");
        if (array.length < 6) {
            System.out.println("Ошибка: Вы что-то упустили! Введено меньше данных чем требуется!");
        }
        if (array.length > 6) {
                System.out.println("Ошибка: Вы ввели данных больше чем требуется");
            return;
        }

        String surname = array[0];
        String name = array[1];
        String patronymic = array[2];
        int phone;

        
        
       
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(array[3]);
        } catch (ParseException e) {
            System.out.println("Ошибка: Неверный формат даты. Введите дату рождения в формате dd.mm.yyyy");
            return;
        }
        
       
        try {
            phone = Integer.parseInt(array[4]);
            if (array[4].length() != 5)
            throw new Exception("Ошибка: Неверный формат телефона! Введите 5 цифр без разделителей");
        }
        catch (NumberFormatException e) {
            return;
        }
        

        String sex = array[5];
        if (!sex.equalsIgnoreCase("m") && !sex.equalsIgnoreCase("f")){
            System.out.println("Ошибка: Неверно введен пол. Введите пол латиницей f-женский или m- мужской");
            return;
        }

        String fileName = surname + ".txt";
        File dir = new File("certif_2\\files\\");
        File file = new File(dir, fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%n %s %s %s %s %s %s", surname, name, patronymic, format.format(birthdate), phone, sex));
        }catch (IOException e){
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }

    }
}