package tes.alipsa

import org.junit.jupiter.api.Test
import se.alipsa.groovyissues.SpreadsheetUtil

class SpreadsheetUtilTest {

  @Test
  void testAsColumnName() {
    assert SpreadsheetUtil.asColumnName(3) == 'C'
  }
}
