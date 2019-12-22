<#-- @ftlvariable name="" type="org.freedesktop.xjbgen.dom.XjbModule" -->
package org.freedesktop.xjb;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class ${className} {

    private ${className}() {
    }
    <#list enums as enum>

    public enum ${enum.srcName} {

        <#list enum.items as item>
        ${item.srcName}(${item.expression})${item_has_next?then(",", ";")}
        </#list>

        private final int value;

        ${enum.srcName}(final int value) {
            this.value = value;
        }
    }
    </#list>
    <#list requests as request>

    public static final class ${request.srcName}Request implements Externalizable {

        private static final int OPCODE = ${request.opcode};
    }
    <#if request.reply??>

    public static final class ${request.srcName}Reply implements Externalizable {
    }
    </#if>
    </#list>
}
