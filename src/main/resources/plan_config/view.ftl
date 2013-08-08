[@ui.bambooSection titleKey="planConfig.title"]
    [@ww.checkbox labelKey='planConfig.enable'
    name='enabled'
    toggle='true' /]

    [@ui.bambooSection dependsOn='enabled' showOn='true']
        [@ww.textfield labelKey='planConfig.github.owner'
        name='github.owner'
        required='true'
        /]

        [@ww.textfield labelKey='planConfig.github.repo'
        name='github.repo'
        required='true'
        /]
    [/@ui.bambooSection]
[/@ui.bambooSection]