package com.wikifood.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.*;
import com.websystique.spring.service.*;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDate;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class foodList {

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		ArticleService service = (ArticleService) context.getBean("articleService");
		byte[] imageBytes;
		String base64;
		URL url;
		String str;
		/*
		 * List<Article> employees = service.findAllArticles(); for (Article emp
		 * : employees) { System.out.println(emp); }
		 */

		try {

			Object obj = parser.parse(new FileReader("D:/Profiles/malaoui/Documents/Projet/Wikidata/query.json"));

			JSONArray jsonObject = (JSONArray) obj;

			Iterator<JSONObject> iterator = jsonObject.iterator();
			JSONObject jsonArticle = new JSONObject();
			while (iterator.hasNext()) {
				Article article = new Article();
				jsonArticle = iterator.next();
				// System.out.println(jsonArticle.get("item"));
				// System.out.println((String) jsonArticle.get("itemLabelAr"));
				article.setItem("" + (String) jsonArticle.get("item"));
				article.setItemLabelFr("" + (String) jsonArticle.get("itemLabelFr"));
				article.setItemLabelEn("" + (String) jsonArticle.get("itemLabelEn"));
				article.setItemLabelEs("" + (String) jsonArticle.get("itemLabelEs"));
				article.setItemLabelAr("" + (String) jsonArticle.get("itemLabelAr"));
				// article.setImage(""+(String) jsonArticle.get("image"));
				if ((String) jsonArticle.get("image") != null) {
					str = ((String) jsonArticle.get("image")).replaceAll("http", "https");
					;
					url = new URL(str);
					imageBytes = IOUtils.toByteArray(url);
					//System.out.println(str);
					article.setImg(resizeImageAsJPG(imageBytes, 200));
				}
				service.saveArticle(article);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method takes in an image as a byte array (currently supports GIF,
	 * JPG, PNG and possibly other formats) and resizes it to have a width no
	 * greater than the pMaxWidth parameter in pixels. It converts the image to
	 * a standard quality JPG and returns the byte array of that JPG image.
	 * 
	 * @param pImageData
	 *            the image data.
	 * @param pMaxWidth
	 *            the max width in pixels, 0 means do not scale.
	 * @return the resized JPG image.
	 * @throws IOException
	 *             if the iamge could not be manipulated correctly.
	 */
	public static byte[] resizeImageAsJPG(byte[] pImageData, int pMaxWidth) throws IOException {
		// Create an ImageIcon from the image data
		ImageIcon imageIcon = new ImageIcon(pImageData);
		int width = imageIcon.getIconWidth();
		int height = imageIcon.getIconHeight();
		// If the image is larger than the max width, we need to resize it
		if (pMaxWidth > 0 && width > pMaxWidth) {
			// Determine the shrink ratio
			double ratio = (double) pMaxWidth / imageIcon.getIconWidth();
			height = (int) (imageIcon.getIconHeight() * ratio);
			width = pMaxWidth;
		}
		// Create a new empty image buffer to "draw" the resized image into
		BufferedImage bufferedResizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// Create a Graphics object to do the "drawing"
		Graphics2D g2d = bufferedResizedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		// Draw the resized image
		g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
		g2d.dispose();
		// Now our buffered image is ready
		// Encode it as a JPEG
		ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(encoderOutputStream);
		encoder.encode(bufferedResizedImage);
		byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
		return resizedImageByteArray;
	}

}
