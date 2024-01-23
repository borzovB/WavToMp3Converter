package org.example.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class WavToMp3Converter2 {

    public static void moveAndRenameFile(String sourcePath, String newFolderPathAndName) {
        try {
            // Create a File object for the source file
            File sourceFile = new File(sourcePath);

            // Split the new folder path and file name
            String[] parts = newFolderPathAndName.split("/");
            String newFolder = parts[0];
            String newFileName = parts[1];

            // Create a Path object for the new path and file name
            Path newPath = Paths.get(newFolder, newFileName);

            // Move and rename the file
            Files.move(sourceFile.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File successfully moved to " + newPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while moving the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\User\\Desktop\\Sound\\intFile\\1.wav";
        String outputFilePath = "C:\\Users\\User\\Desktop\\Sound\\intFile\\1.mp3";

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //if(convertWavToMp3(inputFilePath, outputFilePath))
                    convertWavToMp3(inputFilePath, outputFilePath);
                    System.out.println("Done");

            }
        });
        thread1.start();
        try {
            thread1.join();
        }catch(InterruptedException e) {
            deleteAllFiles("intFile/");
        }

        deleteAllFiles("C:/Users/User/Desktop/Sound/temporary_store_audio");


        //moveAndRenameFile("temporary_store_audio/1.wav", "intFile/ггш3.wav");
    }

    private static void convertWavToMp3(String wavFilePath, String mp3FilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(wavFilePath));

            AudioFormat sourceFormat = audioInputStream.getFormat();
            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(),
                    sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);

            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(convertFormat, audioInputStream);

            // Настройка кодека для MP3
            AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    sourceFormat.getSampleRate(), 16, 1, 2, sourceFormat.getSampleRate(), false);

            AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(targetFormat, convertedStream);

            AudioSystem.write(mp3Stream, AudioFileFormat.Type.WAVE, new File(mp3FilePath));

            audioInputStream.close();
            convertedStream.close();
            mp3Stream.close();

            System.out.println("Конвертация завершена успешно");
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllFiles(String folderPath) {
        try {
            File folder = new File(folderPath);

            // Получаем список всех файлов в папке
            File[] files = folder.listFiles();

            if (files != null) {
                // Удаляем каждый файл
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
