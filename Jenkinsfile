// library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
//         [$class : 'GitSCMSource'
//          remote: 'https://github.com/prakhar7017/jenkins-shared-library.git'
//             credentialsId: 'gitlab-credentials'
//         ]
// ) for not global 

@Library('jenkins-shared-library')_

pipeline {
    agent any
    tools {
        maven  'maven'
    }
//     parameters {
//         choice(name: 'VERSION', choices: ['1.1.0','1.2.0','1.3.0'], description: 'version to deploy')
//         booleanParam(name: 'executeTests', defaultValue: true, description: 'Run tests?')
//    }
    stages {
        stage('increment version') {
            steps {
                script {
                    echo 'incrementing version'
                    sh '''
                        #!/bin/bash
                        set -e
                        CURRENT=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
                        # Strip -SNAPSHOT for parsing (e.g. 1.1.0-SNAPSHOT -> 1.1.0)
                        BASE=${CURRENT%-SNAPSHOT}
                        MAJOR=$(echo "$BASE" | cut -d. -f1)
                        MINOR=$(echo "$BASE" | cut -d. -f2)
                        PATCH=$(echo "$BASE" | cut -d. -f3)
                        PATCH=$((PATCH + 1))
                        NEW_VERSION="${MAJOR}.${MINOR}.${PATCH}"
                        mvn org.codehaus.mojo:versions-maven-plugin:2.16.2:set -DnewVersion="$NEW_VERSION" -DgenerateBackupPoms=false
                        mvn org.codehaus.mojo:versions-maven-plugin:2.16.2:commit
                    '''
                    def version = sh(
                            script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout',
                            returnStdout: true
                    ).trim()
                    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    buildImage "prakhar7017/java-maven-repo:$IMAGE_NAME"
                    dockerLogin()
                    dockerPush "prakhar7017/java-maven-repo:$IMAGE_NAME"
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    deployApp()
                }
            }
        }
        stage("commit version update") {
            steps {
                script {
                    commitVersion()
                }
            }
        }
    }
}