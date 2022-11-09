class Vaccine:
    def __init__(self,id,date,supplier,quantity):
        self.id = id
        self.date = date
        self.supplier = supplier
        self.quantity = quantity

class Supplier:
    def __init__(self,id,name,logistics):
        self.id = id
        self.name = name
        self.logistics = logistics

class Clinic:
    def __init__(self,id,location,demand,logistics):
        self.id = id
        self.location = location
        self.demand = demand
        self.logistics = logistics  

class Logistic:
    def __init__(self,id,name,count_sent,count_received):
        self.id = id
        self.name = name 
        self.count_sent = count_sent
        self.count_received = count_received      