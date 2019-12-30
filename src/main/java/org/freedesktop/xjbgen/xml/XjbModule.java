package org.freedesktop.xjbgen.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.type.XjbEnum;
import org.freedesktop.xjbgen.xml.type.XjbXidType;
import org.freedesktop.xjbgen.xml.type.complex.XjbRequest;
import org.freedesktop.xjbgen.util.PredecessorFunction;

import static java.util.stream.Collectors.toList;

/**
 * This class implements the {@link PredecessorFunction} interface to allow iteration of a collection of
 * {@code XjbModule} instance in topological order via the {@link org.freedesktop.xjbgen.util.TopologicalOrderIterator},
 * with the predecessors being the {@code XjbModule} objects this module depends on.
 */
@XmlRootElement(name = "xcb")
public final class XjbModule extends XjbElement<XjbElement<?>> implements PredecessorFunction<XjbModule> {

    @XmlAttribute(required = true)
    private String header;

    @XmlAttribute(name = "extension-name")
    private String extensionName;

    @XmlAttribute(name = "extension-xname")
    private String extensionXName;

    @XmlElement(name = "import")
    private List<XjbImport> imports = new ArrayList<>();

    @XmlElement(name = "xidtype")
    private List<XjbXidType> xidTypes = new ArrayList<>();

    @XmlElement(name = "enum")
    private List<XjbEnum> enums = new ArrayList<>();

    @XmlElement(name = "request")
    private List<XjbRequest> requests = new ArrayList<>();

    @Override
    public Collection<? extends XjbModule> predecessors() {
        return imports.stream()
            .map(XjbGenerationContext.getInstance()::lookupModule)
            .collect(toList());
    }

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        /* Every extension implicitly imports the XProto module. */
        if (isExtension())
            imports.add(XjbImport.XPROTO_IMPORT);
        XjbGenerationContext.getInstance().registerModule(this);
    }

    public String getClassName() {
        return isExtension() ? extensionName : "XProto";
    }

    public boolean isExtension() {
        return !header.equals("xproto");
    }

    // <editor-fold desc="XML getters">
    public String getHeader() {
        return header;
    }

    public List<XjbEnum> getEnums() {
        return enums;
    }

    public List<XjbRequest> getRequests() {
        return requests;
    }
    // </editor-fold>
}
