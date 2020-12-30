import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.2"

project {

    vcsRoot(HttpsGithubComMoise3helloWordNodeRefsHeadsMaster_2)
    vcsRoot(HttpsGithubComMoise3helloWordNodeRefsHeadsVsaasPack)

    buildType(Build)
    buildType(Build1)

    features {
        dockerRegistry {
            id = "PROJECT_EXT_2"
            name = "Docker Registry"
            url = "https://docker.io"
            userName = "mkameni"
            password = "credentialsJSON:1cf07e00-2dd9-40c9-a0b2-43179e5297ca"
        }
    }
}

object Build : BuildType({
    name = "Build"
    description = "Ceci est un test"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            name = "debug"
            scriptContent = """
                ls
                pwd
            """.trimIndent()
        }
        dockerCommand {
            name = "Build Image"
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "mkameni/hello-world:vdocker-saas"
                commandArgs = "--pull"
            }
        }
        dockerCommand {
            name = "Push image"
            commandType = push {
                namesAndTags = "mkameni/hello-world:vdocker-saas"
            }
        }
        maven {
            goals = "clean test"
            pomLocation = ".teamcity/pom.xml"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_2"
            }
        }
    }
})

object Build1 : BuildType({
    name = "Build Pack"

    vcs {
        root(HttpsGithubComMoise3helloWordNodeRefsHeadsMaster_2)
    }

    steps {
        script {
            name = "Installation de pack"
            scriptContent = "brew install buildpacks/tap/pack"
        }
        script {
            name = "Build Image"
            scriptContent = "pack build mkameni/helloworld:vsaas-pack --path src/. --builder cloudfoundry/cnb:bionic"
        }
        dockerCommand {
            name = "Push image"
            commandType = push {
                namesAndTags = "mkameni/helloworld:vsaas-pack"
            }
        }
        maven {
            goals = "clean test"
            pomLocation = ".teamcity/pom.xml"
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComMoise3helloWordNodeRefsHeadsMaster_2 : GitVcsRoot({
    name = "https://github.com/moise3/hello-word-node#refs/heads/master"
    url = "https://github.com/moise3/hello-word-node"
    branch = "refs/heads/master"
    authMethod = password {
        userName = "moise3"
        password = "credentialsJSON:fa55167e-9509-4c47-88a5-294ad3796110"
    }
})

object HttpsGithubComMoise3helloWordNodeRefsHeadsVsaasPack : GitVcsRoot({
    name = "https://github.com/moise3/hello-word-node#refs/heads/vsaas-pack"
    url = "https://github.com/moise3/hello-word-node"
    branch = "refs/heads/vsaas-pack"
    authMethod = password {
        userName = "moise3"
        password = "credentialsJSON:85791559-1e11-4740-a672-a2a66ab83cbb"
    }
})
