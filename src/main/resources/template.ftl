<#ftl output_format="XML">
<users><#list users as user>
    <user>
        <username>${user.userName}</username>
        <firstname>${user.firstName}</firstname>
        <lastname>${user.lastName}</lastname>
        <email>${user.email}</email>
        <roles>
            <#list user.roles as role>
            <role>${role}</role>
            </#list>
        </roles>
    </user>
    </#list>
</users>
