<#-- @ftlvariable name="" type="org.freedesktop.xjbgen.xml.XjbModule" -->

<#macro generateComplexTypeFields complexType>
    <#list complexType.namedTypedContents as content>

        private ${content.srcType.srcName} ${content.srcName};
    </#list>
</#macro>

<#macro generateExternalizableImplementation complexType>
        @Override
        public void readExternal(final ObjectInput input) {
            <#list complexType.contents as content>
            </#list>
        }

        @Override
        public void writeExternal(final ObjectOutput output) {
            <#list complexType.contents as content>
            </#list>
        }
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

                <#list complexType.namedTypedContents as content>
                ${builtObjectName}.set${content.srcName?capitalize}(${content.srcName});
                </#list>

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

<#if xidTypes?has_content>
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
</#if>
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class ${className} {

    private ${className}() {
    }
    <#list xidTypes as xidType>

    @Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
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

    public static final class ${request.srcName} implements Externalizable {
        <@generateComplexTypeFields request/>

        public static final int OPCODE = ${request.opcode};

        @Deprecated
        public ${request.srcName}() {
        }

        <@generateExternalizableImplementation request/>
        <@generateComplexTypeGettersAndSetters request/>

        <@generateComplexTypeBuilder request/>
    }
    <#if request.reply??>

    public static final class ${request.srcName}Reply implements Externalizable {
        <@generateComplexTypeFields request.reply/>

        <@generateExternalizableImplementation request.reply/>
        <@generateComplexTypeGettersAndSetters request.reply/>
    }
    </#if>
    </#list>
}
