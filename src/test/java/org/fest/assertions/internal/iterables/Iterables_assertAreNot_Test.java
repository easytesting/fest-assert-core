/*
 * Created on Mar 5, 2012
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * 
 * Copyright @2012 the original author or authors.
 */
package org.fest.assertions.internal.iterables;

import static org.fest.assertions.error.ElementsShouldNotBe.elementsShouldNotBe;
import static org.fest.assertions.test.TestData.someInfo;
import static org.fest.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.fest.util.Collections.list;

import static org.mockito.Mockito.verify;

import org.junit.Test;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.internal.Iterables;
import org.fest.assertions.internal.IterablesWithConditionsBaseTest;

/**
 * Tests for <code>{@link Iterables#assertAreNot(AssertionInfo, Iterable, org.fest.assertions.core.Condition)}</code>
 * 
 * @author Nicolas François
 * @author Mikhail Mazursky
 * @author Joel Costigliola
 */
public class Iterables_assertAreNot_Test extends IterablesWithConditionsBaseTest {

  @Test
  public void should_pass_if_each_element_not_satisfies_condition() {
    actual = list("Solo", "Leia");
    iterables.assertAreNot(someInfo(), actual, jedi);
    verify(conditions).assertIsNotNull(jedi);
  }

  @Test
  public void should_throw_error_if_condition_is_null() {
    thrown.expectNullPointerException("The condition to evaluate should not be null");
    actual = list("Solo", "Leia");
    iterables.assertAreNot(someInfo(), actual, null);
    verify(conditions).assertIsNotNull(null);
  }

  @Test
  public void should_fail_if_condition_is_met() {
    testCondition.shouldMatch(false);
    AssertionInfo info = someInfo();
    try {
      actual = list("Solo", "Leia", "Yoda");
      iterables.assertAreNot(someInfo(), actual, jedi);
    } catch (AssertionError e) {
      verify(conditions).assertIsNotNull(jedi);
      verify(failures).failure(info, elementsShouldNotBe(actual, list("Yoda"), jedi));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
