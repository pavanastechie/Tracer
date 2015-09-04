/**
 * @author Jp
 *
 */
package com.tracer.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static com.tracer.common.Constants.SPAM_VALUE;

public class SpamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Cipher ecipher;
	int N = 500;
	protected transient final Log log = LogFactory.getLog(SpamServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.info("Starting of the doGet method");
		try {
			Graphics2D g2;
			Font ffont = new Font("Serif", Font.BOLD, 50);
			String randomText = getRandomText();
			BufferedImage buffer = new BufferedImage(200, 80, BufferedImage.BITMASK);
			g2 = buffer.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			FontRenderContext fc = g2.getFontRenderContext();
			@SuppressWarnings("unused")
			Rectangle2D bounds = ffont.getStringBounds(randomText, fc);
			g2.setColor(Color.BLACK);

			//PasswordEncy enc = PasswordEncy.getInstance();
			//String encrptyspam = enc.encrypt(randomText);

			Cookie ssocookie = new Cookie(SPAM_VALUE, randomText);
			// we want the cookie to be returned to any app below root
			ssocookie.setPath("/");
			// ssocookie.setMaxAge(10);
			
			// adding the cookie to the HttpResponse
			response.addCookie(ssocookie);

			String firststring = randomText.substring(0, 1);
			AffineTransform aft1 = AffineTransform.getRotateInstance(Math.PI * 2, N / 2, N / 2);
			g2.setTransform(aft1);
			g2.setFont(getDynamicFont());
			g2.drawString(firststring, 30, 50);

			String secondstring = randomText.substring(1, 2);
			AffineTransform aft2 = AffineTransform.getRotateInstance(-(Math.PI / 4), N / 2, N / 2);
			g2.setTransform(aft2);
			g2.setFont(getDynamicFont());
			g2.drawString(secondstring, 265, -16);// Drawing Second Sub String 

			String thridstring = randomText.substring(2, 3);
			AffineTransform aft3 = AffineTransform.getRotateInstance(Math.PI / 14, N / 2, N / 2);
			g2.setTransform(aft3);
			g2.setFont(getDynamicFont());
			g2.drawString(thridstring, 60, 90);// Drawing Third Sub String

			String fourthstring = randomText.substring(3, 4);
			AffineTransform aft4 = AffineTransform.getRotateInstance(Math.PI / 6, N / 2, N / 2);
			g2.setTransform(aft4);
			g2.setFont(getDynamicFont());
			g2.drawString(fourthstring, 45, 135);// Drawing Fourth Sub String

			String fifthstring = randomText.substring(4, 5);
			AffineTransform aft5 = AffineTransform.getRotateInstance(Math.PI / 12, N / 2, N / 2);
			g2.setTransform(aft5);
			g2.setFont(getDynamicFont());
			g2.drawString(fifthstring, 120, 77);// Drawing fifth Sub String

			// set the content type and get the output stream
			response.setContentType("image/png");
			OutputStream os = response.getOutputStream();

			// output the image as png
			ImageIO.write(buffer, "png", os);
			os.close();
		} catch (Exception ex) {
			log.error(ex);
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(N, N);
	}

	public String getRandomText() {
		String randomText = null;
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z"};
		int size = letters.length;
		int first = (int) (size * Math.random());

		StringBuffer sb = new StringBuffer(letters[first]);
		int second = (int) (size * Math.random());
		sb.append(letters[second]);

		int third = (int) (size * Math.random());
		sb.append(letters[third]);
		
		int fourth = (int) (size * Math.random());
		sb.append(letters[fourth]);
		
		int fifth = (int) (size * Math.random());
		sb.append(letters[fifth]);

		randomText = sb.toString();
		//log.info("whole string is " + randomText);// Total String 
		return randomText;
	}

	public Font getDynamicFont() {
		Font font1, font2, font3, font4, font5;
		Font font;
		int size = 23;
		font1 = new Font("Serif", Font.BOLD, size);
		font2 = new Font("SansSerif", Font.BOLD, size);
		font3 = new Font("Arial", Font.BOLD + Font.PLAIN, size);
		font4 = new Font("Verdana", Font.BOLD + Font.PLAIN, size);
		font5 = new Font("Minion", Font.BOLD + Font.ITALIC, size);
		int fontNum = (int) (5 * Math.random()) + 1;
		
		switch (fontNum) {
			case 1: font = font1;
					break;
			case 2: font = font2;
					break;
			case 3: font = font3;
					break;
			case 4: font = font4;
					break;
			default: font = font5;
		} // end switch
		return font;
	}
}
