<#-- @ftlvariable name="" type="org.freedesktop.xjbgen.xml.Module" -->
<#macro generateComplexTypeFields complexType>
    <#list complexType.namedTypedContents as content>

        ${content}
    </#list>
</#macro>

<#macro generateDeserializationConstructors complexType>
        ${complexType.srcName}(final byte[] bytes) {
            this(java.nio.ByteBuffer.wrap(bytes));
        }

        ${complexType.srcName}(final java.nio.ByteBuffer buffer) {
        <#list complexType.contents as content>

            ${content.fromBytesSrc}
            <#if content.advanceBufferSrc?has_content>
            ${content.advanceBufferSrc}
            </#if>
        </#list>
        }
</#macro>

<#macro generateComplexTypeGetters complexType>
    <#list complexType.namedTypedContents as content>
        <#local getterSetterSuffix = content.srcName?cap_first/>

        public ${content.srcType.qualifiedSrcName} get${getterSetterSuffix}() {
            return ${content.srcName};
        }
    </#list>
</#macro>

<#macro generateComplexTypeBuilder complexType>
        public static final class Builder {
            <#list complexType.namedTypedContents as content>

            private ${content.srcType.qualifiedSrcName} ${content.srcName};
            </#list>

            public ${complexType.qualifiedSrcName} build() {
                <#local builtObjectName = complexType.srcName[0]?lower_case + complexType.srcName[1..]/>
                final var ${builtObjectName} = new ${complexType.qualifiedSrcName}();

                <#if complexType.namedTypedContents?has_content>
                <#list complexType.namedTypedContents as content>
                ${builtObjectName}.${content.srcName} = ${content.srcName};
                </#list>

                </#if>
                return ${builtObjectName};
            }
            <#list complexType.namedTypedContents as content>

            public Builder ${content.srcName}(final ${content.srcType.qualifiedSrcName} ${content.srcName}) {
                this.${content.srcName} = ${content.srcName};
                return this;
            }
            </#list>
        }
</#macro>
package org.freedesktop.xjb;

@javax.annotation.Generated("org.freedesktop.xjbgen.XjbGenerator")
public final class ${className} {

    /* Prevent instantiation. */
    private ${className}() {
    }
    <#if extension>

    public static void initialize() {
    }
    </#if>
    <#list structs as struct>

    public static final class ${struct.srcName} {
        <@generateComplexTypeFields struct/>
        <#if struct.namedTypedContents?has_content>

        private ${struct.srcName}() {
        }
        </#if>

        <@generateDeserializationConstructors struct/>
        <@generateComplexTypeGetters struct/>

        <@generateComplexTypeBuilder struct/>
    }
    </#list>
    <#list eventStructs as eventStruct>

    public static final class ${eventStruct.srcName} {
        <@generateComplexTypeFields eventStruct/>

        <@generateComplexTypeGetters eventStruct/>
    }
    </#list>
    <#list xidTypes as xidType>

    @java.lang.annotation.Target({
        java.lang.annotation.ElementType.ANNOTATION_TYPE,
        java.lang.annotation.ElementType.FIELD,
        java.lang.annotation.ElementType.LOCAL_VARIABLE,
        java.lang.annotation.ElementType.PARAMETER,
        java.lang.annotation.ElementType.TYPE_USE
    })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionType.RUNTIME)
    @java.lang.annotation.Documented
    public @interface ${xidType.srcName} {
    }
    </#list>
    <#list xidUnions as xidUnion>

    <#list xidUnion.types as type>
    @${type}
    </#list>
    @java.lang.annotation.Target({
        java.lang.annotation.ElementType.FIELD,
        java.lang.annotation.ElementType.LOCAL_VARIABLE,
        java.lang.annotation.ElementType.PARAMETER,
        java.lang.annotation.ElementType.TYPE_USE
    })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionType.RUNTIME)
    @java.lang.annotation.Documented
    public @interface ${xidUnion.srcName} {
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

        public static ${enum.srcName} valueOf(final int value) {
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

    public static final class ${request.srcName} {
        <@generateComplexTypeFields request/>

        public static final int OPCODE = ${request.opcode};
        <#if request.namedTypedContents?has_content>

        private ${request.srcName}() {
        }
        </#if>
        <@generateComplexTypeGetters request/>

        <@generateComplexTypeBuilder request/>
    }
    <#if request.reply??>

    public static final class ${request.reply.srcName} extends org.freedesktop.xjb.Request {
        <@generateComplexTypeFields request.reply/>

        private ${request.reply.srcName}() {
        }

        ${request.reply.srcName}(final byte[] bytes) {
            this(java.nio.ByteBuffer.wrap(bytes));
        }

        ${request.reply.srcName}(final java.nio.ByteBuffer buffer) {
            super(buffer);

        <#list request.reply.contents as content>
            ${content.fromBytesSrc}
            <#if content?is_first>
            sequenceNumber = Short.toUnsignedInt(buffer.getShort());
            replyLength    = Integer.toUnsignedLong(buffer.getInt());
            </#if>
        </#list>
        }
        <@generateComplexTypeGetters request.reply/>
    }
    </#if>
    </#list>
}
