package se.groupfish;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.groupfish.testrestcasemanagement.testresourse.IssueResourseMock;
import se.groupfish.testrestcasemanagement.testresourse.TeamResourseMock;
import se.groupfish.testrestcasemanagement.testresourse.UserResourseMock;
import se.groupfish.testrestcasemanagement.testresourse.WorkItemResourseMock;

@RunWith(Suite.class)
@SuiteClasses({ UserResourseMock.class, TeamResourseMock.class, WorkItemResourseMock.class, IssueResourseMock.class })
public class RestCaseManagementApplicationTests {

}
