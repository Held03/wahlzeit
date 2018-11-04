package org.wahlzeit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.wahlzeit.handlers.TellFriendTest;
import org.wahlzeit.model.AccessRightsTest;
import org.wahlzeit.model.CoordinateTest;
import org.wahlzeit.model.FlagReasonTest;
import org.wahlzeit.model.GenderTest;
import org.wahlzeit.model.GuestTest;
import org.wahlzeit.model.LocationTest;
import org.wahlzeit.model.PhotoFilterTest;
import org.wahlzeit.model.TagsTest;
import org.wahlzeit.model.UserStatusTest;
import org.wahlzeit.model.ValueTest;
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
	AccessRightsTest.class, //
	CoordinateTest.class, //
	FlagReasonTest.class, //
	GenderTest.class, //
	GuestTest.class, //
	LocationTest.class, //
	PhotoFilterTest.class, //
	TagsTest.class, //
	UserStatusTest.class, //
	ValueTest.class, //
	EmailAddressTest.class, //
	EmailServiceTests.class, //
	LogBuilderTest.class, //
	StringUtilTest.class, //
	VersionTest.class, //
})
public class AllTests {

}
