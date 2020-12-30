package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    expectSteps {
        script {
            name = "Set version using script"
            scriptContent = """
                #!/bin/bash
                HASH=%build.vcs.number%
                SHORT_HASH=${'$'}{HASH:0:7}
                BUILD_COUNTER=%build.counter%
                BUILD_NUMBER="1.0${'$'}BUILD_COUNTER.${'$'}SHORT_HASH"
                echo "##teamcity[buildNumber '${'$'}BUILD_NUMBER']"
            """.trimIndent()
        }
        script {
            name = "build"
            scriptContent = """
                mkdir bin
                echo "built artifact" > bin/compiled.txt
            """.trimIndent()
        }
    }
    steps {
        insert(2) {
            script {
                name = "Test CL"
                scriptContent = """echo "Allo Moise""""
            }
        }
    }
}
