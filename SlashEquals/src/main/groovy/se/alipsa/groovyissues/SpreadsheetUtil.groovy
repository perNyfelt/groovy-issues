package se.alipsa.groovyissues

import groovy.transform.CompileStatic

@CompileStatic
class SpreadsheetUtil {

  static String asColumnName(int number) {
    StringBuilder sb = new StringBuilder()
    while (number-- > 0) {
      sb.append(('A' as char + (number % 26)) as char)
      number /= 26
    }
    return sb.reverse().toString()
  }
}
