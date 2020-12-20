# xjbgen

Disclaimer: This project is in a work-in-progress state and is currently not capable of generating a usable library
able to communicate with an X server.

## Table of contents

* [Table of contents](#table-of-contents)
* [Project structure](#project-structure)
* [Usage](#usage)
* [Implementation notes](#implementation-notes)
    * [JAXB implementation](#jaxb-implementation)
* [License](#license)
* [See also](#see-also)
    * [Similar projects](#similar-projects)
    * [Related projects](#related-projects)
    * [Relevant JDK Bug System entries](#relevant-jdk-bug-system-entries)
    * [Further reading](#further-reading)

## Project structure

```text
xjb
 ├─ .idea/dictionaries/
 │   └─ andreblanke.xml
 ├─ xcbproto/
 ├─ xjb/  # Contains static source code which is part of XJB
 │   ├─ src/
 │   │   ├─ main/java/org/freedesktop/xjb/
 │   │   └─ test/
 │   │       ├─ java/org/freedesktop/xjb/
 │   │       └─ resources/org/freedesktop/xjb/
 │   └─ pom.xml
 └─ xjbgen/
     ├─ src/
     │   ├─ main/
     │   │   ├─ java
     │   │   │   └─ org/freedesktop/xjbgen/  # Generates the majority of XJB's source code
     │   │   └─ resources/
     │   │       ├─ org/freedesktop/xjbgen/xml/
     │   │       │   └─ jaxb.properties  # Specifies the JAXBContextFactory implementation
     │   │       └─ templates/
     │   │           └─ xjb-module.java.ftl  # FreeMarker template for generated XJB modules
     │   └─ test/java/org/freedesktop/xjbgen/  # Unit tests for the XJB code generator
     └─ pom.xml
```

## Usage

Preparation is needed before you are able to generate XJB, as the static source code,
i.e. the code of XJB which is not generated from the XML protocol descriptions provided by xcbproto,
relies on JDK functionality which is not yet released to the public, namely support for Unix sockets.

This support is implemented in the `unixdomainchannels` branch of the
[JDK sandbox](https://hg.openjdk.java.net/jdk/sandbox/), which is currently based on an incomplete version of
Java 15.

For more information about the `unixdomainchannels` branch see
[this issue on the JDK bug system](https://bugs.openjdk.java.net/browse/JDK-8231358).

For instructions on how to build the JDK from source see
[this document](https://bugs.openjdk.java.net/browse/JDK-8231358).

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

### Similar projects

* [X C Binding](https://gitlab.freedesktop.org/xorg/lib/libxcb)
* [X Go Binding](https://github.com/BurntSushi/xgb)
* [X Emacs Lisp Binding](https://github.com/ch11ng/xelb)

### Related projects

* [libXau](https://gitlab.freedesktop.org/xorg/lib/libxau)

### Relevant JDK Bug System entries:

* [JDK-8169296: Need a way get local nodename](https://bugs.openjdk.java.net/browse/JDK-8169296)
* [JDK-8231358: Support UNIX domain socket channels](https://bugs.openjdk.java.net/browse/JDK-8231358)

### Further reading

* [X Window System Protocol](https://www.x.org/releases/X11R7.7/doc/xproto/x11protocol.pdf)
