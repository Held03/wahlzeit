package org.wahlzeit.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.wahlzeit.services.mailing.EmailServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ EmailServiceTest.class })
public class EmailServiceTests {

}
