<#-- @ftlvariable name="" type="org.freedesktop.xjbgen.xml.XjbModule" -->
<#macro generateComplexTypeFields complexType>
    <#list complexType.namedTypedContents as content>

        private ${content.srcType} ${content.srcName};
    </#list>
</#macro>

<#macro generateComplexTypeGetters complexType>
    <#list complexType.namedTypedContents as content>
        <#local getterSetterSuffix = content.srcName?cap_first/>

        public ${content.srcType} get${getterSetterSuffix}() {
            return ${content.srcName};
        }
    </#list>
</#macro>

<#macro generateComplexTypeGettersAndSetters complexType>
    <#list complexType.namedTypedContents as content>
        <#local getterSetterSuffix = content.srcName?cap_first/>

        public ${content.srcType} get${getterSetterSuffix}() {
            return ${content.srcName};
        }

        public void set${getterSetterSuffix}(final ${content.srcType} ${content.srcName}) {
            this.${content.srcName} = ${content.srcName};
        }
    </#list>
</#macro>

<#macro generateComplexTypeBuilder complexType>
        public static final class Builder {
            <#list complexType.namedTypedContents as content>

            private ${content.srcType} ${content.srcName};
            </#list>

            public ${complexType.srcName} build() {
                <#local builtObjectName = complexType.srcName[0]?lower_case + complexType.srcName[1..]/>
                final var ${builtObjectName} = new ${complexType.srcName}();

                <#if complexType.namedTypedContents?has_content>
                <#list complexType.namedTypedContents as content>
                ${builtObjectName}.set${content.srcName?cap_first}(${content.srcName});
                </#list>

                </#if>
                return ${builtObjectName};
            }
            <#list complexType.namedTypedContents as content>

            public Builder ${content.srcName}(final ${content.srcType} ${content.srcName}) {
                this.${content.srcName} = ${content.srcName};
                return this;
            }
            </#list>
        }
</#macro>
package org.freedesktop.xjb;

public final class ${className} {

    private ${className}() {
    }
    <#if extension>

    public static void initialize() {
    }
    </#if>
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

    public static final class ${request} {
        <@generateComplexTypeFields request/>

        public static final int OPCODE = ${request.opcode};
        <@generateComplexTypeGettersAndSetters request/>

        <@generateComplexTypeBuilder request/>
    }
    <#if request.reply??>

    public static final class ${request.reply} {
        <@generateComplexTypeFields request.reply/>

        private ${request.reply}() {
        }

        static ${request.reply} fromBytes(final byte[] bytes) {
            int position = 0;
            final var reply = new ${request.reply};

            <#list request.reply.contents as content>
            </#list>

            return reply;
        }
        <@generateComplexTypeGetters request.reply/>
    }
    </#if>
    </#list>
}
