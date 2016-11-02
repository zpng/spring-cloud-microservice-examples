#!/bin/sh

DIR=`cd \`dirname ${BASH_SOURCE[0]}\`/.. && pwd`

rm -fr ${DIR}/cloud-thrift-interface/src/main/java/cloud/thrift/
thrift --gen java:beans -out ${DIR}/cloud-thrift-interface/src/main/java cloud-thrift-interface/src/main/resources/UserService.thrift
