package org.freedesktop.xjbgen;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.jetbrains.annotations.NotNull;

import org.reflections8.Reflections;
import org.reflections8.scanners.ResourcesScanner;

import org.freedesktop.xjbgen.dom.XjbModule;
import org.freedesktop.xjbgen.util.TopologicalOrderIterator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static freemarker.template.Configuration.VERSION_2_3_29;

public final class XjbGenerator {

    private static final JAXBContext XJB_MODULE_CONTEXT = createXjbModuleContext();
    private static JAXBContext createXjbModuleContext() {
        try {
            return JAXBContext.newInstance(XjbModule.class);
        } catch (final JAXBException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static final Template XJB_MODULE_TEMPLATE = createXjbModuleTemplate();
    private static Template createXjbModuleTemplate() {
        final Configuration configuration = new Configuration(VERSION_2_3_29);

        configuration.setClassForTemplateLoading(XjbGenerator.class, "/templates");
        try {
            return configuration.getTemplate("xjb-module.java.ftl");
        } catch (final IOException exception) {
            throw new UncheckedIOException(exception);
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
                .collect(toMap(XjbModule::getHeader, identity()));

        new TopologicalOrderIterator<>(registeredModules.values()).forEachRemaining(module -> {
            try {
                XJB_MODULE_TEMPLATE.process(module, new PrintWriter(System.out));
            } catch (final IOException exception) {
                throw new UncheckedIOException(exception);
            } catch (final TemplateException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    private static XjbModule deserializeModule(@NotNull final String xcbprotoResource) {
        try {
            final var unmarshaller = XJB_MODULE_CONTEXT.createUnmarshaller();

            return (XjbModule) unmarshaller.unmarshal(XjbGenerator.class.getClassLoader().getResource(xcbprotoResource));
        } catch (final JAXBException exception) {
            throw new RuntimeException(exception);
        }
    }
}
