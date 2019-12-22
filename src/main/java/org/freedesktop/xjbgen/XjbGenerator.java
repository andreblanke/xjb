package org.freedesktop.xjbgen;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.XjbModule;

import static freemarker.template.Configuration.VERSION_2_3_29;

public final class XjbGenerator {

    private static final Configuration configuration = new Configuration(VERSION_2_3_29);

    static {
        configuration.setClassForTemplateLoading(XjbGenerator.class, "/templates");
    }

    public static void main(@NotNull final String... args) throws Exception {
        final Template xjbModuleTemplate = configuration.getTemplate("xjb-module.java.ftl");

        final Unmarshaller unmarshaller =
            JAXBContext
                .newInstance(XjbModule.class)
                .createUnmarshaller();

        final var dataModel = (XjbModule) unmarshaller.unmarshal(new File(args[0]));

        xjbModuleTemplate
            .process(dataModel, new PrintWriter(System.out));
    }
}
