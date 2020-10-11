FROM m4rc1no5/wildfly:17.0.1.Final
MAINTAINER marcin.zaremba@gmail.com

RUN $JBOSS_HOME/bin/add-user.sh m4rc1no5 m4rc1no5 --silent

# deploy
COPY InvestMonitor-api.war $JBOSS_HOME/standalone/deployments/