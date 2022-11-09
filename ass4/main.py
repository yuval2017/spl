from DAO import _Vaccine,_Supplier,_Clinic,_Logistic
from DTO import *
from datetime import datetime
import sqlite3

    
supplierNameToID = {}

class DB:
    def __init__(self,connection):
        self.conn = connection
        self.vaccines = []
        self.suppliers = []
        self.clinics = []
        self.logistics = []
        self.vacDAO = _Vaccine(connection)
        self.supDAO = _Supplier(connection)
        self.clDAO = _Clinic(connection)
        self.logDAO = _Logistic(connection)
        self.vID = 1
        self.sID = 1
        self.cID = 1
        self.lID = 1
        self.totalInventory = 0
        self.totalDemand = 0
        self.totalReceived = 0
        self.totalSent = 0

    def addVaccine(self,vaccine):
        self.vacDAO.insert(vaccine)
        self.vaccines.append(vaccine)
        self.totalInventory += vaccine.quantity
        self.vID += 1

    def getVaccine(self,id):
        return self.vacDAO.find(id)

    def addSupplier(self,supplier):
        self.supDAO.insert(supplier)
        self.suppliers.append(supplier)
        self.sID += 1

    def getSupplier(self,id):
        return self.supDAO.find(id)

    def addClinic(self,clinic):
        self.clDAO.insert(clinic)
        self.clinics.append(clinic)
        self.totalDemand += clinic.demand
        self.cID += 1

    def getClinic(self,id):
        return self.clinics[id-1]

    def addLogistic(self,logistic):
        self.logDAO.insert(logistic)
        self.logistics.append(logistic)
        self.lID += 1

    def getLogistic(self,id):
        return self.Logistics[id-1]

    def readData(self,_path):
        with open(_path) as file:
            bluks = file.readline().split(',')
            self.createVaccinesList(file,int(bluks[0]))
            self.createSuppliersList(file,int(bluks[1]))
            self.createClinicsList(file,int(bluks[2]))
            self.createLogisticsList(file,int(bluks[3]))
        
    def createVaccinesList(self,file,num):
        for i in range(0,num):
                curr = file.readline().strip().split(',')
                v = Vaccine(int(curr[0]),datetime.strptime(curr[1],'%Y-%m-%d').date(),int(curr[2]),int(curr[3]))
                self.addVaccine(v)

    def createSuppliersList(self,file,num):
        for i in range(0,num):
                curr = file.readline().strip().split(',')
                s = Supplier(int(curr[0]),curr[1],int(curr[2]))
                supplierNameToID[s.name] = s.id
                self.addSupplier(s)   

    def createClinicsList(self,file,num):
        for i in range(0,num):
                curr = file.readline().strip().split(',')
                c = Clinic(int(curr[0]),curr[1],int(curr[2]),int(curr[3]))
                self.addClinic(c); 

    def createLogisticsList(self,file,num):
        for i in range(0,num):
                curr = file.readline().strip().split(',')
                l = Logistic(int(curr[0]),curr[1],int(curr[2]),int(curr[3]))
                self.addLogistic(l)

def ReceiveShipment(supplierID,amount,date,DB):
    supDTO = DB.getSupplier(supplierID)
    vacDTO = Vaccine(DB.vID,date,supplierID,amount)
    DB.addVaccine(vacDTO)
    DB.logDAO.updateCountReceived(supDTO.logistics,amount)
    DB.totalReceived += amount
    DB.totalInventory -= amount

def SendShipment(location,amount):
    pass


conn = sqlite3.connect('database.db')
db = DB(conn)
db.readData('config.txt')
with open('orders.txt') as file:
    for line in file:  
        order = line.strip().split(',')
        if(len(order) == 3):
            ReceiveShipment(supplierNameToID[order[0]],int(order[1]),datetime.strptime(order[2],'%Y-%m-%d').date(),db)
        print([db.totalInventory,db.totalDemand,db.totalReceived,db.totalSent])
conn.commit()
conn.close()