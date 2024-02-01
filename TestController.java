package com.example.hacks;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	
	@GetMapping("/match")
	public String work(Model model) {
		try {
            // Load two images for comparison
            BufferedImage image1 = ImageIO.read(new File("C:\\Users\\Dhrumil\\OneDrive\\Desktop\\img1.jpg"));
            BufferedImage image2 = ImageIO.read(new File("C:\\Users\\Dhrumil\\OneDrive\\Desktop\\m.jpg"));

            // Compare the images
            String match = compareImages(image1, image2);
            model.addAttribute("value", match);

        } catch (IOException e) {
            e.printStackTrace();
        }
		return "basic.html";
	}
	
	 private static String compareImages(BufferedImage img1, BufferedImage img2) {
	        byte[] pixels1 = ((DataBufferByte) img1.getRaster().getDataBuffer()).getData();
	        byte[] pixels2 = ((DataBufferByte) img2.getRaster().getDataBuffer()).getData();
	        HashMap<Byte, Long> map = new HashMap<>();
	        for(byte a : pixels2) fill(map, a);
	        int count = 0;
	        for (int i = 0; i < pixels1.length; i++) {
	            if(map.containsKey(pixels1[i])) {
	            	remove(map, pixels1[i]);
	            	count++;
	            }
	        }
	        return String.valueOf(((float)count / (pixels1.length)));
	    }
	    
	    public static void fill(HashMap<Byte, Long> map, Byte key) {
	    	if(map.containsKey(key)) map.put(key, map.get(key) + 1);
	    	else map.put(key, 1L);
	    }
	    
	    public static void remove(HashMap<Byte, Long> map, Byte key) {
	    	map.put(key, map.get(key) - 1);
	    	if(map.get(key) == 0) map.remove(key);
	    }

}
