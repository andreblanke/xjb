/**
 * @implNote Update the {@link javax.xml.bind.annotation.XmlElements#value()} arrays of annotated
 *           {@link org.freedesktop.xjbgen.xml.expr.Expression} fields when introducing a new {@code Expression} class
 *           to prevent {@link java.lang.NullPointerException}s from occurring when the expression is used inside a
 *           protocol description but not registered.
 */
package org.freedesktop.xjbgen.xml.expr;
