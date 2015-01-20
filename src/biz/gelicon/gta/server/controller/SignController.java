package biz.gelicon.gta.server.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.data.WeeklySignature;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.repo.WeeklySignatureRepository;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.Base64;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/inner/sign")
public class SignController {

	@Inject
	private WeeklySignatureRepository weeklySignatureRepository;

	@Inject
	private TeamRepository teamRepository;
	
	@RequestMapping(value = "/put", method=RequestMethod.POST, produces = "application/json")
 	@ResponseBody
 	@Transactional
 	public Boolean put(Model ui, HttpServletRequest request,
 			Integer teamId, 
 			String date,
 			String signature,
 			String hash,
 			String data) {
		
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Date endWeek = formatter.parse(date);
/*			
			String dateStart = formatter.format(DateUtils.incDay(dtStart, -6)); 
			ByteArrayServletResponse response = new ByteArrayServletResponse();
			reportController.getR1(response, request, teamId, dateStart, date, null);
			
			byte[] dataRaw = response.toByteArray();
			
*/			
			//String verifyData = EncodingUtils.encodeURIComponent(new String(dataRaw));
			//verifyData = URLDecoder.decode(verifyData, "UTF-8");//StringEscapeUtils.unescape(verifyData);
			//verifyData = Base64.encode(verifyData.getBytes());
/*			
			PrintWriter pw = new PrintWriter(new File("C:\\temp\\temp.txt"));
			pw.print(Base64.encode(dataRaw));
			pw.close();
			
			new FileOutputStream("C:\\temp\\temp.pdf").write(dataRaw);
			System.out.println("client hash:"+hash);
*/			
			
			//byte[] hashBytes = Base64.decode(hash);
/*			
			MessageDigest md = MessageDigest.getInstance("SHA1");
		    //md.update(verifyData.getBytes("iso-8859-1"), 0, verifyData.length());
		    md.update(dataRaw);
		    byte[] sha1hash = md.digest();			
			System.out.println("server hash:"+Base64.encode(sha1hash));
*/		    

			// получаем сертификат и публичный ключ
			byte[] certBuff = UserService.getCurrentUser().getActiveSertificate();
			PublicKey publicKey = getPublicKey(certBuff);
/*			
			// подпись
			Signature sig1 = Signature.getInstance("SHA1withRSA");
			KeyStore ks = KeyStore.getInstance("pkcs12", "SunJSSE");
			ks.load(new FileInputStream("C:\\WORK\\GELICON\\prognoz\\lebedev.pfx"), "lebedev".toCharArray());
			PrivateKey key = (PrivateKey) ks.getKey(ks.aliases().nextElement(), "lebedev".toCharArray());
			sig1.initSign(key);
			sig1.update(verifyData.getBytes());
			byte[] example = sig1.sign();
			System.out.println("server signature:"+Base64.encode(example));
*/			
			
			
			// подпись из base64
			//System.out.println("client signature:"+signature);
			byte[] signatureBytes = Base64.decode(signature);
			
			// данные из base64
			byte[] rawData = Base64.decode(data);
			
			//byte[] signatureBytes = signature.getBytes();
			/*
			if(Security.getProvider("BC")==null)
				Security.addProvider(new BouncyCastleProvider());
			*/
			//org.bouncycastle.crypto.signers.RSADigestSigner
			
			//Инициирую проверку
			final Signature sig = Signature.getInstance("SHA1withRSA");
			//Signature sig = Signature.getInstance("GOST3411withGOST3410", "BC");
			sig.initVerify(publicKey);
			sig.update(rawData);
			
			
			// проверяем
			boolean result = sig.verify(signatureBytes);
			
			if(result) {
				WeeklySignature ws = new WeeklySignature();
				ws.setDtDay(endWeek);
				ws.setDtSignDay(new Date());
				ws.setData(rawData);
				ws.setHash(Base64.decode(hash));
				ws.setSignature(signatureBytes);
				ws.setUser(UserService.getCurrentUser());
				ws.setTeam(teamRepository.findOne(teamId));
				ws.setSertificate(certBuff);
				weeklySignatureRepository.save(ws);
			}
			return result;
		} catch (Exception e) {
			throw new SpringException(e.getMessage());
		}
	}

	private PublicKey getPublicKey() throws Exception, CertificateException, IOException {
		byte[] certBuff = UserService.getCurrentUser().getActiveSertificate();
		return getPublicKey(certBuff);
	}

	private X509Certificate getCertificate() throws Exception {
		byte[] certBuff = UserService.getCurrentUser().getActiveSertificate();
		if(certBuff == null || certBuff.length==0)
			throw new Exception(GtaSystem.getString("message.certneeded"));
			
		InputStream inStream = new ByteArrayInputStream(certBuff);
		X509Certificate cert = X509Certificate.getInstance(inStream);
		inStream.close();
		return cert;
	}
	
	private PublicKey getPublicKey(byte[] certBuff) throws Exception,
			CertificateException, IOException {
		if(certBuff == null || certBuff.length==0)
			throw new Exception(GtaSystem.getString("message.certneeded"));
			
		InputStream inStream = new ByteArrayInputStream(certBuff);
		X509Certificate cert = X509Certificate.getInstance(inStream);
		inStream.close();
		PublicKey publicKey = cert.getPublicKey();
		return publicKey;
	}
	
	
	public byte[] getStampPublicKey() throws Exception {
		int width = 202,height = 102;
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics gr = image.createGraphics();
		gr.setColor(new Color(255,255,255,0));
		gr.fillRect(0, 0, width, height);
		
		gr.setColor(Color.blue);
		gr.draw3DRect(0, 0, 200, 100, true);
		
		gr.setFont(new Font(gr.getFont().getName(), Font.BOLD, 26));
		drawCenralString(gr,"Подписано", 0, width,30);

		X509Certificate cert = getCertificate();
		gr.setFont(new Font(gr.getFont().getName(), Font.PLAIN, 10));
		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
	}
	
	private void drawCenralString(Graphics gr,String s, int left, int right, int top){
		int width = right-left;
        int stringLen = (int) gr.getFontMetrics().getStringBounds(s, gr).getWidth();
        int start = width/2 - stringLen/2;
        gr.drawString(s, start, top);
 }

}
