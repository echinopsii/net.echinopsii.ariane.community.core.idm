# installer idm JPA provider configuration parameters
#
# Copyright (C) 2014 echinopsii
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

__author__ = 'mffrench'


class CPHibernateConnectionPassword(AConfParamNotNone):

    name = "##hibernateConnectionPassword"
    description = "IDM DB connection password"
    hide = True

    def __init__(self):
        self.value = None

    def is_valid(self):
        return super(CPHibernateConnectionPassword, self).is_valid()


class CPHibernateConnectionURL(AConfParamNotNone):

    name = "##hibernateConnectionURL"
    description = "IDM DB connection URL"
    hide = False

    def __init__(self):
        self.value = None

    def is_valid(self):
        return super(CPHibernateConnectionURL, self).is_valid()


class CPHibernateConnectionUsername(AConfParamNotNone):

    name = "##hibernateConnectionUsername"
    description = "IDM DB username"
    hide = False

    def __init__(self):
        self.value = None

    def is_valid(self):
        return super(CPHibernateConnectionUsername, self).is_valid()


class CPHibernateDialect(AConfParamNotNone):

    name = "##hibernateDialect"
    description = "IDM DB dialect"
    hide = False

    def __init__(self):
        self.value = None

    def is_valid(self):
        return super(CPHibernateDialect, self).is_valid()


class CPHibernateDriverClass(AConfParamNotNone):

    name = "##hibernateDriverClass"
    description = "IDM DB driver class"
    hide = False

    def __init__(self):
        self.value = None

    def is_valid(self):
        return super(CPHibernateDriverClass, self).is_valid()


class CPHibernateCacheInfinispanCfg(AConfParamNotNone):

    name = "##hibernateCacheInfinispanCfg"
    description = "IDM DB L2 cache infinispan config"
    hide = False

    def __init__(self):
        self.value = None

    def is_valid(self):
        return super(CPHibernateCacheInfinispanCfg, self).is_valid()


class CUIDMJPAProviderManagedServiceProcessor(AConfUnit):

    def __init__(self, target_conf_dir):
        self.confUnitName = "IDM JPA provider"
        self.confTemplatePath = os.path.abspath(
            "resources/templates/components/"
            "net.echinopsii.ariane.community.core.IDMJPAProviderManagedService.properties.tpl"
        )
        self.confFinalPath = target_conf_dir + \
            "net.echinopsii.ariane.community.core.IDMJPAProviderManagedService.properties"
        hibernate_driver_class = CPHibernateDriverClass()
        hibernate_dialect = CPHibernateDialect()
        hibernate_connection_url = CPHibernateConnectionURL()
        hibernate_connection_username = CPHibernateConnectionUsername()
        hibernate_connection_password = CPHibernateConnectionPassword()
        self.paramsDictionary = {
            hibernate_driver_class.name: hibernate_driver_class,
            hibernate_dialect.name: hibernate_dialect,
            hibernate_connection_url.name: hibernate_connection_url,
            hibernate_connection_username.name: hibernate_connection_username,
            hibernate_connection_password.name: hibernate_connection_password
        }

    def process(self):
        return super(CUIDMJPAProviderManagedServiceProcessor, self).process()

    def get_param_from_key(self, key):
        return super(CUIDMJPAProviderManagedServiceProcessor, self).get_param_from_key(key)

    def get_params_keys_list(self):
        return super(CUIDMJPAProviderManagedServiceProcessor, self).get_params_keys_list()

    def set_key_param_value(self, key, value):
        return super(CUIDMJPAProviderManagedServiceProcessor, self).set_key_param_value(key, value)


class IDMJPAProviderManagedServiceSyringe:

    def __init__(self, target_conf_dir, silent):
        self.silent = silent
        self.idmJPAProviderManagedServiceCUProcessor = CUIDMJPAProviderManagedServiceProcessor(target_conf_dir)
        idm_jpa_provider_managed_service_cujson = open(
            "resources/configvalues/components/cuIDMJPAProviderManagedService.json"
        )
        self.idmJPAProviderManagedServiceCUValues = json.load(idm_jpa_provider_managed_service_cujson)
        idm_jpa_provider_managed_service_cujson.close()
        self.dbConfig = None

    def shoot_builder(self):
        idm_jpa_provider_cache_infinispan_cfg_defined = False
        idm_jpa_provider_managed_service_db_type_defined = False
        idm_jpa_provider_managed_service_connection_defined = False
        for key in self.idmJPAProviderManagedServiceCUProcessor.get_params_keys_list():

            if (key == CPHibernateDriverClass.name or key == CPHibernateDialect.name) and \
                    not idm_jpa_provider_managed_service_db_type_defined:
                self.idmJPAProviderManagedServiceCUProcessor.\
                    set_key_param_value(
                        CPHibernateDriverClass.name,
                        self.idmJPAProviderManagedServiceCUValues[CPHibernateDriverClass.name]
                    )
                self.idmJPAProviderManagedServiceCUProcessor.\
                    set_key_param_value(
                        CPHibernateDialect.name,
                        self.idmJPAProviderManagedServiceCUValues[CPHibernateDialect.name]
                    )
                idm_jpa_provider_managed_service_db_type_defined = True

            elif (key == CPHibernateCacheInfinispanCfg.name) and not idm_jpa_provider_cache_infinispan_cfg_defined:
                self.idmJPAProviderManagedServiceCUProcessor.\
                    set_key_param_value(
                        CPHibernateCacheInfinispanCfg.name,
                        self.cacheConfPath
                    )
                idm_jpa_provider_cache_infinispan_cfg_defined = True

            elif (key == CPHibernateConnectionURL.name or
                  key == CPHibernateConnectionUsername.name or
                  key == CPHibernateConnectionPassword.name) \
                    and not idm_jpa_provider_managed_service_connection_defined:

                tmp_url = self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionURL.name].split("://")[1]

                db_server_fqdn_default = tmp_url.split(":")[0]
                db_server_fqdn_default_ui = "[default - " + db_server_fqdn_default + "] "

                tmp_url = tmp_url.split(":")[1]

                db_server_port_default = tmp_url.split("/")[0]
                db_server_port_default_ui = "[default - " + db_server_port_default + "] "

                db_name_default = tmp_url.split("/")[1]
                db_name_default_ui = "[default - " + db_name_default + "] "

                while not idm_jpa_provider_managed_service_connection_defined:

                    if not self.silent:
                        db_server_dqdn = input("%-- >> Define IDM DB FQDN " + db_server_fqdn_default_ui + ": ")
                        if db_server_dqdn == "" or db_server_dqdn is None:
                            db_server_dqdn = db_server_fqdn_default
                        else:
                            db_server_fqdn_default_ui = "[default - " + db_server_dqdn + "] "
                            db_server_fqdn_default = db_server_dqdn
                    else:
                        db_server_dqdn = db_server_fqdn_default

                    if not self.silent:
                        server_port_is_valid = False
                        db_server_port_str = ""
                        while not server_port_is_valid:
                            db_server_port = 0
                            db_server_port_str = input("%-- >> Define IDM DB port " + db_server_port_default_ui + ": ")
                            if db_server_port_str == "" or db_server_port_str is None:
                                db_server_port_str = db_server_port_default
                                db_server_port = int(db_server_port_default)
                                server_port_is_valid = True
                            else:
                                try:
                                    db_server_port = int(db_server_port_str)
                                    if (db_server_port <= 0) and (db_server_port > 65535):
                                        print("%-- !! Invalid DB port " + str(db_server_port) + ": not in port range")
                                    else:
                                        db_server_port_default_ui = "[default - " + db_server_port_str + "] "
                                        db_server_port_default = db_server_port_str
                                        server_port_is_valid = True
                                except ValueError:
                                    print("%-- !! Invalid DB port " + db_server_port_str + " : not a number")
                    else:
                        db_server_port_str = db_server_port_default
                        db_server_port = int(db_server_port_str)

                    if not self.silent:
                        db_name_is_valid = False
                        db_name = ""
                        while not db_name_is_valid:
                            db_name = input("%-- >> Define IDM DB name " + db_name_default_ui + ": ")
                            if db_name != "":
                                db_name_is_valid = True
                                db_name_default = db_name
                                db_name_default_ui = "[default - " + db_name + "] "
                            elif db_name_default != "":
                                db_name = db_name_default
                                db_name_is_valid = True
                    else:
                        db_name = db_name_default

                    if not self.silent:
                        self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionURL.name] = \
                            "jdbc:mysql://" + db_server_dqdn + ":" + db_server_port_str + "/" + db_name
                    self.idmJPAProviderManagedServiceCUProcessor.set_key_param_value(
                        CPHibernateConnectionURL.name,
                        self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionURL.name]
                    )

                    if not self.silent:
                        db_server_username_default = self.idmJPAProviderManagedServiceCUValues[
                            CPHibernateConnectionUsername.name
                        ]
                        db_server_username_default_ui = "[default - " + db_server_username_default + "] "

                        db_server_username_is_valid = False
                        while not db_server_username_is_valid:
                            self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name] = \
                                input("%-- >> Define IDM DB username " + db_server_username_default_ui + ": ")
                            if self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name] != "":
                                db_server_username_is_valid = True
                                db_server_username_default = self.idmJPAProviderManagedServiceCUValues[
                                    CPHibernateConnectionUsername.name
                                ]
                                db_server_username_default_ui = "[default - " + db_server_username_default + "]"
                            elif db_server_username_default != "":
                                self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name] = \
                                    db_server_username_default
                                db_server_username_is_valid = True
                    else:
                        self.idmJPAProviderManagedServiceCUProcessor.set_key_param_value(
                            CPHibernateConnectionUsername.name,
                            self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name]
                        )

                    if not self.silent:
                        db_server_password_is_valid = False
                        while not db_server_password_is_valid:
                            self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionPassword.name] = \
                                getpass.getpass("%-- >> Define IDM DB password : ")
                            if self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionPassword.name] != "":
                                db_server_password_is_valid = True
                    else:
                        self.idmJPAProviderManagedServiceCUProcessor.set_key_param_value(
                            CPHibernateConnectionPassword.name,
                            self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionPassword.name]
                        )

                    try:
                        cnx = mysql.connector.connect(
                            user=self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name],
                            password=self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionPassword.name],
                            host=db_server_dqdn,
                            port=db_server_port,
                            database=db_name
                        )
                        idm_jpa_provider_managed_service_connection_defined = True
                        self.dbConfig = {
                            'user': self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name],
                            'password': self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionPassword.name],
                            'host': db_server_dqdn,
                            'port': db_server_port,
                            'database': db_name
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

                self.idmJPAProviderManagedServiceCUProcessor.set_key_param_value(
                    CPHibernateConnectionURL.name,
                    self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionURL.name]
                )
                self.idmJPAProviderManagedServiceCUProcessor.set_key_param_value(
                    CPHibernateConnectionUsername.name,
                    self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionUsername.name]
                )
                self.idmJPAProviderManagedServiceCUProcessor.set_key_param_value(
                    CPHibernateConnectionPassword.name,
                    self.idmJPAProviderManagedServiceCUValues[CPHibernateConnectionPassword.name]
                )

    def get_db_config_from_shoot(self):
        return self.dbConfig

    def inject(self):
        idm_jpa_provider_managed_service_cujson = open(
            "resources/configvalues/components/cuIDMJPAProviderManagedService.json", "w"
        )
        json_str = json.dumps(
            self.idmJPAProviderManagedServiceCUValues, sort_keys=True, indent=4, separators=(',', ': ')
        )
        idm_jpa_provider_managed_service_cujson.write(json_str)
        idm_jpa_provider_managed_service_cujson.close()
        self.idmJPAProviderManagedServiceCUProcessor.process()
