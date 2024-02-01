package com.example.hacks;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {
    public static void main(String[] args) {
        Tesseract tess = new Tesseract();

        try {
            tess.setDatapath("C:\\Users\\Dhrumil\\OneDrive\\Desktop\\Tess4J-3.4.8-src\\Tess4J\\tessdata");

            // Set the resolution (DPI)
            tess.setTessVariable("user_defined_dpi", "300");

            // Provide the path to the image file
            File imageFile = new File("C:\\Users\\Dhrumil\\OneDrive\\Desktop\\plo.jpg");

            // Perform OCR on the image
            String result = tess.doOCR(imageFile);

            // Output the result
            System.out.println("OCR Result:\n" + result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
