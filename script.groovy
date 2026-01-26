def buildJar() {
    echo "building the application"
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image"
    withCredentials([usernamePassword(credentialsId:"docker-hub-repo",usernameVariable: "USER", passwordVariable: "PWD")]) {
        sh 'docker build -t prakhar7017/java-maven-repo:java-maven-app-1.1 .'
        sh "echo $PWD | docker login -u $USER --password-stdin"
        sh 'docker push prakhar7017/java-maven-repo:java-maven-app-1.1'

    }
}

def deployApp() {
    echo 'deploying the application...'
} 


return this