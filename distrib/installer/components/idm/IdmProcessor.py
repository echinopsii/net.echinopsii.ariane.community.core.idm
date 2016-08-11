# installer idm processor
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
import os
from components.idm.cuIDMJPAProviderManagedService import IDMJPAProviderManagedServiceSyringe
from components.idm.DBIDMMySQLInitiator import DBIDMMySQLInitiator
from components.idm.DBIDMMySQLPopulator import DBIDMMySQLPopulator

__author__ = 'mffrench'


class IdmProcessor:

    def __init__(self, home_dir_path, dist_dep_type, directory_db_conf, idm_db_conf, bus_processor, silent):
        print("\n%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--"
              "%--%--%--%--%--%--%--%--%--\n")
        print("%-- IDM configuration : \n")
        self.silent = silent
        self.homeDirPath = home_dir_path
        self.dist_dep_type = dist_dep_type
        self.idmDBConfig = idm_db_conf
        self.directoryDBConfig = directory_db_conf
        self.busProcessor = bus_processor

        kernel_repository_dir_path = self.homeDirPath + "/repository/ariane-core/"
        if not os.path.exists(kernel_repository_dir_path):
            os.makedirs(kernel_repository_dir_path, 0o755)
        self.idmJPAProviderSyringe = IDMJPAProviderManagedServiceSyringe(kernel_repository_dir_path, silent)
        self.idmJPAProviderSyringe.shoot_builder()
        self.idmDBConfig = self.idmJPAProviderSyringe.get_db_config_from_shoot()
        self.idmSQLInitiator = DBIDMMySQLInitiator(self.idmDBConfig)
        self.idmSQLPopulator = DBIDMMySQLPopulator(self.idmDBConfig)

    def process(self):
        self.idmJPAProviderSyringe.inject()
        self.idmSQLInitiator.process()
        self.idmSQLPopulator.process()
        return self
