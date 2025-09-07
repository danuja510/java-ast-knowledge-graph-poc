from neo4j import GraphDatabase

class GraphDataSource:
    def __init__(self, uri, user, password, database=None):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        self.database = database

    def execute_query(self, query, **parameters):
        return self.driver.execute_query(query, database_=self.database, **parameters)

    def close(self):
        self.driver.close()