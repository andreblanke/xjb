<#-- @ftlvariable name="" type="org.freedesktop.xjbgen.xml.XjbModule" -->
<#-- @ftlvariable name="XjbAtomicType" type="java.lang.Class" -->
<#-- @ftlvariable name="XjbEnum" type="java.lang.Class" -->
<#-- @ftlvariable name="XjbFieldStructureContent" type="java.lang.Class" -->
<#-- @ftlvariable name="XjbPadStructureContent" type="java.lang.Class" -->
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
            final var buffer = java.nio.ByteBuffer.wrap(bytes);
            final var reply  = new ${request.reply};
            <#list request.reply.contents as content>

            <#if instance_of(content, XjbPadStructureContent)>
                <#if content?is_last>
            /* Skipping ${content.byteSize()} byte(s) of padding at end of buffer. */
                <#else>
            /* Skip ${content.byteSize()} byte(s) of padding. */
            buffer.position(buffer.position() + ${content.byteSize()});
                </#if>
            <#elseif instance_of(content, XjbFieldStructureContent)>
                <#if instance_of(content.srcType, XjbAtomicType)>
                    <#switch content.srcType.xmlName>
                        <#case "CARD8">
                        <#case "BYTE">
            ${content.srcName} = Byte.toUnsignedInt(buffer.get());
                            <#break/>
                        <#case "CARD16">
            ${content.srcName} = Short.toUnsignedInt(buffer.getShort())
                            <#break/>
                        <#case "CARD32">
            ${content.srcName} = Integer.toUnsignedLong(buffer.getInt());
                            <#break/>
                        <#case "CARD64">
            ${content.srcName} = buffer.getLong();
                            <#break/>
                        <#case "INT8">
            ${content.srcName} = buffer.get();
                            <#break/>
                        <#case "INT16">
            ${content.srcName} = buffer.getShort();
                            <#break/>
                        <#case "INT32">
            ${content.srcName} = buffer.getInt();
                            <#break/>
                        <#case "INT64">
            ${content.srcName} = buffer.getLong();
                            <#break/>
                        <#case "BOOL">
            ${content.srcName} = (buffer.get() == 1);
                            <#break/>
                        <#case "float">
            ${content.srcName} = buffer.getFloat();
                            <#break/>
                        <#case "double">
            ${content.srcName} = buffer.getDouble();
                            <#break/>
                        <#case "double">
            ${content.srcName} = buffer.get();
                            <#break/>
                        <#case "char">
            ${content.srcName} = (char) buffer.get();
                    </#switch>
                <#elseif instance_of(content.srcType, XjbEnum)>
            ${content.srcName} = ${content.srcType.srcName}.valueOf();
                </#if>
            buffer.position(buffer.position() + ${content.byteSize()});
            </#if>
            </#list>

            return reply;
        }
        <@generateComplexTypeGetters request.reply/>
    }
    </#if>
    </#list>
}
