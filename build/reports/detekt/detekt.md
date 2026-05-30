# detekt

## Metrics

* 39 number of properties

* 33 number of functions

* 15 number of classes

* 2 number of packages

* 15 number of kt files

## Complexity Report

* 480 lines of code (loc)

* 398 source lines of code (sloc)

* 254 logical lines of code (lloc)

* 4 comment lines of code (cloc)

* 39 cyclomatic complexity (mcc)

* 6 cognitive complexity

* 1 number of total code smells

* 1% comment source ratio

* 153 mcc per 1,000 lloc

* 3 code smells per 1,000 lloc

## Findings (1)

### performance, SpreadOperator (1)

In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.

[Documentation](https://detekt.dev/docs/rules/performance#spreadoperator)

* /Users/sysout/codesys/projects/olympus-gate/src/main/kotlin/com/olympusgate/OlympusGateApplication.kt:10:43
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
7  class OlympusGateApplication
8  
9  fun main(args: Array<String>) {
10     runApplication<OlympusGateApplication>(*args)
!!                                           ^ error
11 }
12 

```

generated with [detekt version 1.23.6](https://detekt.dev/) on 2026-05-30 20:22:34 UTC
