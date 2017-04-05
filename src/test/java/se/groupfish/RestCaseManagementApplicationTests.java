package se.groupfish;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.groupfish.testrestcasemanagement.testresourse.UserResourseMock;


@RunWith(Suite.class)
@SuiteClasses({UserResourseMock.class})
public class RestCaseManagementApplicationTests {

}
