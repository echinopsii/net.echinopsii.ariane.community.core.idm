# CC installer idm JPA provider configuration parameters
#
# Copyright (C) 2014 Mathilde Ffrench
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
import getpass
import json
import os
import mysql.connector
from mysql.connector import errorcode
from tools.AConfParamNotNone import AConfParamNotNone
from tools.AConfUnit import AConfUnit

__author__ = 'echinopsii'


class cpHibernateConnectionPassword(AConfParamNotNone):

    name = "##hibernateConnectionPassword"
    description = "CC idm hibernate connection password"
    hide = True

    def __init__(self):
        self.value = None


class cpHibernateConnectionURL(AConfParamNotNone):

    name = "##hibernateConnectionURL"
    description = "CC idm hibernate connection URL"
    hide = False

    def __init__(self):
        self.value = None


class cpHibernateConnectionUsername(AConfParamNotNone):

    name = "##hibernateConnectionUsername"
    description = "CC idm hibernate username"
    hide = False

    def __init__(self):
        self.value = None


class cpHibernateDialect(AConfParamNotNone):

    name = "##hibernateDialect"
    description = "CC idm hibernate dialect"
    hide = False

    def __init__(self):
        self.value = None


class cpHibernateDriverClass(AConfParamNotNone):

    name = "##hibernateDriverClass"
    description = "CC idm hibernate driver class"
    hide = False

    def __init__(self):
        self.value = None


class cuIDMJPAProviderManagedServiceProcessor(AConfUnit):

    def __init__(self, targetConfDir):
        self.confUnitName = "CC idm JPA provider"
        self.confTemplatePath = os.path.abspath("resources/templates/components/com.spectral.cc.core.IDMJPAProviderManagedService.properties.tpl")
        self.confFinalPath = targetConfDir + "com.spectral.cc.core.IDMJPAProviderManagedService.properties"
        hibernateDriverClass = cpHibernateDriverClass()
        hibernateDialect = cpHibernateDialect()
        hibernateConnectionURL = cpHibernateConnectionURL()
        hibernateConnectionUsername = cpHibernateConnectionUsername()
        hibernateConnectionPassword = cpHibernateConnectionPassword()
        self.paramsDictionary = {
            hibernateDriverClass.name: hibernateDriverClass,
            hibernateDialect.name: hibernateDialect,
            hibernateConnectionURL.name: hibernateConnectionURL,
            hibernateConnectionUsername.name: hibernateConnectionUsername,
            hibernateConnectionPassword.name: hibernateConnectionPassword,
        }


class idmJPAProviderManagedServiceSyringe:

    def __init__(self, targetConfDif, silent):
        self.silent = silent
        self.idmJPAProviderManagedServiceCUProcessor = cuIDMJPAProviderManagedServiceProcessor(targetConfDif)
        idmJPAProviderManagedServiceCUJSON = open("resources/configvalues/components/cuIDMJPAProviderManagedService.json")
        self.idmJPAProviderManagedServiceCUValues = json.load(idmJPAProviderManagedServiceCUJSON)
        idmJPAProviderManagedServiceCUJSON.close()
        self.dbConfig = None

    def shootBuilder(self):
        idmJPAProviderManagedServiceDBTypeDefined = False
        idmJPAProviderManagedServiceConnectionDefined = False
        for key in self.idmJPAProviderManagedServiceCUProcessor.getParamsKeysList():

            if (key == cpHibernateDriverClass.name or key == cpHibernateDialect.name) and not idmJPAProviderManagedServiceDBTypeDefined:
                self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateDriverClass.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateDriverClass.name])
                self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateDialect.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateDialect.name])
                idmJPAProviderManagedServiceDBTypeDefined = True

            elif (key == cpHibernateConnectionURL.name or key == cpHibernateConnectionUsername.name or key == cpHibernateConnectionPassword.name) and not idmJPAProviderManagedServiceConnectionDefined:

                tmpurl = self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionURL.name].split("://")[1]

                dbServerFQDNDefault = tmpurl.split(":")[0]
                dbServerFQDNDefaultUI = "[default - " + dbServerFQDNDefault + "] "

                tmpurl = tmpurl.split(":")[1]

                dbServerPortDefault = tmpurl.split("/")[0]
                dbServerPortDefaultUI = "[default - " + dbServerPortDefault + "] "

                dbNameDefault = tmpurl.split("/")[1]
                dbNameDefaultUI = "[default - " + dbNameDefault + "] "

                while not idmJPAProviderManagedServiceConnectionDefined:

                    if not self.silent:
                        dbServerFQDN = input("%-- >> Define CC idm DB FQDN " + dbServerFQDNDefaultUI + ": ")
                        if dbServerFQDN == "" or dbServerFQDN is None:
                            dbServerFQDN = dbServerFQDNDefault
                        else:
                            dbServerFQDNDefaultUI = "[default - " + dbServerFQDN + "] "
                            dbServerFQDNDefault = dbServerFQDN
                    else:
                        dbServerFQDN = dbServerFQDNDefault

                    if not self.silent:
                        serverPortIsValid = False
                        dbServerPortStr = ""
                        while not serverPortIsValid:
                            dbServerPort = 0
                            dbServerPortStr = input("%-- >> Define CC idm DB port " + dbServerPortDefaultUI + ": ")
                            if dbServerPortStr == "" or dbServerPortStr is None:
                                dbServerPortStr = dbServerPortDefault
                                dbServerPort = int(dbServerPortDefault)
                                serverPortIsValid = True
                            else:
                                try:
                                    dbServerPort = int(dbServerPortStr)
                                    if (dbServerPort <= 0) and (dbServerPort > 65535):
                                        print("%-- !! Invalid DB port " + str(dbServerPort) + ": not in port range")
                                    else:
                                        dbServerPortDefaultUI = "[default - " + dbServerPortStr + "] "
                                        dbServerPortDefault = dbServerPortStr
                                        serverPortIsValid = True
                                except ValueError:
                                    print("%-- !! Invalid DB port " + dbServerPortStr + " : not a number")
                    else:
                        dbServerPortStr = dbServerPortDefault
                        dbServerPort = int(dbServerPortStr)

                    if not self.silent:
                        dbNameIsValid = False
                        dbName = ""
                        while not dbNameIsValid:
                            dbName = input("%-- >> Define CC idm DB name " + dbNameDefaultUI + ": ")
                            if dbName != "":
                                dbNameIsValid = True
                                dbNameDefault = dbName
                                dbNameDefaultUI = "[default - " + dbName + "] "
                            elif dbNameDefault != "":
                                dbName = dbNameDefault
                                dbNameIsValid = True
                    else:
                        dbName = dbNameDefault

                    if not self.silent:
                        self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionURL.name] = "jdbc:mysql://" + dbServerFQDN + ":" + dbServerPortStr + "/" + dbName
                    self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateConnectionURL.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionURL.name])

                    if not self.silent:
                        dbServerUsernameDefault = self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name]
                        dbServerUsernameDefaultUI = "[default - " + dbServerUsernameDefault + "] "

                        dbServerUsernameIsValid = False
                        while not dbServerUsernameIsValid:
                            self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name] = input("%-- >> Define CC idm DB username " + dbServerUsernameDefaultUI + ": ")
                            if self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name] != "":
                                dbServerUsernameIsValid = True
                                dbServerUsernameDefault = self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name]
                                dbServerUsernameDefaultUI = "[default - " + dbServerUsernameDefault + "]"
                            elif dbServerUsernameDefault != "":
                                self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name] = dbServerUsernameDefault
                                dbServerUsernameIsValid = True
                    else:
                        self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateConnectionUsername.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name])

                    if not self.silent:
                        dbServerPasswordIsValid = False
                        while not dbServerPasswordIsValid:
                            self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionPassword.name] = getpass.getpass("%-- >> Define CC idm DB password : ")
                            if self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionPassword.name] != "":
                                dbServerPasswordIsValid = True
                    else:
                        self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateConnectionPassword.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionPassword.name])

                    try:
                        cnx = mysql.connector.connect(
                            user=self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name],
                            password=self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionPassword.name],
                            host=dbServerFQDN,
                            port=dbServerPort,
                            database=dbName
                        )
                        idmJPAProviderManagedServiceConnectionDefined = True
                        self.dbConfig = {
                            'user': self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name],
                            'password': self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionPassword.name],
                            'host': dbServerFQDN,
                            'port': dbServerPort,
                            'database': dbName
                        }
                    except mysql.connector.Error as err:
                        if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                            print("Something is wrong with your user name or password")
                        elif err.errno == errorcode.ER_BAD_DB_ERROR:
                            print("Database does not exists")
                        else:
                            print(err)
                    else:
                        cnx.close()

                self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateConnectionURL.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionURL.name])
                self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateConnectionUsername.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionUsername.name])
                self.idmJPAProviderManagedServiceCUProcessor.setKeyParamValue(cpHibernateConnectionPassword.name, self.idmJPAProviderManagedServiceCUValues[cpHibernateConnectionPassword.name])

    def getDBConfigFromShoot(self):
        return self.dbConfig

    def inject(self):
        idmJPAProviderManagedServiceCUJSON = open("resources/configvalues/components/cuIDMJPAProviderManagedService.json", "w")
        jsonStr = json.dumps(self.idmJPAProviderManagedServiceCUValues, sort_keys=True, indent=4, separators=(',', ': '))
        idmJPAProviderManagedServiceCUJSON.write(jsonStr)
        idmJPAProviderManagedServiceCUJSON.close()
        self.idmJPAProviderManagedServiceCUProcessor.process()