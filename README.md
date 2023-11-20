# Cloud Metadata API Demo

## Overview

This repository contains a demo application designed to illustrate the potential risks associated with the Cloud Metadata API. The application features two input fields, each representing a different vulnerability. Users can interact with the demo to understand the security implications of SSRF (Server-Side Request Forgery) and Command Injection vulnerabilities in regard to the Cloud Metadata API

## SSRF Vulnerability

The first input field demonstrates an SSRF vulnerability. When a user inputs a target URL, the application performs a request to that URL and generates a curl request using the provided payload. Note that the SSRF vulnerability has been partially mitigated (when it comes to attacking the Cloud Metadata API) by the inclusion of a required header from version `/v1` onwards.

### Usage

1. Enter a target URL in the SSRF input field.
2. Observe the generated curl request based on the provided payload.

#### Example SSRF Payload

```bash
http://169.254.169.254/computeMetadata/
```

## Command Injection Vulnerability

The second input field showcases a command injection vulnerability. Users can input a command, and the application returns the execution of that command within a shell. It's important to be aware that successful exploitation of this vulnerability can lead to unauthorized access to service account tokens and facilitate lateral movement within the underlying system and many more.

### Usage

1. Enter a command in the Command Injection input field.
2. Review the output to understand the potential impact of command injection.

#### Example Command Injection Payload

```bash
curl http://metadata.google.internal/computeMetadata/v1/instance/service-accounts/ -H "Metadata-Flavor: Google"
```

## How to Use

This repository is open for anyone to use as a learning tool to explore and showcase the workings of the Cloud Metadata API. Feel free to test the vulnerabilities, but exercise caution and ensure that you are in a controlled environment.

## How to Run the application

```bash
## Navigate to the application directory
cd ssrf-demo/app
## Clean any previous build phase and makes sure the maven dependencies are installed
mvn clean install
## Starts Cloudbuild.yaml file to generate the container image
gcloud builds submit --region=$REGION --tag $IMAGE_REFERENCE
## Navigate to the infra directory
cd ../infra
## Deploy the application with the correct image reference (to be filled in)
kubectl apply -f deployment.yaml
## Expose the Deployment through a Node Port to make it available for port forwarding
kubectl expose deployment/cloud-metadata-demo --type="NodePort" --port 8080
## Port Forward the application on localhost:8080
gcloud container clusters get-credentials $GKE_CLUSTER_NAME --zone $REGION --project $GCP_PROJECT && kubectl port-forward $(kubectl get pod --selector="app=cloud-metadata-demo" --output jsonpath='{.items[0].metadata.name}') 8080:8080
```

## Important Note

**Security Warning:** The vulnerabilities demonstrated in this application are intentional and for educational purposes only. Do not use this application in a production environment, and ensure that you have the necessary permissions before attempting to exploit any vulnerabilities.

## Contributing

Contributions to enhance the demo or address any security concerns are welcome. Please submit a pull request or open an issue to discuss potential improvements.

