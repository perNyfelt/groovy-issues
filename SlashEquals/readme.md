# Slash Equals Operator in Groovy 5 during static compilation

This is a minimal example to demonstrate that the slash equals operator (`/=`) does not work as expected in Groovy 5 when using static compilation.

When executing `./gradlew build` you will see that the test fails with:

```
> Task :compileGroovy
startup failed:
.../groovy-issues/SlashEquals/src/main/groovy/se/alipsa/groovyissues/SpreadsheetUtil.groovy: 12: [Static type checking] - Cannot assign value of type java.math.BigDecimal to variable of type int
 @ line 12, column 7.
         number /= 26
         ^

1 error
```