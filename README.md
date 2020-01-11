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
