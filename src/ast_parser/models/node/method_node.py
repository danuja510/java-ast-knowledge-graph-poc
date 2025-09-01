from node import Node
from ast_parser.config.constants import METHOD_NODE_LABEL

class MethodNode(Node):
    def __init__(self, __name: str):
        super().__init__(METHOD_NODE_LABEL, __name)