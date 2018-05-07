## Module-10 DevSecOps 

- DevOps is a combination of cultural philosophies, practices, and tools that emphasizes collaboration and communication between IT infra team and software developers

- DevSecOps covers security of and in the CI/CD pipeline, including automating security operations and auditing. The goals of DevSecOps are to:

	1. Embed security knowledge into DevOps teams so that they can secure the pipelines they design and automate.
	2. Embed application development knowledge and automated tools and processes into security teams so that they can provide security at scale in the cloud.

- In this module, we will covers a fundamental knowledge and tools for DevSecOps and how to implement in Java project.


### 1. Applying Git-Secret

Refer : ** https://github.com/awslabs/git-secrets **

- Prevents you from committing passwords and other sensitive information to a Git repository.

#### 1.1 Install Git-Secrit

```
git clone https://github.com/awslabs/git-secrets
```

You can use install target of the provided Makefile to install git secrets and the man page. You can customize the install path using the PREFIX and MANPREFIX variables.

```
make install
```

Or, installing with Homebrew (for OS X users).

```
brew install git-secrets
```

#### 1.2 Scan 

Scan all files in the repo:

```
git secrets --scan
```

Scans a single file for secrets:

```
git secrets --scan /path/to/file
```

Scans a directory recursively for secrets:

```
git secrets --scan -r /path/to/directory
```

Scans multiple files for secrets:

```
git secrets --scan /path/to/file /path/to/other/file
```

#### 1.3 Test AWS secret 

	1. register AWS secrete into git secrets

```
git secrets --add-provider -- git secrets --aws-provider
```
	2. Add your access key in moudle-04, applicaiton.properties fies
	
```
amazon.dynamodb.endpoint=endpoint
amazon.aws.accesskey=AAAAAXXCXCXCCXCXCX
amazon.aws.secretkey=key2
```

	3. Run a scan

```
git secrets --scan module-04/src/main/resources/application.properties 
module-04/src/main/resources/application.properties:3:amazon.aws.amazon.aws.accesskey=AAAAAXXCXCXCCXCXCX

[ERROR] Matched one or more prohibited patterns
```

#### 1.4 Test a custom pattern
	1. Save a following content as test.txt in your arbitrary directory

```
This is a test!
password=ex@mplepassword
password=******
More test...

```

2. Run following commands

```
git secrets --add 'password\s*=\s*.+'
git secrets --add --allowed --literal 'ex@mplepassword'

git secrets --scan ./text.txt

```

- You can add this step in your build phase


### 2. Use aws-security-benchmark

- Benchmark scripts mapped against trusted security frameworks.
- It requires Python 2.7 or 3.4

#### 2.1 Install aws-security-benchmark

1. Download 

```
git clone https://github.com/awslabs/aws-security-benchmark.git
```
2. 

### Post-Works
- Try to build your own DevSecOps CI/CD, choose following tasks.



### Reference
- DevSecOps blog : 

	https://aws.amazon.com/blogs/devops/implementing-devsecops-using-aws-codepipeline/
	https://aws.amazon.com/blogs/developer/devops-meets-security-security-testing-your-aws-application-part-i-unit-testing/	
	
- git-secrets - Prevents you from committing passwords and other sensitive information to a Git repository.
- aws-security-benchmark - Benchmark scripts mapped against trusted security frameworks.
- aws-config-rules - [Node, Python, Java] Repository of sample custom rules for AWS Config
- Netflix/security_monkey - Monitors policy changes and alerts on insecure configurations in an AWS account. 
- Netflix/edda - Edda is a service to track changes in your cloud deployments.
- ThreatResponse - Open Source Security Suite for hardening and responding in AWS.
- CloudSploit – Capturing things like open security groups, misconfigured VPCs, and more.
- Stelligent/Cfn_nag – Looks for patterns in CloudFormation templates that may indicate insecure infrastructure.
- Capitalone/cloud-custodian - Rules engine for AWS fleet management.

