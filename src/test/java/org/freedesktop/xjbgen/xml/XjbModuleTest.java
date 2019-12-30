package org.freedesktop.xjbgen.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.freedesktop.xjbgen.xml.expr.XjbIntegerExpression;
import org.freedesktop.xjbgen.xml.expr.XjbIntegerExpression.*;

class XjbModuleTest {

    @ValueSource(strings = {
        "/bigreq.xml",
        "/composite.xml",
        "/damage.xml",
        "/dpms.xml",
        "/dri2.xml",
        "/dri3.xml",
        "/ge.xml",
        "/glx.xml",
        "/present.xml",
        "/randr.xml",
        "/record.xml",
        "/render.xml",
        "/res.xml",
        "/screensaver.xml",
        "/shape.xml",
        "/shm.xml",
        "/sync.xml",
        "/xc_misc.xml",
        "/xevie.xml",
        "/xf86dri.xml",
        "/xf86vidmode.xml",
        "/xfixes.xml",
        "/xinerama.xml",
        "/xinput.xml",
        "/xkb.xml",
        "/xprint.xml",
        "/xproto.xml",
        "/xselinux.xml",
        "/xtest.xml",
        "/xv.xml",
        "/xvmc.xml"
    })
    @ParameterizedTest
    void testDeserialization(final String xprotoXmlFileLocation) throws Exception {
        final Unmarshaller unmarshaller =
            JAXBContext
                .newInstance(XjbModule.class, XjbIntegerExpression.class, XjbBitExpression.class, XjbValueExpression.class)
                .createUnmarshaller();

        unmarshaller
            .unmarshal(XjbModuleTest.class.getResource(xprotoXmlFileLocation));
    }
}
