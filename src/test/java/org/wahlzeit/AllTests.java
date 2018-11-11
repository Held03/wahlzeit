package org.wahlzeit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.wahlzeit.handlers.TellFriendTest;
import org.wahlzeit.model.AllModelTests;
import org.wahlzeit.model.persistence.DatastoreAdapterTest;
import org.wahlzeit.services.EmailAddressTest;
import org.wahlzeit.services.EmailServiceTests;
import org.wahlzeit.services.LogBuilderTest;
import org.wahlzeit.utils.StringUtilTest;
import org.wahlzeit.utils.VersionTest;

@RunWith(Suite.class)
@SuiteClasses({ //
	TellFriendTest.class, //
	DatastoreAdapterTest.class, //
	AllModelTests.class, //
	EmailAddressTest.class, //
	EmailServiceTests.class, //
	LogBuilderTest.class, //
	StringUtilTest.class, //
	VersionTest.class, //
})
public class AllTests {

}
