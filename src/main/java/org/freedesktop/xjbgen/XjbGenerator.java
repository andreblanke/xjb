package org.freedesktop.xjbgen;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
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
import org.freedesktop.xjbgen.xml.XjbModule;

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
                JAXBContext.newInstance(XjbModule.class);
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

    public static void main(final String... args) {
        new XjbGenerator().generateXJavaBindings();
    }

    public void generateXJavaBindings() {
        final Map<String, XjbModule> registeredModules =
            new Reflections("xcbproto", new ResourcesScanner())
                .getResources(Pattern.compile(".*\\.xml"))
                .stream()
                .map(XjbGenerator::deserializeModule)
                .collect(toUnmodifiableMap(XjbModule::getHeader, identity()));

        new TopologicalOrderIterator<>(registeredModules.values()).forEachRemaining(module -> {
            try {
                LOGGER.info("Generating %1$s.java from %2$s.xml.".formatted(module.getClassName(), module.getHeader()));

                XJB_MODULE_TEMPLATE.process(module, new FileWriter(module.getClassName() + ".java"));
            } catch (final IOException exception) {
                throw new UncheckedIOException(exception);
            } catch (final TemplateException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private static XjbModule deserializeModule(@NotNull final String xcbprotoResource) {
        try {
            final var unmarshaller = JAXB_XJB_MODULE_CONTEXT.createUnmarshaller();
            unmarshaller.setSchema(XCB_SCHEMA);

            return (XjbModule) unmarshaller.unmarshal(XjbGenerator.class.getClassLoader().getResource(xcbprotoResource));
        } catch (final JAXBException exception) {
            throw new RuntimeException(exception);
        }
    }
}
