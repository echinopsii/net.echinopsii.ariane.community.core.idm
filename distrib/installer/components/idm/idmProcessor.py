# CC installer idm processor
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
from components.idm.cuIDMJPAProviderManagedService import idmJPAProviderManagedServiceSyringe
from components.idm.dbIDMMySQLInitiator import dbIDMMySQLInitiator
from components.idm.dbIDMMySQLPopulator import dbIDMMySQLPopulator

__author__ = 'mffrench'


class idmProcessor:

    def __init__(self, homeDirPath, silent):
        print("\n%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--%--\n")
        print("%-- CC idm configuration : \n")
        self.silent = silent
        self.homeDirPath = homeDirPath
        kernelRepositoryDirPath = self.homeDirPath + "/repository/cc-distrib/"
        if not os.path.exists(kernelRepositoryDirPath):
            os.makedirs(kernelRepositoryDirPath, 0o755)
        self.idmJPAProviderSyringe = idmJPAProviderManagedServiceSyringe(kernelRepositoryDirPath, silent)
        self.idmJPAProviderSyringe.shootBuilder()
        self.idmDBConfig = self.idmJPAProviderSyringe.getDBConfigFromShoot()
        self.idmSQLInitiator = dbIDMMySQLInitiator(self.idmDBConfig)
        self.idmSQLPopulator = dbIDMMySQLPopulator(self.idmDBConfig)

    def process(self):
        self.idmJPAProviderSyringe.inject()
        self.idmSQLInitiator.process()
        self.idmSQLPopulator.process()
        return self
