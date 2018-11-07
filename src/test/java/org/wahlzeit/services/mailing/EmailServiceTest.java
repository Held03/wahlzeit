/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.wahlzeit.services.mailing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.EmailAddress;

public class EmailServiceTest {

	EmailService emailService = null;
	EmailAddress validAddress = null;
	EmailAddress validAddress2 = null;
	EmailAddress validAddress3 = null;

	@Before
	public void setup() throws Exception {
		emailService = EmailServiceManager.getDefaultService();
		validAddress = EmailAddress.getFromString("test@test.de");
		validAddress2 = EmailAddress.getFromString("test@fau.de");
		validAddress3 = EmailAddress.getFromString("fau@test.de");
	}

	@Test
	public void testSendInvalidEmail() {
		try {
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, "lol", "hi"));
			assertFalse(emailService.sendEmailIgnoreException(null, validAddress, null, "body"));
			assertFalse(emailService.sendEmailIgnoreException(validAddress, null, "hi", "       "));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}

	@Test
	public void testSendValidEmail() {
		try {
			assertTrue(emailService.sendEmailIgnoreException(validAddress, validAddress, "hi", "test"));
		} catch (Exception ex) {
			Assert.fail("Silent mode does not allow exceptions");
		}
	}
	
	@Test
	public void testAbstractEmailServiceSendEmailWithoutBCC() {
		AtomicBoolean composed = new AtomicBoolean(false);
		AtomicBoolean deliverd = new AtomicBoolean(false);
		
		String theSubject = "The subject";
		String theBody = "The email body";
		
		// Dummy message
		MimeMessage mm = new MimeMessage((Session) null);
		
		EmailService emailService = new AbstractEmailService() {

			@Override
			protected Message doCreateEmail(EmailAddress from, EmailAddress to, EmailAddress bcc, String subject,
					String body) throws MailingException {
				// Test arguments
				assertEquals(validAddress, from);
				assertEquals(validAddress2, to);
				assertEquals(EmailAddress.EMPTY, bcc);
				assertEquals(theSubject, subject);
				assertEquals(theBody, body);
				
				// Assert a single invocation
				assertFalse(composed.getAndSet(true));
				
				// Return dummy
				return mm;
			}

			@Override
			protected void doSendEmail(Message msg) throws MailingException {
				// Test argument
				assertEquals(mm, msg);
				
				// Assert single invocation
				assertFalse(deliverd.getAndSet(true));
			}
			
		};
		
		assertTrue(emailService.sendEmailIgnoreException(validAddress, validAddress2, theSubject, theBody));
		
		// Assert successful invocations
		assertTrue(composed.get());
		assertTrue(deliverd.get());
	}
	
	@Test
	public void testAbstractEmailServiceSendEmailWithBCC() {
		AtomicBoolean composed = new AtomicBoolean(false);
		AtomicBoolean deliverd = new AtomicBoolean(false);
		
		String theSubject = "The subject";
		String theBody = "The email body";
		
		MimeMessage mm = new MimeMessage((Session) null);
		
		EmailService emailService = new AbstractEmailService() {

			@Override
			protected Message doCreateEmail(EmailAddress from, EmailAddress to, EmailAddress bcc, String subject,
					String body) throws MailingException {
				// Test arguments
				assertEquals(validAddress, from);
				assertEquals(validAddress2, to);
				assertEquals(validAddress3, bcc);
				assertEquals(theSubject, subject);
				assertEquals(theBody, body);
				
				// Assert a single invocation
				assertFalse(composed.getAndSet(true));
				
				// Return dummy
				return mm;
			}

			@Override
			protected void doSendEmail(Message msg) throws MailingException {
				// Test argument
				assertEquals(mm, msg);
				
				// Assert single invocation
				assertFalse(deliverd.getAndSet(true));
			}
			
		};
		
		assertTrue(emailService.sendEmailIgnoreException(validAddress, validAddress2, validAddress3, theSubject, theBody));
		
		// Assert successful invocations
		assertTrue(composed.get());
		assertTrue(deliverd.get());
	}
}