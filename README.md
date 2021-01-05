# Node Hello World

Simple node.js application qui affiche "hello world"

Utile pour tester un développement

### Usage

```bash
    git clone git@github.com/moise3/hello-word-node.git
    cd src/hello-word-node
    npm install
    npm start
```

### Build des images
```bash
    docker build -t helloworld:vdocker -f build/Dockerfile.bionic .
    pack build helloworld:vpack --path src/. --builder cloudfoundry/cnb:bionic
    s2i build src/. centos/nodejs-12-centos7 helloworld:vs2i
```
