package patches.projects

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the root project
accordingly, and delete the patch script.
*/
changeProject(DslContext.projectId) {
    features {
        add {
            dockerRegistry {
                id = "PROJECT_EXT_3"
                name = "Docker Registry"
                url = "https://docker.io"
                userName = "mkameni"
                password = "credentialsJSON:ca3e46c7-7fcc-4e1b-a344-196d2e769641"
            }
        }
    }
}