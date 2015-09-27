package servicefinder.data.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CategoryControllerTests.class, BusinessControllerTests.class, QuoteControllerTests.class, servicefinder.data.api.BusinessFinderTests.class})
public class ApiTestSuite {

}
