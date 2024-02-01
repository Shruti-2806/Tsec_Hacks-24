package com.example.hacks;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.io.*;
import javax.imageio.ImageIO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@Controller
public class BarcodeController {
	
	public String decodeBarcode(byte[] barcodeImage) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(barcodeImage));

        MultiFormatReader reader = new MultiFormatReader();
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        Result result = reader.decode(
                new BinaryBitmap(
                        new HybridBinarizer(
                                new BufferedImageLuminanceSource(bufferedImage)
                        )
                ),
                hints
        );

        return result.getText();
    }
	
	@PostMapping("/upload-barcode")
    public ResponseEntity<String> scanBarcode(@RequestPart("fileInput2") MultipartFile barcodeImage) {
        try {
            String barcodeValue = decodeBarcode(barcodeImage.getBytes());
            System.out.println(barcodeValue);
            fetch(barcodeValue);
            return ResponseEntity.ok(barcodeValue);
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error decoding barcode");
        }
    }
	
	public void fetch(String barcode) {
		String ans = "";
		try {
			URL url = new URL("https://world.openfoodfacts.net/api/v2/product/" + barcode + "?fields=product_name,nutriscore_data,nutriments,nutrition_grades");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			int code = connection.getResponseCode();
			System.out.println(code);
			if(code == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while(true) {
					String line = br.readLine();
					if(line != null) ans += line;
					else break;
				}
				int index = ans.indexOf("carbohydrates");
				int end = ans.indexOf(",", index);
				String value = ans.substring(index + 15, end);
				System.out.println("Carbohydrates : " + value);
				index = ans.indexOf("proteins");
				end = ans.indexOf(",", index);
				value = ans.substring(index + 9, end);
				System.out.println("Proteins : " + value);
				index = ans.indexOf("fat");
				end = ans.indexOf(",", index);
				value = ans.substring(index + 5, end);
				System.out.println("Fats : " + value);
				index = ans.indexOf("energy-kcal");
				end = ans.indexOf(",", index);
				value = ans.substring(index + 13, end);
				System.out.println("Calories : " + value);
			}
			connection.disconnect();
		}
		catch(Exception e) {}
	}
	
}
