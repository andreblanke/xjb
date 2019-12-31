<#-- @ftlvariable name="" type="org.freedesktop.xjbgen.xml.XjbModule" -->

<#macro generateComplexTypeFields complexType>
    <#list complexType.namedTypedContents as content>

        private ${content.srcType.srcName} ${content.srcName};
    </#list>
</#macro>

<#macro generateComplexTypeGettersAndSetters complexType>
    <#list complexType.namedTypedContents as content>
        <#local getterSetterSuffix = content.srcName?capitalize/>

        public ${content.srcType.srcName} get${getterSetterSuffix}() {
            return ${content.srcName};
        }

        public void set${getterSetterSuffix}(final ${content.srcType.srcName} ${content.srcName}) {
            this.${content.srcName} = ${content.srcName};
        }
    </#list>
</#macro>

<#macro generateComplexTypeBuilder complexType>
        public static final class Builder {
            <#list complexType.namedTypedContents as content>

            private ${content.srcType.srcName} ${content.srcName};
            </#list>

            public ${complexType.srcName} build() {
                <#local builtObjectName = complexType.srcName[0]?lower_case + complexType.srcName[1..]/>
                final var ${builtObjectName} = new ${complexType.srcName}();

                <#if complexType.namedTypedContents?has_content>
                <#list complexType.namedTypedContents as content>
                ${builtObjectName}.set${content.srcName?capitalize}(${content.srcName});
                </#list>

                </#if>
                return ${builtObjectName};
            }
            <#list complexType.namedTypedContents as content>

            public Builder ${content.srcName}(final ${content.srcType.srcName} ${content.srcName}) {
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
    <#list xidTypes as xidType>

    @java.lang.annotation.Target({
        java.lang.annotation.ElementType.FIELD,
        java.lang.annotation.ElementType.LOCAL_VARIABLE,
        java.lang.annotation.ElementType.PARAMETER })
    @java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
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

    public static final class ${request.srcName} {
        <@generateComplexTypeFields request/>

        public static final int OPCODE = ${request.opcode};

        <@generateComplexTypeGettersAndSetters request/>
        <@generateComplexTypeBuilder request/>
    }
    <#if request.reply??>

    public static final class ${request.reply.srcName} {
        <@generateComplexTypeFields request.reply/>

        @Deprecated
        public ${request.reply.srcName}() {
        }
        <@generateComplexTypeGettersAndSetters request.reply/>
    }
    </#if>
    </#list>
}
