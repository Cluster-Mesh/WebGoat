/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.securepasswords;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.owasp.webgoat.container.assignments.AttackResult;

class SecurePasswordsAssignmentTest {

  private final SecurePasswordsAssignment assignment = new SecurePasswordsAssignment();

  @Test
  void shouldFailWhenPasswordIsNull() {
    AttackResult result = assignment.completed(null);

    assertThat(result.assignmentSolved()).isFalse();
    assertThat(result.getFeedback()).isEqualTo("securepassword-failed");
    assertThat(result.getOutput()).contains("Password must not be empty.");
  }

  @Test
  void shouldFailWhenPasswordIsBlank() {
    AttackResult result = assignment.completed("   ");

    assertThat(result.assignmentSolved()).isFalse();
    assertThat(result.getFeedback()).isEqualTo("securepassword-failed");
    assertThat(result.getOutput()).contains("Password must not be empty.");
  }

  @Test
  void shouldMaskPasswordInResponseOutput() {
    String password = "<script>alert(1)</script>VeryStrongPassword!123";
    AttackResult result = assignment.completed(password);

    assertThat(result.getOutput()).contains("<b>Your Password: *******</b>");
    assertThat(result.getOutput()).doesNotContain(password);
  }

  @Test
  void calculateTimeShouldReturnExpectedSecondsRemainder() {
    assertThat(SecurePasswordsAssignment.calculateTime(61))
        .isEqualTo("0 years 0 days 0 hours 1 minutes 1 seconds");
  }
}
