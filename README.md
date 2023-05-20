# ADempiere Template Project

Fill it with a comment about project.

## Requirements
- [JDK 11 or later](https://adoptium.net/)
- [Gradle 8.0.1 or later](https://gradle.org/install/)


### Packages Names
you should change the follows packages for your own implementation, just change the word `template` by your implementation

```Java
org.spin.template.model.validator
org.spin.template.setup
org.spin.template.util
```

### Model Validators
Change the `org.spin.template.model.validator.Validator` by your implementation, example: `org.spin.template.model.validator.MyOwnFunctionality`

### Model Deploy class
Change the `org.spin.template.setup.Deploy` by your implementation, example: `org.spin.template.setup.MyOwnSetupForDeploy`

### Model Util class for core changes
Change the `org.spin.template.util.Changes` by your implementation, example: `org.spin.template.util.MyOwnChanges`

## Binary Project

You can get all binaries from github [here](https://central.sonatype.com/artifact/io.github.adempiere/adempiere-template-project/1.0.0).

All contruction is from github actions


## Some XML's:

All dictionary changes are writing from XML and all XML's hare `xml/migration`


## How to add this library?

Is very easy.

- Gradle

```Java
implementation 'io.github.adempiere:adempiere-template-project:1.0.0'
```

- SBT

```
libraryDependencies += "io.github.adempiere" % "adempiere-template-project" % "1.0.0"
```

- Apache Maven

```
<dependency>
    <groupId>io.github.adempiere</groupId>
    <artifactId>adempiere-template-project</artifactId>
    <version>1.0.0</version>
</dependency>
```