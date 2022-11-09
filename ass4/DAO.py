from DTO import *

class _Vaccine:
    def __init__(self,conn):
        self._conn = conn
    
    def insert(self,vaccine):
        self._conn.execute('''
            INSERT INTO vaccines (id,date,supplier,quantity) VALUES (?,?,?,?)
        ''',[vaccine.id,vaccine.date,vaccine.supplier,vaccine.quantity])

    def find(self,id):
        cur = self._conn.cursor()
        vacDB = cur.execute('''
            SELECT * FROM vaccines WHERE id = (?)
        ''',[id])
        return Vaccine(*vacDB.fetchone())
    
class _Supplier:
    def __init__(self,conn):
        self._conn = conn

    def insert(self,supplier):
        self._conn.execute('''
            INSERT INTO suppliers (id,name,logistic) VALUES (?,?,?)
        ''',[supplier.id,supplier.name,supplier.logistics])

    def find(self,id):
        cur = self._conn.cursor()
        supDB = cur.execute('''
            SELECT * FROM suppliers WHERE id = (?)
        ''',[id])
        return Supplier(*supDB.fetchone())
    

class _Clinic:
    def __init__(self,conn):
        self._conn = conn

    def insert(self,clinic):
        self._conn.execute('''
            INSERT INTO clinics (id,location,demand,logistic) VALUES (?,?,?,?)
        ''',[clinic.id,clinic.location,clinic.demand,clinic.logistics])

            def find(self,id):
        cur = self._conn.cursor()
        supDB = cur.execute('''
            SELECT * FROM clinics WHERE id = (?)
        ''',[id])
        return Clinic(*supDB.fetchone())

class _Logistic:
    def __init__(self,conn):
        self._conn = conn

    def insert(self,logistic):
        self._conn.execute('''
            INSERT INTO logistics (id,name,count_sent,count_received) VALUES (?,?,?,?)
        ''',[logistic.id,logistic.name,logistic.count_sent,logistic.count_received])

    def updateCountReceived(self,logisticsID,amount):
        self._conn.execute('''
                UPDATE logistics SET count_received = count_received + (?) WHERE id = (?)
            ''',[amount,logisticsID])

        def find(self,id):
        cur = self._conn.cursor()
        supDB = cur.execute('''
            SELECT * FROM logistics WHERE id = (?)
        ''',[id])
        return Logistic(*supDB.fetchone())        