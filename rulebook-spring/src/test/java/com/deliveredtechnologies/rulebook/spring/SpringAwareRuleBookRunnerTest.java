package com.deliveredtechnologies.rulebook.spring;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.RuleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.deliveredtechnologies.rulebook.spring.SpringTestService.EXPECTED_RESULT;

@ContextConfiguration(classes = TestConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringAwareRuleBookRunnerTest {
  @Resource(name = "springAwareRuleBookRunner")
  private SpringAwareRuleBookRunner _ruleBook;

  @Resource(name = "errorOnFailure")
  private SpringAwareRuleBookRunner _errorOnFailureRuleBook;

  private NameValueReferableMap<String> _facts = new FactMap<>();

  @Before
  public void setUp() {
    _facts.setValue("value1", "Value");
    _facts.setValue("value2", "Value");
  }

  @Test
  public void ruleBookShouldRunRulesInPackage() {
    _ruleBook.run(_facts);
    Assert.assertEquals(EXPECTED_RESULT, _facts.getValue("value2"));
  }

  @Test
  public void ruleRunnerShouldReturnNullRuleClassIsInvalid() {
    Class clazz = Class.class;
    Assert.assertNull(_ruleBook.getRuleInstance(clazz));
  }

  @Test(expected = RuleException.class)
  public void springRulesSetToErrorOnFailureThrowExceptionsInRuleBook() {
    FactMap facts = new FactMap<>();
    facts.setValue("doThrowError", true);
    _errorOnFailureRuleBook.run(facts);
  }
}
