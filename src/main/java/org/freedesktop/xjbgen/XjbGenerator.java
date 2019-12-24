package org.freedesktop.xjbgen;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.XjbModule;
import org.freedesktop.xjbgen.util.TopologicalOrderIterator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static freemarker.template.Configuration.VERSION_2_3_29;

public final class XjbGenerator {

    @NotNull
    private final Path xprotoXmlFileDirectory;

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

    public XjbGenerator(@NotNull final Path xprotoXmlFileDirectory) {
        this.xprotoXmlFileDirectory = xprotoXmlFileDirectory;
    }

    public static void main(@NotNull final String... args) throws Exception {
        new XjbGenerator(Paths.get(args[0])).generateXJavaBindings();
    }

    public void generateXJavaBindings() throws IOException {
        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.xml");
        final Map<String, XjbModule> registeredModules =
            Files
                .list(xprotoXmlFileDirectory)
                .filter(matcher::matches)
                .map(XjbGenerator::deserializeModule)
                .collect(toMap(XjbModule::getHeader, identity()));

        new TopologicalOrderIterator<>(registeredModules.values())
            .forEachRemaining(module -> {
            });
    }

    private static XjbModule deserializeModule(@NotNull final Path xmlProtocolDescriptionFile) {
        try {
            return
                (XjbModule) XJB_MODULE_CONTEXT
                    .createUnmarshaller()
                    .unmarshal(xmlProtocolDescriptionFile.toFile());
        } catch (final JAXBException exception) {
            throw new RuntimeException(exception);
        }
    }
}
