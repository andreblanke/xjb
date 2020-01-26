# xjbgen

## Table of contents

* [Table of contents](#table-of-contents)
* [Project structure](#project-structure)
* [Usage](#usage)
* [Implementation notes](#implementation-notes)
    * [JAXB implementation](#jaxb-implementation)
* [License](#license)
* [See also](#see-also)

## Project structure

```text
xjbgen
 ├─ .idea/dictionaries/
 │   └─ andreblanke.xml
 └─ src/
     ├─ main/
     │   ├─ java
     │   │   └─ org/freedesktop/
     │   │       ├─ xjb/  # Contains static source code which is part of XJB
     │   │       └─ xjbgen/  # Generates the majority of XJB's source code
     │   └─ resources/
     │       ├─ org/freedesktop/xjbgen/xml/
     │       │   └─ jaxb.properties  # Specifies the JAXBContextFactory implementation
     │       └─ templates/
     │           └─ xjb-module.java.ftl  # FreeMarker template for generated XJB modules
     └─ test/
         ├─ java/
         │   ├─ org/freedesktop/xjb/  # Unit tests for the static XJB source code
         │   └─ org/freedesktop/xjbgen/  # Unit tests for the XJB code generator
         └─ resources/
             └─ mockito-extensions/
                 └─ org.mockito.plugins.MockMaker  # Instructs Mockito to allow mocking final classes
```

## Usage

## Implementation notes

### JAXB implementation

The project is not tightly coupled to Eclipse MOXy and a different JAXB implementation should be
usable by removing all MOXy-specific functionality, which, at the time of writing, only concerns
`@XmlValueExtension`.

Because `@XmlValueExtension` is only used internally for backwards compatibility with EclipseLink
2.5.0, the project does not depend on any vendor-specific extensions.

Additionally, the `jaxb.properties` file needs to be removed or adjusted when switching JAXB
implementations.

## License

This project is licensed under the Apache License, Version 2.0. For more information see [LICENSE](LICENSE).

## See also
