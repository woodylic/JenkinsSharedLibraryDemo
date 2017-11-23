def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent any
        stages {
            stage('Prepared') {
                steps {
                    script {
                        IMAGE = "${pipelineParams.repository}:${pipelineParams.tag}"
                        REMOTE_IMAGE = "${pipelineParams.registry}/$IMAGE"
                    }
                }
            }
            stage('Docker build') {
                steps {
                    script {
                        echo "docker build -t $IMAGE ${pipelineParams.dockerfilePath}"
                    }
                }
            }
            stage('Docker push') {
                steps {
                    script {
                        echo "docker push $REMOTE_IMAGE"
                    }
                }
            }
        }            
    }
}