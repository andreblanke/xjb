package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class IntegerExpression extends Expression {

    public abstract int getValue();
}
