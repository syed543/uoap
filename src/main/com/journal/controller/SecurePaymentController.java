/**
 * SecurePaymentController.java
 * Created Date : 11 May 2020
 */
package com.journal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.journal.dao.UserInvoiceJDBCTemplate;
import com.journal.model.UserInvoice;
import com.journal.requests.PaymentRequest;
import com.journal.utils.Encryptor;
import com.journal.utils.JournalConstants;

/**
 * @author imran
 *
 */
@Controller
public class SecurePaymentController {

	@Autowired
	private UserInvoiceJDBCTemplate userInvoiceJDBCTemplate;

	/**
	 * This method generates the securePayment URL
	 * 
	 * @author imran
	 * @param userId
	 * @param money
	 * @return String
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/generatesecureurl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateSecurePaymentUrl(
			@RequestBody PaymentRequest paymentRequest)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		// local variables
		StringBuilder tokenInfo = new StringBuilder();
		StringBuilder urlInfo = null;
		String token = null;
		String generatedUrl = "";
		Map<String, Object> result = new HashMap<String, Object>();

		String invoice = generateInvoiceNumber(
				JournalConstants.INVOICE_NUMBER_LENGTH);

		tokenInfo.append(paymentRequest.getUserId()).append("@").append(invoice)
				.append("@").append(paymentRequest.getJournalName()).append("@")
				.append(paymentRequest.getAuthorName()).append("@")
				.append(paymentRequest.getCurrencyCode()).append("@")
				.append(paymentRequest.getAmount());

		token = Encryptor.getEncodedEncrytedString(tokenInfo.toString());

		// insert invoice record into db
		saveUserInvoice(paymentRequest, invoice);
		
		token = URLEncoder.encode(token, "UTF-8");
		
		if (null != token) {
			urlInfo = new StringBuilder();
			generatedUrl = urlInfo.append(JournalConstants.SECURE_HTTP)
					.append(JournalConstants.HOST_NAME).append("/")
					.append(JournalConstants.CONTEXT).append("/")
					.append("router/verifytoken?token=").append(token)
					.toString();
		}		

		result.put("statusCode", "200");
		result.put("data", generatedUrl);

		return result;
	}

	/**
	 * 
	 * @author imran
	 * @param token
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException       String
	 */
	@RequestMapping(value = "/verifytoken", method = RequestMethod.GET)
	public ModelAndView verifyPaymentUrl(@RequestParam String token)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		ModelAndView view = new ModelAndView();

		view.setViewName("notValidUrl");

		if (token == null) {
			return view;
		}

		String dycryptedToken = Encryptor.getDecodedDecrytedString(token);

		String[] tokenValues = dycryptedToken.split("@");
		
		UserInvoice userInvoice = userInvoiceJDBCTemplate
				.getUserInvoiceById(tokenValues[1]);
		
		if (userInvoice == null) {
			return view;
		}
		
//		if (isExpired(userInvoice.getCreationDate(),
//				JournalConstants.TOKEN_EXPIRY,
//				JournalConstants.EXPIRY_IN_DAYS)) {
//
//			view.setViewName("expired");
//			return view;
//		}

		if (JournalConstants.PAYMENT_SUCCESS
				.equalsIgnoreCase(userInvoice.getPaymentStatus())) {
			view.setViewName("alreadySubmitted");
			return view;
		}

		String status = validateToken(tokenValues, userInvoice);

		if ("OK".equals(status)) {
			view.setViewName("invoice");
			view.addObject(userInvoice);
		}

		return view;
	}

	/**
	 * 
	 * @author imran
	 * @param paymentRequest void
	 */
	@RequestMapping(value = "/updatePayment", method = RequestMethod.POST)
	@ResponseBody
	public void updatePaymentStatus(
			@RequestBody PaymentRequest paymentRequest) {

		UserInvoice userInvoice = userInvoiceJDBCTemplate
				.getUserInvoiceById(paymentRequest.getInvoiceNumber());

		userInvoice.setPaymentStatus(paymentRequest.getPaymentStatus());
		userInvoice.setTransactionId(paymentRequest.getTransactionId());

		userInvoiceJDBCTemplate.updatePaymentStatus(userInvoice);
	}

	/**
	 * 
	 * @author imran
	 * @param paymentRequest void
	 */
	private void saveUserInvoice(PaymentRequest paymentRequest,
			String invoiceNumber) {
		UserInvoice userInvoice = new UserInvoice();

		userInvoice.setAmount(paymentRequest.getAmount());
		userInvoice.setArticleName((paymentRequest.getArticleName()));
		userInvoice.setAuthorName(paymentRequest.getAuthorName());
		userInvoice.setUserId(paymentRequest.getUserId());
		userInvoice.setCurrencyCode(paymentRequest.getCurrencyCode());
		userInvoice.setInvoiceNumber(invoiceNumber);
		userInvoice.setJournalName(paymentRequest.getJournalName());
		userInvoice.setArticleNumber(paymentRequest.getArticleNumber());
		userInvoice.setAuthorEmailId(paymentRequest.getAuthorEmailId());
		userInvoice.setCreationDate(new Date());

		userInvoiceJDBCTemplate.saveUserInvoice(userInvoice);
	}

	/**
	 * 
	 * @author imran
	 * @param count
	 * @return String
	 */
	private String generateInvoiceNumber(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random()
					* JournalConstants.ALPHA_NUMERIC_STRING.length());
			builder.append(
					JournalConstants.ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * 
	 * @author imran
	 * @param creationDate
	 * @param interval
	 * @param type
	 * @return boolean
	 */
	private boolean isExpired(Date creationDate, int interval, String type) {
		// Local variable
		Period period = null;
		Period expiryPeriod = null;

		if (creationDate == null) {
			return true;
		}

		switch (type) {

		case "DAYS":

			period = new Period(creationDate.getTime(), new Date().getTime(),
					PeriodType.days());

			expiryPeriod = new Period().withDays(interval);

			if (period.getDays() > expiryPeriod.getDays()) {

				return true;

			} // if{}

			break;

		case "HOURS":

			period = new Period(creationDate.getTime(), new Date().getTime(),
					PeriodType.hours());

			expiryPeriod = new Period().withHours(interval);

			if (period.getHours() > expiryPeriod.getHours()) {

				return true;

			} // if{}

			break;

		case "MINUTES":

			period = new Period(creationDate.getTime(), new Date().getTime(),
					PeriodType.minutes());

			expiryPeriod = new Period().withMinutes(interval);

			if (period.getMinutes() > expiryPeriod.getMinutes()) {

				return true;

			} // if{}

			break;

		default:

			break;

		} // switch{}

		return false;
	}

	/**
	 * 
	 * @author imran
	 * @param tokenValues
	 * @param invoice
	 * @return String
	 */
	private String validateToken(String[] tokenValues, UserInvoice invoice) {

		if (tokenValues[0].equalsIgnoreCase(invoice.getUserId())
				&& tokenValues[1].equalsIgnoreCase(invoice.getInvoiceNumber())
				&& tokenValues[2].equalsIgnoreCase(invoice.getJournalName())
				&& tokenValues[3].equalsIgnoreCase(invoice.getAuthorName())
				&& tokenValues[4].equalsIgnoreCase(invoice.getCurrencyCode())
				&& tokenValues[5].equalsIgnoreCase(
						String.valueOf(invoice.getAmount()))) {
			return "OK";
		}

		return "NOTOK";
	}
}
