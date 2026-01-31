// library identifier: 'jenkins-shared-library@main', retriever: modernSCM(
//         [$class : 'GitSCMSource'
//          remote: 'https://github.com/prakhar7017/jenkins-shared-library.git'
//             credentialsId: 'gitlab-credentials'
//         ]
// ) for not global 

@Library('jenkins-shared-library')
def gv
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
        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
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
                    buildImage 'prakhar7017/java-maven-repo:java-maven-app-3.0'
                    dockerLogin()
                    dockerPush 'prakhar7017/java-maven-repo:java-maven-app-3.0'
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
    }
}