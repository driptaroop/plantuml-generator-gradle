# plantuml generator gradle plugin
![Build Status](https://github.com/driptaroop/plantuml-generator-gradle/workflows/build/badge.svg?branch=develop)

*Plantuml Generator gradle plugin* can be used to generate PlantUml class diagrams from existing java (or JVM languages such as Kotlin). This plugin is heavily inspired from the [**plantuml-generator-maven-plugin**](https://github.com/devlauer/plantuml-generator) project and heavily utilises the the [plantuml-generator](https://github.com/devlauer/plantuml-generator) to generate the class diagram.

## Sample Usage
This plugin registers a `generateUml` task.
Apply the plugin with either,
```kotlin
plugins {  
  id("org.dripto.gradle.plugin.plantuml.plantuml-generator") version "<version>"
}
```
or using the buildscript block,
```kotlin
buildscript {  
  repositories { jcenter() }  
  dependencies { 
	  classpath("org.dripto.gradle.plugin.plantuml:plugin:<version>")  
  }  
}
apply(plugin="org.dripto.gradle.plugin.plantuml.plantuml-generator")
```
task configuration example (with defaults),
```kotlin
tasks.withType<org.dripto.gradle.plugin.plantuml.task.PlantUmlGeneratorTask>{  
  destination="${buildDir}/reports/class-diagram.puml"  
  config {  
	  scanPackages = listOf("org.dripto.plugins")  
  }  
  asciiConfig {  
	  enableAsciidocWrapper = true  
	  asciidocDiagramImageFormat = "png"  
	  asciidocDiagramName = "ascii_diagram"  
	  asciidocDiagramBlockDelimiter = "----"  
  }  
}
```
For other options and defaults please check [plantuml-generator](https://github.com/devlauer/plantuml-generator).