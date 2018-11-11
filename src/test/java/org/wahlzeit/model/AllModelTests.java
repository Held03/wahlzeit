package org.wahlzeit.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AccessRightsTest.class,
	CoordinateTest.class,
	FlagReasonTest.class,
	GenderTest.class,
	GuestTest.class,
	LocationTest.class,
	MandelbrotPhotoTest.class,
	MandelbrotPhotoFactoryTest.class,
	MandelbrotPhotoManagerTest.class,
	PhotoFilterTest.class,
	TagsTest.class,
	UserStatusTest.class,
	ValueTest.class })
public class AllModelTests {

}
