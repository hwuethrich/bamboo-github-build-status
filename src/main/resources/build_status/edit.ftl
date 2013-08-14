[@ui.bambooSection titleKey="githubBuildStatus.title"]
    [@ww.checkbox labelKey='githubBuildStatus.enable'
    name='custom.githubBuildStatus.enabled'
    toggle='true' /]

    [@ui.bambooSection dependsOn='custom.githubBuildStatus.enabled' showOn='true']
        [@ww.textfield labelKey='githubBuildStatus.githubOwner'
        name='custom.githubBuildStatus.githubOwner'
        required='true'
        /]

        [@ww.textfield labelKey='githubBuildStatus.githubRepo'
        name='custom.githubBuildStatus.githubRepo'
        required='true'
        /]

        [@ww.textfield labelKey='githubBuildStatus.githubToken'
        name='custom.githubBuildStatus.githubToken'
        required='true'
        /]
    [/@ui.bambooSection]
[/@ui.bambooSection]