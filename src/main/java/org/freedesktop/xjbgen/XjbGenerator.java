package org.freedesktop.xjbgen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.jetbrains.annotations.NotNull;

import org.reflections8.Reflections;
import org.reflections8.scanners.ResourcesScanner;

import org.freedesktop.xjbgen.util.TopologicalOrderIterator;
import org.freedesktop.xjbgen.xml.Module;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import static freemarker.template.Configuration.VERSION_2_3_29;

public final class XjbGenerator {

    private static final Template XJB_MODULE_TEMPLATE;

    private static final JAXBContext JAXB_XJB_MODULE_CONTEXT;

    private static final Schema XCB_SCHEMA;

    private static final Logger LOGGER = Logger.getLogger(XjbGenerator.class.getSimpleName());

    static {
        try {
            final var configuration = new Configuration(VERSION_2_3_29);
            configuration.setClassForTemplateLoading(XjbGenerator.class, "/templates");

            XJB_MODULE_TEMPLATE = configuration.getTemplate("xjb-module.java.ftl");

            JAXB_XJB_MODULE_CONTEXT =
                JAXBContext.newInstance(Module.class);
            XCB_SCHEMA =
                SchemaFactory
                    .newDefaultInstance()
                    .newSchema(XjbGenerator.class.getResource("/xcbproto/xcb.xsd"));
        } catch (final RuntimeException exception) {
            throw exception;
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void main(final String... args) throws IOException {
        new XjbGenerator().generateXJavaBindings(Path.of("generated-sources"));
    }

    public void generateXJavaBindings(final Path targetDirectory) throws IOException {
        final Map<String, Module> registeredModules =
            new Reflections("xcbproto", new ResourcesScanner())
                .getResources(Pattern.compile(".*\\.xml"))
                .stream()
                .map(XjbGenerator::deserializeModule)
                .collect(toUnmodifiableMap(Module::getHeader, identity()));

        if (Files.notExists(targetDirectory))
            Files.createDirectory(targetDirectory);

        new TopologicalOrderIterator<>(registeredModules.values()).forEachRemaining(module -> {
            try {
                LOGGER.info(String.format("Generating %1$s.java from %2$s.xml.", module.getClassName(), module.getHeader()));

                XJB_MODULE_TEMPLATE.process(module, new FileWriter(targetDirectory.resolveSibling(module.getClassName() + ".java").toFile()));
            } catch (final IOException exception) {
                throw new UncheckedIOException(exception);
            } catch (final TemplateException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private static Module deserializeModule(@NotNull final String xcbprotoResource) {
        try {
            final var unmarshaller = JAXB_XJB_MODULE_CONTEXT.createUnmarshaller();
            unmarshaller.setSchema(XCB_SCHEMA);

            return (Module) unmarshaller.unmarshal(XjbGenerator.class.getClassLoader().getResource(xcbprotoResource));
        } catch (final JAXBException exception) {
            throw new RuntimeException(exception);
        }
    }
}
