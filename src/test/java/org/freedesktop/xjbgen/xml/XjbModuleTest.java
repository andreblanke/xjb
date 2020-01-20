package org.freedesktop.xjbgen.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.freedesktop.xjbgen.xml.expr.IntegerExpression;
import org.freedesktop.xjbgen.xml.expr.IntegerExpression.*;

public final class XjbModuleTest {

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
    public void testDeserialization(final String xprotoXmlFileLocation) throws Exception {
        final Unmarshaller unmarshaller =
            JAXBContext
                .newInstance(Module.class, IntegerExpression.class, BitExpression.class, ValueExpression.class)
                .createUnmarshaller();

        unmarshaller
            .unmarshal(XjbModuleTest.class.getResource(xprotoXmlFileLocation));
    }
}
