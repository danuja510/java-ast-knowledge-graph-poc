from node import Node
from ast_parser.config.constants import DATABASE_TABLE_NODE_LABEL

class DatabaseTableNode(Node):
    def __init__(self, __name: str):
        super().__init__(DATABASE_TABLE_NODE_LABEL, __name)
