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

@javax.annotation.Generated("xjbgen")
public final class ${className} {

    private ${className}() {
    }
    <#if extension>

    public static void initialize() {
    }
    </#if>
    <#list xidTypes as xidType>

    @java.lang.annotation.Target({
        java.lang.annotation.ElementType.FIELD,
        java.lang.annotation.ElementType.LOCAL_VARIABLE,
        java.lang.annotation.ElementType.PARAMETER
    })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionType.RUNTIME)
    @java.lang.annotation.Documented
    public @interface ${xidType.srcName} {
    }
    </#list>
    <#list enums as enum>

    public enum ${enum} {

        <#list enum.items as item>
        ${item.srcName}(${item.expression})${item_has_next?then(",", ";")}
        </#list>

        private final int value;

        ${enum}(final int value) {
            this.value = value;
        }

        public static ${enum} valueOf(final int value) {
            for (var item : values()) {
                if (item.getValue() == value)
                    return item;
            }
            return null;
        }

        public int getValue() {
            return value;
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
            final var buffer = java.nio.ByteBuffer.wrap(bytes);
            final var reply  = new ${request.reply};
            <#list request.reply.contents as content>

            ${content.fromBytesSrc}
            buffer.position(buffer.position() + ${content.byteSize()});
            </#list>

            return reply;
        }
        <@generateComplexTypeGetters request.reply/>
    }
    </#if>
    </#list>
}
