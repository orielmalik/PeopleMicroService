from cassandra.cluster import Cluster

# יצירת חיבור לאשכול Cassandra
cluster = Cluster(['localhost'])
session = cluster.connect('mykeyspace')

def create_customer(id, name, email):
    """יוצר לקוח חדש"""
    session.execute("INSERT INTO peoples (id, name, email) VALUES (%s, %s, %s)", [id, name, email])

def get_customer(id):
    """מחזיר את פרטי הלקוח לפי ID"""
    row = session.execute("SELECT * FROM peoples WHERE id = %s", [id]).one()
    return row

def update_customer(id, new_email):
    """מעדכן את כתובת הדוא"ל של לקוח"""
    session.execute("UPDATE peoples SET email = %s WHERE id = %s", [new_email, id])

def delete_customer(id):
    """מחק את הלקוח לפי ID"""
    session.execute("DELETE FROM peoples WHERE id = %s", [id])


def close():
    session.shutdown()
    cluster.shutdown()