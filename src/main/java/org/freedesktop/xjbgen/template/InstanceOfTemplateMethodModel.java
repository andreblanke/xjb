package org.freedesktop.xjbgen.template;

import java.util.List;

import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import org.jetbrains.annotations.NotNull;

public class InstanceOfTemplateMethodModel implements TemplateMethodModelEx {

    @Override
    public @NotNull Boolean exec(@NotNull final List arguments) throws TemplateModelException {
        if (arguments.size() != 2)
            throw new TemplateModelException();

        final var clazz = ((WrapperTemplateModel) arguments.get(1)).getWrappedObject();
        if (!(clazz instanceof Class))
            throw new TemplateModelException();

        return ((Class<?>) clazz).isInstance(((WrapperTemplateModel) arguments.get(0)).getWrappedObject());
    }
}
